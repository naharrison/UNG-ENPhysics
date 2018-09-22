package edu.ung.phys;

import edu.ung.phys.basicSim.*;
import edu.ung.phys.plot.Histogram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class PidStatTracker {

  public ArrayList<Axis> axes = new ArrayList<>();
  public int nParticleTypes;
  public int nVars;
  public double eBarScale;
  public ArrayList<Histogram> particleHistos = new ArrayList<>();
  public ArrayList<Integer> totalOccurances, nUnknown, nTies, nLowCon_corr, nLowCon_incorr, nHighCon_corr, nHighCon_incorr;
  public HashMap<Integer, Integer> indicesForParticles;


  public PidStatTracker(int nParticleTypes, double eBarScale, HashMap<Integer, Integer> indicesForParticles) {
    this.nParticleTypes = nParticleTypes;
    this.eBarScale = eBarScale;
    this.nVars = 0;
    this.indicesForParticles = indicesForParticles;
    totalOccurances = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nUnknown = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nTies = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nLowCon_corr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nLowCon_incorr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nHighCon_corr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
    nHighCon_incorr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
  }


  public void addAxis(Axis axis) {
    axes.add(axis);
    nVars++;
  }


  public void initializeHistos() {
    for(int k = 0; k < nParticleTypes; k++) particleHistos.add(new Histogram(axes));
  }


  public void train(String filename, int n) throws IOException {
    PidTestDataReader reader = new PidTestDataReader(filename, n);
    for(int j = 0; j < n; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      ArrayList<Double> vars = new ArrayList<>();
      for(int k = 1; k <= nVars; k++) vars.add(Double.parseDouble(values[k]));
      particleHistos.get(indicesForParticles.get(particleID)).fill(vars);
    }
  }


  public void test(String filename, int n) throws IOException {
    PidTestDataReader testreader = new PidTestDataReader(filename, n);

    for(int j = 0; j < n; j++) {
      String[] values = testreader.getNextEvent();
      int trueID = Integer.parseInt(values[0]);
      ArrayList<Double> vars =new ArrayList<>();
      for(int k = 1; k <= nVars; k++) vars.add(Double.parseDouble(values[k]));

      ArrayList<Integer> binIndices = particleHistos.get(0).getBinIndices(vars);

      totalOccurances.set(indicesForParticles.get(trueID), totalOccurances.get(indicesForParticles.get(trueID)) + 1);
      HashMap<Integer, Integer> countsForParticles = new HashMap<Integer, Integer>();
      for(int k = 0; k< nParticleTypes; k++) countsForParticles.put(k, particleHistos.get(k).getBinContent(binIndices));

      int pid1;
      //int pid2;
      //pid1 = pid2 = -1;
      pid1 = -1;
      int high1, high2;
      high1 = high2 = Integer.MIN_VALUE;
      for(Entry<Integer, Integer> entry : countsForParticles.entrySet()) {
        int pid = entry.getKey();
        int counts = entry.getValue();
        if(counts > high1) {
          high2 = high1;
          //pid2 = pid1;
          high1 = counts;
          pid1 = pid;
        }
        else if(counts > high2) {
          high2 = counts;
          //pid2 = pid;
        }
      }

      double eBar1 = eBarScale*Math.sqrt(high1);
      double eBar2 = eBarScale*Math.sqrt(high2);

      if(high1 == 0) nUnknown.set(indicesForParticles.get(trueID), nUnknown.get(indicesForParticles.get(trueID)) + 1);
      else if(high1 == high2) nTies.set(indicesForParticles.get(trueID), nTies.get(indicesForParticles.get(trueID)) + 1);
      else if(high1 - eBar1 < high2 + eBar2 && pid1 == trueID) nLowCon_corr.set(indicesForParticles.get(trueID), nLowCon_corr.get(indicesForParticles.get(trueID)) + 1);
      else if(high1 - eBar1 < high2 + eBar2 && pid1 != trueID) nLowCon_incorr.set(indicesForParticles.get(trueID), nLowCon_incorr.get(indicesForParticles.get(trueID)) + 1);
      else if(high1 - eBar1 > high2 + eBar2 && pid1 == trueID) nHighCon_corr.set(indicesForParticles.get(trueID),  nHighCon_corr.get(indicesForParticles.get(trueID)) + 1);
      else if(high1 - eBar1 > high2 + eBar2 && pid1 != trueID) nHighCon_incorr.set(indicesForParticles.get(trueID), nHighCon_incorr.get(indicesForParticles.get(trueID)) + 1);
    }
  }


  public void printResults() {
    for(int n = 0; n < nParticleTypes; n++) {
      System.out.println("Efficiency Particle " + n + " = " + getEfficiency(n));
      System.out.println("Purity Particle " + n + " = " + getPurity(n));
      System.out.println("");
    }
  }


  public double getEfficiency(int pIndex) {
    return (double) nHighCon_corr.get(pIndex)/totalOccurances.get(pIndex);
  }


  public double getPurity(int pIndex) {
    return (double) nHighCon_corr.get(pIndex)/(nHighCon_corr.get(pIndex)+nHighCon_incorr.get(pIndex));
  }


}
