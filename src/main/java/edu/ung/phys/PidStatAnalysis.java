package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

public class PidStatAnalysis {

  public static int nParticleTypes;
  public static ArrayList<Integer> pids;
  public static ArrayList<PidStatTracker> statTrackers;
  public static ArrayList<GraphErrors> rocs;


  public static void init() {
    pids = new ArrayList<>();
    pids.add(-11);
    pids.add(211);
    pids.add(321);
    pids.add(2212);
    nParticleTypes = pids.size();

    statTrackers = new ArrayList<>();

    rocs = new ArrayList<>();
    for(int k = 0; k < nParticleTypes; k++) {
        rocs.add(new GraphErrors());
    }

    ArrayList<Double> eBarScales = new ArrayList<>();

    int nEBarVariations = 2;
    int nBinVariations = 2;
    for(int e = 0; e < nEBarVariations; e++) {
      for(int b = 0; b < nBinVariations; b++) {
        int index = nBinVariations*e + b;
        eBarScales.add((e+1)*0.25);
        statTrackers.add(new PidStatTracker(pids, eBarScales.get(index)));
        statTrackers.get(index).addAxis(new Axis((b+1)*4, 0.21, 5.3));
        statTrackers.get(index).addAxis(new Axis((b+1)*4, 0.088, 2.22));
        statTrackers.get(index).addAxis(new Axis((b+1)*4, 0, 1.5));
        statTrackers.get(index).addAxis(new Axis((b+1)*4, 0, 350));
        statTrackers.get(index).addAxis(new Axis((b+1)*4, 0, 0.9));
        statTrackers.get(index).addAxis(new Axis((b+1)*4, 0, 1.1));
        statTrackers.get(index).initializeHistos();
      }
    }
  }



  public static void trainAndTest() throws IOException {
    for(int t = 0; t < statTrackers.size(); t++) {
      statTrackers.get(t).trainOnFile(System.getenv("DATASAMPLES") + "/e1f/Pid-Data/pidout-649951.txt", 649951);
      statTrackers.get(t).testOnFile(System.getenv("DATASAMPLES") + "/e1f/Pid-Data/pidout-649951.txt", 649951);
      System.out.println("");
      System.out.println("Results for tracker " + t + ":");
      statTrackers.get(t).printTestResults();
      System.out.println("");

      for(int k = 0; k < nParticleTypes; k++) {
        rocs.get(k).addPoint(statTrackers.get(t).epTracker.getEfficiency(pids.get(k)), statTrackers.get(t).epTracker.getPurity(pids.get(k)), 0, 0);
      }
    }
  }



  public static void main(String[] args) throws IOException {
    init();

    TCanvas can = new TCanvas("can", 1000, 600);
    can.divide(nParticleTypes, 1);

    trainAndTest();

    for(int k = 0; k < nParticleTypes; k++) {
      rocs.get(k).addPoint(-0.1, -0.1, 0.0, 0.0); // hack to set axis ranges to avoid plotting bug
      rocs.get(k).addPoint(1.1, 1.1, 0.0, 0.0); // hack to set axis ranges to avoid plotting bug

      can.cd(k);
      can.draw(rocs.get(k));
    }
  }

}
