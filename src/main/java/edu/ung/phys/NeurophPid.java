package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.neuroph.core.NeuralNetwork; // see http://neuroph.sourceforge.net/javadoc/index.html
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import edu.ung.phys.basicSim.PidTestDataReader;

public class NeurophPid {

  public TransferFunctionType tft; // e.g. TransferFunctionType.SIGMOID
  public int nParticleTypes;
  public int nVars;
  public ArrayList<Integer> nNeuronsInHiddenLayers;
  public ArrayList<Integer> uniqueParticleIDs;
  public ArrayList<Integer> totalOccurances, nCorrect, nIncorrect;
  public DataSet trainingSet, testingSet;
  public MultiLayerPerceptron mlp;

  // TODO add a constructor that loads a saved network
  // NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

  public NeurophPid(TransferFunctionType tft, int nParticleTypes, int nVars, ArrayList<Integer> nNeuronsInHiddenLayers) {
    init(tft, nParticleTypes, nVars, nNeuronsInHiddenLayers);
  }

  public NeurophPid(TransferFunctionType tft, int nParticleTypes, int nVars, Integer... nNeuronsInHiddenLayers) {
    init(tft, nParticleTypes, nVars, new ArrayList<Integer>(Arrays.asList(nNeuronsInHiddenLayers)));
  }

  private void init(TransferFunctionType tft, int nParticleTypes, int nVars, ArrayList<Integer> nNeuronsInHiddenLayers) {
    this.tft = tft;
    this.nParticleTypes = nParticleTypes;
    this.nVars = nVars;
    this.nNeuronsInHiddenLayers = nNeuronsInHiddenLayers;
    ArrayList<Integer> allLayers = new ArrayList<>(nNeuronsInHiddenLayers);
    allLayers.add(nParticleTypes);
    allLayers.add(0, nVars);
    mlp = new MultiLayerPerceptron(allLayers, tft);
    uniqueParticleIDs = new ArrayList<>();
    totalOccurances = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nCorrect = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nIncorrect = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
  }


  public void train(String filename, int nEvents) throws IOException {
    trainingSet = new DataSet(nVars, nParticleTypes);
    PidTestDataReader reader = new PidTestDataReader(filename, nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      if(!uniqueParticleIDs.contains(particleID)) uniqueParticleIDs.add(particleID);
      ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
      desiredOutput.add(uniqueParticleIDs.indexOf(particleID), 1.0);
      trainingSet.addRow(new DataSetRow(varValues, desiredOutput));
    }
    mlp.learn(trainingSet);
  }


  public void test(String filename, int nEvents) throws IOException {
    testingSet = new DataSet(nVars, nParticleTypes);
    PidTestDataReader reader = new PidTestDataReader(filename, nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      if(!uniqueParticleIDs.contains(particleID)) uniqueParticleIDs.add(particleID);
      ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
      desiredOutput.add(uniqueParticleIDs.indexOf(particleID), 1.0);

      int prediction = getPrediction(new DataSetRow(varValues, desiredOutput));
      int actual = uniqueParticleIDs.indexOf(particleID);

      if(actual == prediction) nCorrect.set(prediction, nCorrect.get(prediction)+1);
      else nIncorrect.set(prediction, nIncorrect.get(prediction)+1);
      totalOccurances.set(actual, totalOccurances.get(actual)+1);
    }
    
  }


  public void save(String filename) {
    mlp.save(filename);
  }


  public void printLearningRule() {
    System.out.println(mlp.getLearningRule().toString());
  }


  public int getPrediction(DataSetRow dataRow) {
    mlp.setInput(dataRow.getInput());
    mlp.calculate();
    double[] networkOutput = mlp.getOutput();
    int maxIndex = 0;
    for(int i = 1; i < networkOutput.length; i++) maxIndex = networkOutput[i] > networkOutput[maxIndex] ? i : maxIndex;
    return maxIndex;
  }


  public static void main(String[] args) throws IOException {

    int npart = 4;
    int nvar = 6;
    NeurophPid npid = new NeurophPid(TransferFunctionType.SIGMOID, npart, nvar, 5);
    npid.train(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-6537.txt", 100);
    npid.test(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-327307.txt", 50);

    System.out.println("");
    System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s %n", "", "total", "rec", "rec", "", "");
    System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s %n", "pid", "gen", "corr", "incorr", "eff", "pur");
    for(int k = 0; k < npid.uniqueParticleIDs.size(); k++) {
      int pid = npid.uniqueParticleIDs.get(k);
      int tot = npid.totalOccurances.get(k);
      int corr = npid.nCorrect.get(k);
      int incorr = npid.nIncorrect.get(k);
      double eff = (double) corr / (double) tot;
      double pur = (double) corr / (double) (corr + incorr);
      System.out.printf("%-6s %-6s %-6s %-6s %-1.4f %-1.4f %n", pid+":", tot, corr, incorr, eff, pur);
    }
    System.out.println("");

  }

  
}
