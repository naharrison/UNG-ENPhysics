/* 
 * 
 * NeuroEvolution of Augmenting Topologies
 * https://www.heatonresearch.com/encog/neat/
 * https://towardsdatascience.com/introduction-to-genetic-algorithms-including-example-code-e396e98d8bf3
 * 
 */

package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;

import org.encog.Encog;
import org.encog.ml.CalculateScore;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.ea.train.EvolutionaryAlgorithm;
import org.encog.neural.neat.NEATUtil;
import org.encog.neural.neat.NEATNetwork;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.util.simple.EncogUtility;

import edu.ung.phys.basicSim.PidTestDataReader;


public class NeatPid {

  public MLDataSet trainingSet;
  public int nVars, nParticleTypes;
  public ArrayList<Integer> uniqueParticleIDs;


  public NeatPid(int nVars, ArrayList<Integer> uniqueParticleIDs) {
    this.nVars = nVars;
    this.uniqueParticleIDs = uniqueParticleIDs;
    this.trainingSet = new BasicMLDataSet();
    this.nParticleTypes = uniqueParticleIDs.size();
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


  public static void main(String[] args) throws IOException {
    int nvar = 6;
    ArrayList<Integer> pids = new ArrayList<>(Arrays.asList(-11, 211, 321, 2212));

    NeatPid npid = new NeatPid(nvar, pids);
    npid.addTrainingEventsFromFile(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-649951.txt", 7000);

    NEATPopulation pop = new NEATPopulation(nvar, pids.size(), 50);
    pop.setInitialConnectionDensity(1.0); // not required, but speeds training
    pop.reset();

    CalculateScore score = new TrainingSetScore(npid.trainingSet);

    final EvolutionaryAlgorithm train = NEATUtil.constructNEATTrainer(pop, score);

    do {
      train.iteration();
      System.out.println("Epoch #" + train.getIteration() + " Error:" + train.getError()+ ", Species:" + pop.getSpecies().size());
    } while(train.getError() > 0.04);
    
    NEATNetwork network = (NEATNetwork)train.getCODEC().decode(train.getBestGenome());
    
    // test the neural network
    System.out.println("Neural Network Results:");
    EncogUtility.evaluate(network, npid.trainingSet);

    Encog.getInstance().shutdown();
  }


}
