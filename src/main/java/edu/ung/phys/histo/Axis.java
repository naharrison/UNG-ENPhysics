package edu.ung.phys.histo;

public class Axis {
	
	public double min, max;
	public int nDivisions;
	
	public Axis(double min, double max, int nDivisions) {
		this.min = min;
		this.max = max;
		this.nDivisions = nDivisions;
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
