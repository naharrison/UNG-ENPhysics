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

	public EfficiencyPurityTracker(int nSpecies, ArrayList<Integer> ids) {
	   this.nSpecies = nSpecies;
	   this.ids = ids;
	   this.totalOccurances = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   this.nLowCon_corr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   this.nLowCon_incorr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   this.nHighCon_corr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   this.nHighCon_incorr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   this.nUnknown = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   this.nTies = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	}

	public void track(int actual, int prediction, ConfidenceLevel confidence){
	  if (confidence == ConfidenceLevel.UNKNOWN) {
	  	nUnknown.set(ids.get(actual), nUnknown.get(ids.get(actual)) + 1);
	  }
	  else if(confidence == ConfidenceLevel.TIE) {
		nTies.set(ids.get(actual), nTies.get(ids.get(actual)) + 1);
	  }
	  else if(prediction == actual && confidence == ConfidenceLevel.HIGH) {
 		nHighCon_corr.set(ids.get(actual), nHighCon_corr.get(ids.get(actual)) + 1);
	  }
	  else if(prediction == actual && confidence == ConfidenceLevel.LOW) {
		nLowCon_corr.set(ids.get(actual), nLowCon_corr.get(ids.get(actual)) + 1);
	  }
	  else if(prediction != actual && confidence == ConfidenceLevel.HIGH) {
		nHighCon_incorr.set(ids.get(actual), nHighCon_incorr.get(ids.get(actual)) + 1);
	  }
	  else if(prediction != actual && confidence == ConfidenceLevel.LOW) {
		nLowCon_incorr.set(ids.get(actual), nLowCon_incorr.get(ids.get(actual)) + 1);
	  }
	  else {
		System.out.println("Something is very wrong");
	  }
	}
 }

//copy over 'getPurity' and 'getEfficiency' from PidStatTracker so that this outputs something.
