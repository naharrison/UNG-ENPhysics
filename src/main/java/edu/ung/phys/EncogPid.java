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

public class EncogPid {

  public static void main(String[] args) throws IOException {

    int nParticleTypes = 3;
    int nVars = 5;
    ArrayList<Integer> uniqueParticleIDs = new ArrayList<>();

    BasicNetwork network = new BasicNetwork();
    network.addLayer(new BasicLayer(null, true, nVars));
    network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 5));
    network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 5));
    network.addLayer(new BasicLayer(new ActivationSigmoid(), false, nParticleTypes));
    network.getStructure().finalizeStructure();
    network.reset();

    MLDataSet trainingSet = new BasicMLDataSet();
    int nEvents = 50;
    PidTestDataReader reader = new PidTestDataReader(System.getenv("DATASAMPLES")+"/pid_toymodel_5000.txt", nEvents);
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> varValues = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) varValues.add(Double.parseDouble(values[k]));
      if(!uniqueParticleIDs.contains(particleID)) uniqueParticleIDs.add(particleID);
      ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
      desiredOutput.add(uniqueParticleIDs.indexOf(particleID), 1.0);

      BasicMLData inputData = new BasicMLData(ArrayUtils.toPrimitive(varValues.toArray(new Double[varValues.size()])));
      BasicMLData idealData = new BasicMLData(ArrayUtils.toPrimitive(desiredOutput.toArray(new Double[desiredOutput.size()])));
      trainingSet.add(inputData, idealData);
    }

    System.out.println("Training...");
    final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
    int epoch = 1;
    while(train.getError() > 0.01 || epoch == 1) {
      train.iteration();
      System.out.println(epoch + " " + train.getError());
      epoch++;
    }
    train.finishTraining();
    System.out.println("Done training.");
    System.out.println("");

    // test the network using same data set (should use a different one)
    System.out.println("Testing:");
    for(MLDataPair pair : trainingSet) {
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
    System.out.println("index -> pid map: " + uniqueParticleIDs);
    System.out.println("");

    Encog.getInstance().shutdown();

  }

}
