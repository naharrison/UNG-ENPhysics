package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.encog.Encog;

import edu.ung.phys.basicSim.PidTestDataReader;


public class NeatPidAnalysis {


  public static void main(String[] args) throws IOException {

    int nvar = 6;
    ArrayList<Integer> pids = new ArrayList<>(Arrays.asList(-11, 211, 321, 2212));
    EfficiencyPurityTracker epTracker = new EfficiencyPurityTracker(pids);

    // create 8 separate NEAT networks, depending on if nphe, ein, and eout are zero or non-zero
    ArrayList<ArrayList<ArrayList<NeatPid>>> neats = new ArrayList<>();
    neats.add(new ArrayList<>());
    neats.get(0).add(new ArrayList<>());
    neats.get(0).get(0).add(new NeatPid(nvar - 3, pids));
    neats.get(0).get(0).add(new NeatPid(nvar - 2, pids));
    neats.get(0).add(new ArrayList<>());
    neats.get(0).get(1).add(new NeatPid(nvar - 2, pids));
    neats.get(0).get(1).add(new NeatPid(nvar - 1, pids));
    neats.add(new ArrayList<>());
    neats.get(1).add(new ArrayList<>());
    neats.get(1).get(0).add(new NeatPid(nvar - 2, pids));
    neats.get(1).get(0).add(new NeatPid(nvar - 1, pids));
    neats.get(1).add(new ArrayList<>());
    neats.get(1).get(1).add(new NeatPid(nvar - 1, pids));
    neats.get(1).get(1).add(new NeatPid(nvar - 0, pids));

    int nEvents = 8000;
    PidTestDataReader reader = new PidTestDataReader(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-649951.txt", nEvents);
    
    for(int j = 0; j < nEvents; j++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      double p = Double.parseDouble(values[1]);
      double theta = Double.parseDouble(values[2]);
      double beta = Double.parseDouble(values[3]);
      double nphe = Double.parseDouble(values[4]);
      double ein = Double.parseDouble(values[5]);
      double eout = Double.parseDouble(values[6]);

      int nonZeroNphe = 0;
      if(nphe > Double.MIN_VALUE) nonZeroNphe = 1;
      int nonZeroEin = 0;
      if(ein > Double.MIN_VALUE) nonZeroEin = 1;
      int nonZeroEout = 0;
      if(eout > Double.MIN_VALUE) nonZeroEout = 1;

      ArrayList<Double> nonZeroValues = new ArrayList<>();
      nonZeroValues.add(p);
      nonZeroValues.add(theta);
      nonZeroValues.add(beta);
      if(nonZeroNphe == 1) nonZeroValues.add(nphe);
      if(nonZeroEin == 1) nonZeroValues.add(ein);
      if(nonZeroEout == 1) nonZeroValues.add(eout);

      neats.get(nonZeroNphe).get(nonZeroEin).get(nonZeroEout).addTrainingEvent(particleID, nonZeroValues);
    }

    for(int i = 0; i < 2; i++) {
      for(int j = 0; j < 2; j++) {
        for(int k = 0; k < 2; k++) {
          System.out.println(i + " " + j + " " + k);
          neats.get(i).get(j).get(k).initializePopulation(200);
          neats.get(i).get(j).get(k).runEvolution(0.045);
          System.out.println("");
    }}}


    // %%%%% Test %%%%% //
    nEvents = 100000;
    PidTestDataReader reader2 = new PidTestDataReader(System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-649951.txt", nEvents);

    for(int ev = 0; ev < nEvents; ev++) {
      String[] values = reader.getNextEvent();
      int particleID = Integer.parseInt(values[0]);
      double p = Double.parseDouble(values[1]);
      double theta = Double.parseDouble(values[2]);
      double beta = Double.parseDouble(values[3]);
      double nphe = Double.parseDouble(values[4]);
      double ein = Double.parseDouble(values[5]);
      double eout = Double.parseDouble(values[6]);

      int nonZeroNphe = 0;
      if(nphe > Double.MIN_VALUE) nonZeroNphe = 1;
      int nonZeroEin = 0;
      if(ein > Double.MIN_VALUE) nonZeroEin = 1;
      int nonZeroEout = 0;
      if(eout > Double.MIN_VALUE) nonZeroEout = 1;

      ArrayList<Double> nonZeroValues = new ArrayList<>();
      nonZeroValues.add(p);
      nonZeroValues.add(theta);
      nonZeroValues.add(beta);
      if(nonZeroNphe == 1) nonZeroValues.add(nphe);
      if(nonZeroEin == 1) nonZeroValues.add(ein);
      if(nonZeroEout == 1) nonZeroValues.add(eout);

      ArrayList<Double> networkOutput = neats.get(nonZeroNphe).get(nonZeroEin).get(nonZeroEout).getNetworkOutput(nonZeroValues);

      // check accuracy of network prediction, tally result
      int trueIDindex = pids.indexOf(particleID);
      ArrayList<Double> desiredOutput = new ArrayList<Double>(Collections.nCopies(pids.size()-1, 0.0));
      desiredOutput.add(trueIDindex, 1.0);

      double high = -1.0*Double.MAX_VALUE;
      Integer highIndex = null;
      for(int k = 0; k < pids.size(); k++) {
        if (networkOutput.get(k) > high) {
          high = networkOutput.get(k);
          highIndex = k;
        }
      }
      epTracker.trackActualPredicted(pids.get(trueIDindex), pids.get(highIndex), ConfidenceLevel.HIGH);
    }

    // print results
    for(int pid : pids) {
      System.out.println("Efficiency of Particle " + pid + " = " + epTracker.getEfficiency(pid));
      System.out.println("Purity of Particle " + pid + " = " + epTracker.getPurity(pid));
      System.out.println("");
    }
    for (int k = 0; k < pids.size(); k++){
      System.out.println("pid " + pids.get(k));
      System.out.println("actual total = " + epTracker.totalOccurances.get(k));
      System.out.println("# in sample = " + epTracker.nHighCon_corr.get(k));
      System.out.println("others in sample = " + epTracker.nHighCon_incorr.get(k));
      System.out.println("");
    }

    Encog.getInstance().shutdown();

  }


}
