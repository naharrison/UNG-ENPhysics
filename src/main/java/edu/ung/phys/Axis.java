package edu.ung.phys;

import java.util.ArrayList;

/**
 * @author naharrison
 * a simple Axis class for histograms
 * bin numbers go from 0 to n-1
 * bin -1 is underflow bin
 * bin n is overflow bin
 */
public class Axis {
	
	public int nDivisions;
	public ArrayList<Double> binLimits;
	public double min, max;

	
	public Axis(int nDivisions, double min, double max) {
		this.nDivisions = nDivisions;
		binLimits = new ArrayList<>();
		for(int k = 0; k <= nDivisions; k++) {
			binLimits.add(min + k*((max - min)/nDivisions));
		}
		this.min = min;
		this.max = max;
	}
	
	
	public Axis(ArrayList<Double> binLimits) {
		this.nDivisions = binLimits.size() - 1;
		this.binLimits = binLimits;
		this.min = binLimits.get(0);
		this.max = binLimits.get(binLimits.size()-1);
	}

	
	public double getBinWidth(int index) {
		return binLimits.get(index+1) - binLimits.get(index);
	}

	
	public int getBin(double value) {
		if(value < min) return -1;
		else if(value >= max) return nDivisions;
		
		int result = 0;
		for(int k = 1; k < binLimits.size()-1; k++) {
			if(value < binLimits.get(k)) {
				result = k - 1;
				break;
			}
		}
		return result;
	}

}
