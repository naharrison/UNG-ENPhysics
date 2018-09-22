package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import edu.ung.phys.basicSim.PidTestDataReader;
import edu.ung.phys.EfficiencyPurityTracker;

public class EncogPid {

  public BasicNetwork network;
  public int nParticleTypes, nVars;
  public ArrayList<Integer> nNeuronsInHiddenLayers;
  public ArrayList<Integer> uniqueParticleIDs;
  public ArrayList<Integer> totalOccurances, nCorrect, nIncorrect;
  public MLDataSet trainingSet, testingSet;


  public EncogPid(int nParticleTypes, int nVars, ArrayList<Integer> nNeuronsInHiddenLayers) {
    init(nParticleTypes, nVars, nNeuronsInHiddenLayers);
  }

  public EncogPid(int nParticleTypes, int nVars, Integer... nNeuronsInHiddenLayers) {
    init(nParticleTypes, nVars, new ArrayList<Integer>(Arrays.asList(nNeuronsInHiddenLayers)));
  }

  private void init(int nParticleTypes, int nVars, ArrayList<Integer> nNeuronsInHiddenLayers) {
    this.nParticleTypes = nParticleTypes;
    this.nVars = nVars;
    this.nNeuronsInHiddenLayers = nNeuronsInHiddenLayers;
    network = new BasicNetwork();
    network.addLayer(new BasicLayer(null, true, nVars));
    for(Integer i : nNeuronsInHiddenLayers) network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
    network.addLayer(new BasicLayer(new ActivationSigmoid(), false, nParticleTypes));
    network.getStructure().finalizeStructure();
    network.reset();

    uniqueParticleIDs = new ArrayList<>();
    totalOccurances = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nCorrect = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nIncorrect = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
  }


  public void train(String filename, int nEvents) throws IOException {
    trainingSet = new BasicMLDataSet();
    PidTestDataReader reader = new PidTestDataReader(filename, nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      if(!uniqueParticleIDs.contains(particleID)) uniqueParticleIDs.add(particleID);
      ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
      desiredOutput.add(uniqueParticleIDs.indexOf(particleID), 1.0);

      BasicMLData inputData =
        new BasicMLData(ArrayUtils.toPrimitive(varValues.toArray(new Double[varValues.size()])));
      BasicMLData idealData =
        new BasicMLData(ArrayUtils.toPrimitive(desiredOutput.toArray(new Double[desiredOutput.size()])));
      trainingSet.add(inputData, idealData);
    }

    final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
    int epoch = 1;
    while(train.getError() > 0.025 || epoch == 1) {
      train.iteration();
      if(epoch %2000 == 0 || epoch < 101) System.out.println(epoch + " " + train.getError());
      epoch++;
    }
    train.finishTraining();
  }


  public void test(String filename, int nEvents) throws IOException {
    testingSet = new BasicMLDataSet();

    PidTestDataReader reader = new PidTestDataReader(filename, nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      if(!uniqueParticleIDs.contains(particleID)) uniqueParticleIDs.add(particleID);
      ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
      desiredOutput.add(uniqueParticleIDs.indexOf(particleID), 1.0);

      BasicMLData inputData =
        new BasicMLData(ArrayUtils.toPrimitive(varValues.toArray(new Double[varValues.size()])));
      BasicMLData idealData =
        new BasicMLData(ArrayUtils.toPrimitive(desiredOutput.toArray(new Double[desiredOutput.size()])));
      testingSet.add(inputData, idealData);
    }

    for(MLDataPair pair : testingSet) {
      final MLData output = network.compute(pair.getInput());
      
      System.out.print("Data: ");
      for(int k = 0; k < nVars; k++) System.out.print(pair.getInput().getData(k) + ", ");
      System.out.println("");
      System.out.print("Network Result: ");
      for(int k = 0; k < nParticleTypes; k++) System.out.print(output.getData(k) + ", ");
      System.out.println("");
      System.out.print("Ideal Result: ");
      for(int k = 0; k < nParticleTypes; k++) System.out.print(pair.getIdeal().getData(k) + ", ");
      System.out.println("");
      System.out.println("");
    }
  }


  public void shutdown() {
    Encog.getInstance().shutdown();
  }


  public static void main(String[] args) throws IOException {
    int npart = 4;
    int nvar = 6;
    EncogPid epid = new EncogPid(npart, nvar, 5);
    epid.train(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-6537.txt", 6000);
    epid.test(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-327307.txt", 600);

    System.out.println("index -> pid map: " + epid.uniqueParticleIDs);

    epid.shutdown();
  }

}
