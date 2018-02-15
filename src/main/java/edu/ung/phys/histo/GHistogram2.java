package edu.ung.phys.histo;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * @author naharrison
 * For plotting a Histogram object of dimension N.
 * This will create a N-2 dimensional array of GHisto2D's (hence the "2" in the class name).
 * Just like Histogram, the (N-2)-D array is converted to a 1-D array.
 */
public class GHistogram2 {
	
	public PApplet parent;
	public Histogram histogram;
	public ArrayList<GHisto2D> ghistos;
	
	public GHistogram2(PApplet parent, Histogram histogram) {
		this.parent = parent;
		this.histogram = histogram;
		this.init();
	}
	
	private void init() {
		ghistos = new ArrayList<>();

		int n_Nminus2_bins = 1; // how many total bins in the (N-2)-D array
		for(int j = 2; j < histogram.axes.size(); j++) n_Nminus2_bins = n_Nminus2_bins*histogram.axes.get(j).nDivisions;
		
		Object a = (Axis) new Axis(10, 0, 1);

		for(int i = 0; i < n_Nminus2_bins; i++) {
			
		}
	}

}
