package edu.ung.phys.hist;

import java.util.ArrayList;

import processing.core.PApplet;

public class GHist1DCollection1D {

	public PApplet parent;
	public ArrayList<GHist1D> histos;
	
	public GHist1DCollection1D(PApplet parent, ArrayList<GHist1D> histos) {
		this.parent = parent;
		this.histos = histos;
	}
	
	public void display() {
		int nHistos = histos.size();
		for(int j = 0; j < nHistos; j++) {
			parent.pushMatrix();
			parent.translate(j*(parent.width/nHistos), 0);
			parent.translate(40, 30);
			parent.scale((float) 1.0/nHistos, (float) 1.0);
			parent.scale((float) 0.78, (float) 0.82);
			histos.get(j).display();
			parent.popMatrix();
		}
	}
}
