package edu.ung.phys.histo;

import java.util.ArrayList;
import java.util.Collections;

public class Histogram {
	
	public ArrayList<Axis> axes;
	public ArrayList<Integer> counts;
	
	public Histogram(ArrayList<Axis> axes) {
		this.axes = axes;
		int totalBins = 1;
		for(Axis axis : axes) {
			totalBins = totalBins * axis.nDivisions;
		}
		counts = new ArrayList<Integer>(Collections.nCopies(totalBins, 0));
	}

}
