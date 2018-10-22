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
import org.encog.ml.data.MLData;
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
  public NEATPopulation pop;
  public NEATNetwork network;
  public EfficiencyPurityTracker epTracker;



  public NeatPid(int nVars, ArrayList<Integer> uniqueParticleIDs) {
    this.nVars = nVars;
    this.uniqueParticleIDs = uniqueParticleIDs;
    this.trainingSet = new BasicMLDataSet();
    this.nParticleTypes = uniqueParticleIDs.size();
    this.epTracker = new EfficiencyPurityTracker(uniqueParticleIDs);
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



  public void initializePopulation(int n) {
    pop = new NEATPopulation(nVars, nParticleTypes, n);
    pop.setInitialConnectionDensity(1.0); // not required, but speeds training
    pop.reset();
  }



  public void runEvolution(double err) {
    CalculateScore score = new TrainingSetScore(trainingSet);
    final EvolutionaryAlgorithm trainer = NEATUtil.constructNEATTrainer(pop, score);
    do {
      trainer.iteration();
      int epoch = trainer.getIteration();
      if(epoch == 1 || epoch % 100 == 0) System.out.println("Epoch #" + epoch + " Error:" + trainer.getError()+ ", Species:" + pop.getSpecies().size());
    } while(trainer.getError() > err);
    System.out.println("Epoch #" + trainer.getIteration() + " Error:" + trainer.getError()+ ", Species:" + pop.getSpecies().size());
    network = (NEATNetwork) trainer.getCODEC().decode(trainer.getBestGenome());
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
    int trueIDindex = uniqueParticleIDs.indexOf(particleID);
    ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(nParticleTypes-1, 0.0));
    desiredOutput.add(trueIDindex, 1.0);
    ArrayList<Double> output = getNetworkOutput(vars);

    double high = -1.0*Double.MAX_VALUE;
    Integer highIndex = null;
    for(int k = 0; k < uniqueParticleIDs.size(); k++) {
      if (output.get(k) > high) {
        high = output.get(k);
        highIndex = k;
      }
    }
    epTracker.trackActualPredicted(uniqueParticleIDs.get(trueIDindex), uniqueParticleIDs.get(highIndex), ConfidenceLevel.HIGH);
  }
  public void testWithEvent(int particleID, Double... vars) {
    testWithEvent(particleID, new ArrayList<Double>(Arrays.asList(vars)));
  }



  public static void main(String[] args) throws IOException {
    org.apache.commons.lang.time.StopWatch sw = new org.apache.commons.lang.time.StopWatch();
    sw.start();

    int nvar = 6;
    ArrayList<Integer> pids = new ArrayList<>(Arrays.asList(-11, 211, 321, 2212));

    NeatPid npid = new NeatPid(nvar, pids);
    npid.addTrainingEventsFromFile(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-649951.txt", 12000);
    npid.initializePopulation(200);
    npid.runEvolution(0.03);

    npid.testOnFile(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-649951.txt", 100000);

    System.out.println("");

    for(int pid : pids) {
      System.out.println("Efficiency of Particle " + pid + " = " + npid.epTracker.getEfficiency(pid));
      System.out.println("Purity of Particle " + pid + " = " + npid.epTracker.getPurity(pid));
      System.out.println("");
    }
    for (int k = 0; k < pids.size(); k++){
      System.out.println("pid " + pids.get(k));
      System.out.println("actual total = " + npid.epTracker.totalOccurances.get(k));
      System.out.println("# in sample = " + npid.epTracker.nHighCon_corr.get(k));
      System.out.println("others in sample = " + npid.epTracker.nHighCon_incorr.get(k));
      System.out.println("");
    }

    Encog.getInstance().shutdown();
    sw.stop();
    System.out.println("time: " + sw.toString());
  }


}
