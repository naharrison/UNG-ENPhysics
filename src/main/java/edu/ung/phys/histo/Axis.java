package edu.ung.phys.histo;

/**
 * @author naharrison
 * a simple Axis class for histograms
 * bin numbers go from 1 to n
 * bin 0 is underflow bin
 * bin n+1 is overflow bin
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
		int bin = (int) Math.floor((value - min)/getBinWidth()) + 1;
		if(bin < 1) bin = 0;
		else if(bin > nDivisions) bin = nDivisions + 1;
		return bin;
	}

}
