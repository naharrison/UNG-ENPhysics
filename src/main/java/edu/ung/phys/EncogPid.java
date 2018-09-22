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
  public EfficiencyPurityTracker tracker;
  public MLDataSet trainingSet, testingSet;


  public EncogPid(int nParticleTypes, int nVars, ArrayList<Integer> nNeuronsInHiddenLayers, ArrayList<Integer> uniqueParticleIDs) {
    this.nParticleTypes = nParticleTypes;
    this.nVars = nVars;
    this.nNeuronsInHiddenLayers = nNeuronsInHiddenLayers;
    network = new BasicNetwork();
    network.addLayer(new BasicLayer(null, true, nVars));
    for(Integer i : nNeuronsInHiddenLayers) network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
    network.addLayer(new BasicLayer(new ActivationSigmoid(), false, nParticleTypes));
    network.getStructure().finalizeStructure();
    network.reset();
    this.uniqueParticleIDs = uniqueParticleIDs;
    tracker = new EfficiencyPurityTracker(nParticleTypes, uniqueParticleIDs);
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
    while(train.getError() > 0.05 || epoch == 1) {
      train.iteration();
      if(epoch %1000 == 0 || epoch < 101) System.out.println(epoch + " " + train.getError());
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
    ArrayList<Integer> hidden = new ArrayList<>();
    hidden.add(7);
    System.out.println(hidden);
    ArrayList<Integer> pids = new ArrayList<>();
    pids.add(211);
    pids.add(2212);
    pids.add(321);
    pids.add(-11);
    EncogPid epid = new EncogPid(npart, nvar , hidden, pids);
    epid.train(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-6537.txt", 6536);
    epid.test(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-327307.txt", 10000);

    System.out.println("index -> pid map: " + epid.uniqueParticleIDs);

    epid.shutdown();
  }

}

//Put in HGH LOW etc confidence measure
//make something to go through the output of the network and see highest and second highest number. Subtract and set a number of what is high/low/tie/etc.//
