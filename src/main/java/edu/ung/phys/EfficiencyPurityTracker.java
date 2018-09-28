///program on efficiency and purity of data//
package edu.ung.phys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class EfficiencyPurityTracker {

  public int nSpecies;
  public ArrayList<Integer> ids, totalOccurances, nLowCon_corr, nLowCon_incorr, nHighCon_corr, nHighCon_incorr, nUnknown, nTies; 


  public EfficiencyPurityTracker(ArrayList<Integer> ids) {
    this.ids = ids;
    this.nSpecies = ids.size();
    this.totalOccurances = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
    this.nLowCon_corr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
    this.nLowCon_incorr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
    this.nHighCon_corr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
    this.nHighCon_incorr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
    this.nUnknown = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
    this.nTies = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
  }


  public void trackActualPredicted(int actual, Integer prediction, ConfidenceLevel confidence){
    totalOccurances.set(ids.indexOf(actual), totalOccurances.get(ids.indexOf(actual)) + 1);

    if (confidence == ConfidenceLevel.UNKNOWN) {
      nUnknown.set(ids.indexOf(actual), nUnknown.get(ids.indexOf(actual)) + 1);
    }
    else if(confidence == ConfidenceLevel.TIE) {
      nTies.set(ids.indexOf(actual), nTies.get(ids.indexOf(actual)) + 1);
    }
    else if(prediction == actual && confidence == ConfidenceLevel.LOW) {
      nLowCon_corr.set(ids.indexOf(actual), nLowCon_corr.get(ids.indexOf(actual)) + 1);
    }
    else if(prediction != actual && confidence == ConfidenceLevel.LOW) {
      nLowCon_incorr.set(ids.indexOf(prediction), nLowCon_incorr.get(ids.indexOf(prediction)) + 1);
    }
    else if(prediction == actual && confidence == ConfidenceLevel.HIGH) {
      nHighCon_corr.set(ids.indexOf(actual), nHighCon_corr.get(ids.indexOf(actual)) + 1);
    }
    else if(prediction != actual && confidence == ConfidenceLevel.HIGH) {
      nHighCon_incorr.set(ids.indexOf(prediction), nHighCon_incorr.get(ids.indexOf(prediction)) + 1);
    }
    else {
      System.out.println("Problem with EfficiencyPurityTracker");
    }
  }


  public double getEfficiency(int id) {
    if(totalOccurances.get(ids.indexOf(id)) == 0) return 0.0;
    return ((double) nHighCon_corr.get(ids.indexOf(id))) / ((double) totalOccurances.get(ids.indexOf(id)));
  }


  public double getPurity(int id) {
    int denominator = nHighCon_corr.get(ids.indexOf(id)) + nHighCon_incorr.get(ids.indexOf(id));
    if(denominator == 0) return 0.0;
    return ((double) nHighCon_corr.get(ids.indexOf(id))) / ((double) denominator);
  }

 }
