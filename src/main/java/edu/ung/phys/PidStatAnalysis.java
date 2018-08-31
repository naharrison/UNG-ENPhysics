package edu.ung.phys;

import java.io.IOException;

public class PidStatAnalysis {

  public static void main(String[] args) throws IOException {

    int nParticleTypes = 3;
    double eBarScale = 0.0;

    PidStatTracker tracker = new PidStatTracker(nParticleTypes, eBarScale);
    tracker.addAxis(new Axis(15, 0, 1));
    tracker.addAxis(new Axis(15, 0, 1));
    tracker.addAxis(new Axis(15, 0, 1));
    tracker.addAxis(new Axis(15, 0, 1));
    tracker.addAxis(new Axis(15, 0, 1));
    tracker.initializeHistos();

    tracker.train(System.getenv("DATASAMPLES") + "/pid_toymodel_5000.txt", 5000);
    tracker.test(System.getenv("DATASAMPLES") + "/pid_toymodel_5000.txt", 5000);
    tracker.printResults();

  }


}
