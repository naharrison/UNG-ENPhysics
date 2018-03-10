package edu.ung.phys;

/**
 * @author naharrison
 * a simple Axis class for histograms
 * bin numbers go from 0 to n-1
 * bin -1 is underflow bin
 * bin n is overflow bin
 */
public class Axis {
	
	public int nDivisions;
	public double min, max;
	
	public Axis(int nDivisions, double min, double max) {
		this.nDivisions = nDivisions;
		this.min = min;
		this.max = max;
	}
	
	public double getBinWidth() {
		return (max - min)/nDivisions;
	}
	
	public int getBin(double value) {
		if(value < min) return -1;
		else if(value >= max) return nDivisions;
		else return (int) Math.floor((value - min)/getBinWidth());
	}

}
