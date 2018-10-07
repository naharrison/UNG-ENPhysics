package edu.ung.phys;

import edu.ung.phys.basicSim.*;

import java.io.IOException;
import java.util.ArrayList;

import org.jlab.groot.ui.TCanvas;
import org.jlab.groot.math.F1D;
import org.jlab.groot.data.H2F;

public class CutBasedPid {


  public static double pMin, pMax, bMin, bMax;
  public static F1D posH, posL, piH, piL, kH, kL, prH, prL;
  public static EfficiencyPurityTracker epTracker;
  public static H2F bvp, bvp_pos, bvp_pi, bvp_k, bvp_pr;



  public static void init() {
    pMin = 0.15;
    pMax = 5.0;
    bMin = 0.4;
    bMax = 1.15;

    posH = new F1D("posH", "1.075 + 0.0*x", pMin, 0.75);
    posH.setLineWidth(3);
    posL = new F1D("posL", "0.91 + 0.333*x", pMin, 0.75);
    posL.setLineWidth(3);
    piH  = new F1D("piH", "1.05 + 0.0*x", pMin + 0.1, pMax);
    piH.setLineWidth(3);
    piL  = new F1D("piL", "0.48*(sqrt(x*x/(0.14*0.14 + x*x)) + 0.98sqrt(x*x/(0.493*0.493 + x*x)))", pMin + 0.1, pMax);
    piL.setLineWidth(3);
    kH = new F1D("kH", "0.6666*(sqrt(x*x/(0.494*0.494 + x*x))) +  0.3333*(sqrt(x*x/(0.14*0.14 + x*x)))", pMin + 0.1, pMax);
    kH.setLineWidth(3);
    kL = new F1D("kL", "0.6666*(sqrt(x*x/(0.494*0.494 + x*x))) + 0.3333*(sqrt(x*x/(0.938*0.938 + x*x)))", pMin + 0.1, pMax);
    kL.setLineWidth(3);
    prH = new F1D("prH", "0.35*(sqrt(x*x/(0.938*0.938 + x*x))) + 0.7*(sqrt(x*x/(0.494*0.494 + x*x)))", pMin + 0.1, pMax);
    prH.setLineWidth(3);
    prL = new F1D("prL", "1.5*(sqrt(x*x/(0.938*0.938 + x*x))) - 0.5*(sqrt(x*x/(0.494*0.494 + x*x)))", pMin + 0.1, pMax);
    prL.setLineWidth(3);

    ArrayList<Integer> ids = new ArrayList<>();
    ids.add(-11);
    ids.add(211);
    ids.add(321);
    ids.add(2212);
    epTracker = new EfficiencyPurityTracker(ids);

    bvp = new H2F("bvp", "bvp", 200, 0.0, pMax, 200, bMin, bMax);
    bvp_pos = new H2F("bvp_pos", "bvp_pos", 200, 0.0, pMax, 200, bMin, bMax);
    bvp_pi = new H2F("bvp_pi", "bvp_pi", 200, 0.0, pMax, 200, bMin, bMax);
    bvp_k = new H2F("bvp_k", "bvp_k", 200, 0.0, pMax, 200, bMin, bMax);
    bvp_pr = new H2F("bvp_pr", "bvp_pr", 200, 0.0, pMax, 200, bMin, bMax);
  }



  public static void runAnalysis() throws IOException {
    int n = 649951;
    PidTestDataReader reader = new PidTestDataReader("/home/mflor9255/data-samples/e1f/Pid-Data/pidout-649951.txt", n);
    for(int k = 0; k < n; k++) {
      String[] values = reader.getNextEvent();
      int trueID = Integer.parseInt(values[0]);
      double p = Double.parseDouble(values[1]);
      double beta = Double.parseDouble(values[3]);
      bvp.fill(p, beta);
      if(trueID == -11) bvp_pos.fill(p, beta);
      else if(trueID == 211) bvp_pi.fill(p, beta);
      else if(trueID == 321) bvp_k.fill(p, beta);
      else if(trueID == 2212) bvp_pr.fill(p, beta);
      else System.out.println("CRAP!!!");

      ConfidenceLevel confidence = ConfidenceLevel.HIGH;
      int predictionID;
      if(beta < posH.evaluate(p) && beta > posL.evaluate(p)) predictionID = -11;
      else if(beta < piH.evaluate(p) && beta > piL.evaluate(p)) predictionID = 211;
      else if(beta < kH.evaluate(p) && beta > kL.evaluate(p)) predictionID = 321;
      else if(beta < prH.evaluate(p) && beta > prL.evaluate(p)) predictionID = 2212;
      else {
        predictionID = 2212;
        confidence = ConfidenceLevel.UNKNOWN;
      }

      epTracker.trackActualPredicted(trueID, predictionID, confidence);
    }
  }



  public static void main(String[] args) throws IOException {
    init();
    runAnalysis();

    TCanvas can = new TCanvas("can", 550, 500);
    can.getPad().getAxisZ().setLog(true);
    can.draw(bvp);
    can.draw(posH, "same");
    can.draw(posL, "same");
    can.draw(piH, "same");
    can.draw(piL, "same");
    can.draw(kH, "same");
    can.draw(kL, "same");
    can.draw(prH, "same");
    can.draw(prL, "same");

    TCanvas can2 = new TCanvas("can2", 1100, 450);
    can2.divide(4, 1);

    can2.cd(0);
    can2.getPad().getAxisZ().setLog(true);
    can2.draw(bvp_pos);
    can2.draw(posH, "same");
    can2.draw(posL, "same");

    can2.cd(1);
    can2.getPad().getAxisZ().setLog(true);
    can2.draw(bvp_pi);
    can2.draw(piH, "same");
    can2.draw(piL, "same");

    can2.cd(2);
    can2.getPad().getAxisZ().setLog(true);
    can2.draw(bvp_k);
    can2.draw(kH, "same");
    can2.draw(kL, "same");

    can2.cd(3);
    can2.getPad().getAxisZ().setLog(true);
    can2.draw(bvp_pr);
    can2.draw(prH, "same");
    can2.draw(prL, "same");

    System.out.println("");
    for(int pid : epTracker.ids) System.out.println(pid + " " + epTracker.getEfficiency(pid) + " " + epTracker.getPurity(pid));
    System.out.println("");
  }


}
