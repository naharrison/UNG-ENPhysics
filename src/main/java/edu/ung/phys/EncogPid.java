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
  public EfficiencyPurityTracker epTracker;
  public MLDataSet trainingSet;

//TODO change Hidden layers, possibly activiation function (relu?), possibly backprop, 

  public EncogPid(int nParticleTypes, int nVars, ArrayList<Integer> nNeuronsInHiddenLayers, ArrayList<Integer> uniqueParticleIDs) {
    this.nParticleTypes = nParticleTypes;
    this.nVars = nVars;
    this.nNeuronsInHiddenLayers = nNeuronsInHiddenLayers;
    this.network = new BasicNetwork();
    network.addLayer(new BasicLayer(null, true, nVars));
    for(Integer i : nNeuronsInHiddenLayers) network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
    network.addLayer(new BasicLayer(new ActivationSigmoid(), false, nParticleTypes));
    network.getStructure().finalizeStructure();
    network.reset();
    this.uniqueParticleIDs = uniqueParticleIDs;
    this.epTracker = new EfficiencyPurityTracker(uniqueParticleIDs);
    this.trainingSet = new BasicMLDataSet();
  }



  public void addTrainingEventsFromFile(String filename, int nEvents) throws IOException {
    PidTestDataReader reader = new PidTestDataReader(filename, nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      addTrainingEvent(particleID, varValues);
    }
  }



  public void addTrainingEvent(int particleID, ArrayList<Double> vars) {
    ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
    desiredOutput.add(uniqueParticleIDs.indexOf(particleID), 1.0);

    BasicMLData inputData = new BasicMLData(ArrayUtils.toPrimitive(vars.toArray(new Double[vars.size()])));
    BasicMLData idealData = new BasicMLData(ArrayUtils.toPrimitive(desiredOutput.toArray(new Double[desiredOutput.size()])));
    trainingSet.add(inputData, idealData);
  }
  public void addTrainingEvent(int particleID, Double... vars) {
    addTrainingEvent(particleID, new ArrayList<Double>(Arrays.asList(vars)));
  }



  public void train() {
    final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
    int epoch = 1;
    double lowError = 1;
    while((train.getError() > 0.05 || epoch == 1) && epoch < 4000) {
      train.iteration();
      if(epoch %1000 == 0 || epoch < 101) System.out.println(epoch + " " + train.getError());
      if(train.getError() < lowError) lowError = train.getError();
      epoch++;
    }
    System.out.println(lowError);
    train.finishTraining();
  }



  public ArrayList<Double> getNetworkOutput(ArrayList<Double> vars) {
    BasicMLData inputdata = new BasicMLData(ArrayUtils.toPrimitive(vars.toArray(new Double[vars.size()])));
    final MLData outputdata = network.compute(inputdata);
    ArrayList<Double> output = new ArrayList<>();
    for(int k = 0; k < nParticleTypes; k++) output.add(outputdata.getData(k));
    return output;
  }
  public ArrayList<Double> getNetworkOutput(Double... vars) {
    return getNetworkOutput(new ArrayList<Double>(Arrays.asList(vars)));
  }



  public void testOnFile(String filename, int nEvents) throws IOException {
    PidTestDataReader reader = new PidTestDataReader(filename, nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      testWithEvent(particleID, varValues);
    }
  }



  public void testWithEvent(int particleID, ArrayList<Double> vars) {
    int trueID = uniqueParticleIDs.indexOf(particleID);
    ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
    desiredOutput.add(trueID, 1.0);
    ArrayList<Double> output = getNetworkOutput(vars);

    double high1 = Double.MIN_VALUE;
    double high2 = Double.MIN_VALUE;
    Integer highIndex = null;

    for(int k = 0; k < uniqueParticleIDs.size(); k++){
      if (output.get(k) > high1){ 
        high2 = high1;
        high1 = output.get(k);
        highIndex = k;
      }
      else if (output.get(k) > high2) high2 = output.get(k);
  }
      epTracker.trackActualPredicted(uniqueParticleIDs.get(trueID), uniqueParticleIDs.get(highIndex), ConfidenceLevel.HIGH);


  }
  public void testWithEvent(int particleID, Double... vars) {
    testWithEvent(particleID, new ArrayList<Double>(Arrays.asList(vars)));
  }



  public void shutdown() {
    Encog.getInstance().shutdown();
  }



  public static void main(String[] args) throws IOException {
    int npart = 4;
    int nvar = 6;
    ArrayList<Integer> hidden = new ArrayList<>();
    hidden.add(7);
    System.out.println(hidden);
    ArrayList<Integer> pids = new ArrayList<>();
    pids.add(211);
    pids.add(2212);
    pids.add(321);
    pids.add(-11);
    EncogPid epid = new EncogPid(npart, nvar , hidden, pids);
    epid.addTrainingEventsFromFile(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-327307.txt", 6536);
    epid.train();
    epid.testOnFile(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-327307.txt", 10000);

    System.out.println("index -> pid map: " + pids);

    for(int pid : pids) {
      System.out.println("Efficiency Particle " + pid + " = " + epid.epTracker.getEfficiency(pid));
      System.out.println("Purity Particle " + pid + " = " + epid.epTracker.getPurity(pid));
      System.out.println("");
    }
    for (int k = 0; k < npart; k++){
      System.out.println(epid.epTracker.totalOccurances.get(k));
      System.out.println(epid.epTracker.nHighCon_corr.get(k));
      System.out.println(epid.epTracker.nHighCon_incorr.get(k));
      System.out.println("");
    }
    epid.shutdown();
  }

}

//Put in HGH LOW etc confidence measure
//make something to go through the output of the network and see highest and second highest number. Subtract and set a number of what is high/low/tie/etc.//
