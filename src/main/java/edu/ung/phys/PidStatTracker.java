package edu.ung.phys;

import edu.ung.phys.basicSim.*;
import edu.ung.phys.plot.Histogram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class PidStatTracker {

  public ArrayList<Axis> axes = new ArrayList<>();
  public int nParticleTypes;
  public int nVars;
  public double eBarScale;
  public ArrayList<Histogram> particleHistos = new ArrayList<>();
  public EfficiencyPurityTracker epTracker;
  public ArrayList<Integer> pids;



  public PidStatTracker(ArrayList<Integer> pids, double eBarScale) {
    this.pids = pids;
    this.nParticleTypes = pids.size();
    this.eBarScale = eBarScale;
    this.nVars = 0;
    this.epTracker = new EfficiencyPurityTracker(pids);
  }



  public void addAxis(Axis axis) {
    axes.add(axis);
    nVars++;
  }



  public void initializeHistos() {
    for(int k = 0; k < nParticleTypes; k++) particleHistos.add(new Histogram(axes));
  }



  public void trainOnFile(String filename, int n) throws IOException {
    PidTestDataReader reader = new PidTestDataReader(filename, n);
    for(int j = 0; j < n; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> vars = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) vars.add(Double.parseDouble(values[k]));
      trainWithEvent(particleID, vars);
    }
  }



  public void trainWithEvent(int particleID, ArrayList<Double> vars) {
    particleHistos.get(pids.indexOf(particleID)).fill(vars);
  }
  public void trainWithEvent(int particleID, Double... vars) {
    trainWithEvent(particleID, new ArrayList<Double>(Arrays.asList(vars)));
  }



  public void testOnFile(String filename, int n) throws IOException {
    PidTestDataReader testreader = new PidTestDataReader(filename, n);
    for(int j = 0; j < n; j++) {
      String[] values = testreader.getNextEvent();
      int trueID = Integer.parseInt(values[0]);
      ArrayList<Double> vars = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) vars.add(Double.parseDouble(values[k]));
      testWithEvent(trueID, vars);
    }
  }



  public void testWithEvent(int trueID, ArrayList<Double> vars) {
    PredictionConfidencePair pcPair = getPredictionConfidencePair(vars);
    epTracker.trackActualPredicted(trueID, pcPair.prediction, pcPair.confidence);
  }
  public void testWithEvent(int trueID, Double... vars) {
    testWithEvent(trueID, new ArrayList<Double>(Arrays.asList(vars)));
  }


  public PredictionConfidencePair getPredictionConfidencePair(ArrayList<Double> vars) {
    ArrayList<Integer> binIndices = particleHistos.get(0).getBinIndices(vars);

      HashMap<Integer, Integer> countsForParticleIndex = new HashMap<Integer, Integer>();
      for(int k = 0; k< nParticleTypes; k++) countsForParticleIndex.put(k, particleHistos.get(k).getBinContent(binIndices));

      Integer pindex1;
      //Integer pindex2;
      //pindex1 = pindex2 = null;
      pindex1 = null;
      int high1, high2;
      high1 = high2 = Integer.MIN_VALUE;
      for(Entry<Integer, Integer> entry : countsForParticleIndex.entrySet()) {
        int pindex = entry.getKey();
        int counts = entry.getValue();
        if(counts > high1) {
          high2 = high1;
          //pindex2 = pindex1;
          high1 = counts;
          pindex1 = pindex;
        }
        else if(counts > high2) {
          high2 = counts;
          //pindex2 = pindex;
        }
      }

      double eBar1 = eBarScale*Math.sqrt(high1);
      double eBar2 = eBarScale*Math.sqrt(high2);

      if(high1 == 0) return new PredictionConfidencePair(null, ConfidenceLevel.UNKNOWN);
      else if(high1 == high2) return new PredictionConfidencePair(null, ConfidenceLevel.TIE);
      else if(high1 - eBar1 < high2 + eBar2) return new PredictionConfidencePair(pids.get(pindex1), ConfidenceLevel.LOW);
      else if(high1 - eBar1 > high2 + eBar2) return new PredictionConfidencePair(pids.get(pindex1), ConfidenceLevel.HIGH);
      else {
        System.out.println("Problem in PidStatTracker");
        return null;
      }
  }
  public PredictionConfidencePair getPredictionConfidencePair(Double... vars) {
    return getPredictionConfidencePair(new ArrayList<Double>(Arrays.asList(vars)));
  }



  public void printTestResults() {
    for(int pid : pids) {
      System.out.println("Efficiency Particle " + pid + " = " + epTracker.getEfficiency(pid));
      System.out.println("Purity Particle " + pid + " = " + epTracker.getPurity(pid));
      System.out.println("");
    }
  }


}
