package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

public class PidStatAnalysis {

  public static void main(String[] args) throws IOException {

    int nParticleTypes = 3;

    ArrayList<Double> eBarScales = new ArrayList<>();
    ArrayList<PidStatTracker> trackers = new ArrayList<>();

    int nEBarVariations = 4;
    int nBinVariations = 4;
    for(int e = 0; e < nEBarVariations; e++) {
      for(int b = 0; b < nBinVariations; b++) {
        int index = nBinVariations*e + b;
        eBarScales.add((e+1)*0.25);
        trackers.add(new PidStatTracker(nParticleTypes, eBarScales.get(index)));
        trackers.get(index).addAxis(new Axis((b+1)*5, 0, 1));
        trackers.get(index).addAxis(new Axis((b+1)*5, 0, 1));
        trackers.get(index).addAxis(new Axis((b+1)*5, 0, 1));
        trackers.get(index).addAxis(new Axis((b+1)*5, 0, 1));
        trackers.get(index).addAxis(new Axis((b+1)*5, 0, 1));
        trackers.get(index).initializeHistos();
      }
    }

    TCanvas can = new TCanvas("can", 1000, 600);
    can.divide(nParticleTypes, 1);

    ArrayList<GraphErrors> rocs = new ArrayList<>();
    for(int k = 0; k < nParticleTypes; k++) {
        rocs.add(new GraphErrors());
    }

    for(int t = 0; t < eBarScales.size(); t++) {
      trackers.get(t).train(System.getenv("DATASAMPLES") + "/pid_toymodel_5000.txt", 5000);
      trackers.get(t).test(System.getenv("DATASAMPLES") + "/pid_toymodel_5000.txt", 5000);
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
