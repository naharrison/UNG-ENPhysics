package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

public class PidStatAnalysis {

  public static void main(String[] args) throws IOException {

    int nParticleTypes = 4;
    
    HashMap<Integer, Integer> indicesForParticles = new HashMap<Integer, Integer>();
    indicesForParticles.put(211, 0);
    indicesForParticles.put(-11, 1);
    indicesForParticles.put(2212, 2);
    indicesForParticles.put(321, 3);

    ArrayList<Double> eBarScales = new ArrayList<>();
    ArrayList<PidStatTracker> trackers = new ArrayList<>();

    int nEBarVariations = 4;
    int nBinVariations = 4;
    for(int e = 0; e < nEBarVariations; e++) {
      for(int b = 0; b < nBinVariations; b++) {
        int index = nBinVariations*e + b;
        eBarScales.add((e+1)*0.25);
        trackers.add(new PidStatTracker(nParticleTypes, eBarScales.get(index), indicesForParticles));
        trackers.get(index).addAxis(new Axis((b+1)*3, 0.1, 4.5));
        trackers.get(index).addAxis(new Axis((b+1)*3, 0, 2.5));
        trackers.get(index).addAxis(new Axis(1, -2000, 400));
        trackers.get(index).addAxis(new Axis(1, 0, 300));
        trackers.get(index).addAxis(new Axis((b+1)*3, 0, 0.75));
        trackers.get(index).addAxis(new Axis((b+1)*3, 0, 1.3));
        trackers.get(index).initializeHistos();
      }
    }
    
    // tracker index 16:
    //trackers.add(new PidStatTracker(nParticleTypes, 0.75, indicesForParticles));
    //trackers.get(16).addAxis(new Axis(5, 0, 1));
    //trackers.get(16).addAxis(new Axis(5, 0, 1));
    //trackers.get(16).addAxis(new Axis(5, 0, 1));
    //trackers.get(16).addAxis(new Axis(5, 0, 1));
    //trackers.get(16).addAxis(new Axis(5, 0, 1));
    //trackers.get(16).addAxis(new Axis(5, 0, 1));
    //trackers.get(16).initializeHistos();
    

    TCanvas can = new TCanvas("can", 1000, 600);
    can.divide(nParticleTypes, 1);

    ArrayList<GraphErrors> rocs = new ArrayList<>();
    for(int k = 0; k < nParticleTypes; k++) {
        rocs.add(new GraphErrors());
    }

    for(int t = 0; t < trackers.size(); t++) {
      trackers.get(t).train(System.getenv("DATASAMPLES") + "/e1f/Pid-Data/pidout-6537.txt", 6537);
      trackers.get(t).test(System.getenv("DATASAMPLES") + "/e1f/Pid-Data/pidout-327307.txt", 327307);
      System.out.println("");
      System.out.println("Results for tracker " + t + ":");
      trackers.get(t).printResults();
      System.out.println("");

      for(int k = 0; k < nParticleTypes; k++) {
        rocs.get(k).addPoint(trackers.get(t).getEfficiency(k), trackers.get(t).getPurity(k), 0, 0);
      }
    }

    for(int k = 0; k < nParticleTypes; k++) {
      can.cd(k);
      can.draw(rocs.get(k));
    }

  }

}
