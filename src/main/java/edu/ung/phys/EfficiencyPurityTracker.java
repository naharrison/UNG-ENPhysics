///program on efficiency and purity of data//
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap

public class EfficiencyPurityTracker {
	
	public int nSpecies;
	public ArrayList<Integer> ids, totalOccurances, nLowCon_corr, nLowCon_incorr, nHighCon_corr, nHighCon_incorr, nUnknown, nTies; 

	public EfficiencyPurityTracker(int nSpecies, ArrayList<Integer> ids) {
	   this.nSpecies = nSpecies;
	   this.ids = ids;
	   totalOccurances = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   nLowCon_Corr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   nLowCon_incorr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   nHighCon_corr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   nHighCon_incorr = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   nUnknown = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	   nTies = new ArrayList<Integer>(Collections.nCopies(nSpecies, 0));
	}

	public void track(int actual, int prediction, ConfidenceLevel confidence)
	  if (confidence == ConfidenceLevel.UNKNOWN) {
	  	nUnknown.set(ids.get(actual), nUnknown.get(ids.get(actual)) + 1);
	  }
	  if else(confidence == ConfidenceLevel.TIE) {
		nTies.set(ids.get(actual), nTies.get(ids.get(actual)) + 1);
	  }
	  if else(prediction == actual && confidence == ConfidenceLevel.HIGH) {
 		nHighCon_corr.set(ids.get(actual), nHighCon_corr.get(ids.get(actual)) + 1);
	  }
	  if else(prediction == actual && confidence == ConfidenceLevel.LOW) {
		nLowCon_corr.set(ids.get(actual), nLowCon_corr.get(ids.get(actual)) + 1);
	  }
	  if else(prediction != actual && confidence == ConfidenceLevel.HIGH) {
		nHighCon_incorr.set(ids.get(actual), nHighCon_incorr.get(ids.get(actual)) + 1);
	  }
	  if else(prediction != acutal && confidence == ConfidenceLevel.LOW) {
		nLowCon_incorr.set(ids.get(actual), nLowCon_incorr.get(ids.get(actual)) + 1);
	  }
	  else {
		System.out.println("Something is very wrong")
	  }
 }
