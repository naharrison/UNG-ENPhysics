package edu.ung.phys.histo;

import java.util.ArrayList;

import processing.core.PApplet;

public class GHisto3D {

	public PApplet parent;
	public ArrayList<GHisto2D> histos;
	
	public GHisto3D(PApplet parent, ArrayList<GHisto2D> histos) {
		this.parent = parent;
		this.histos = histos;
	}
	
	public void display() {
		int nHistos = histos.size();
		for(int j = 0; j < nHistos; j++) {
			parent.pushMatrix();
			parent.translate(j*(parent.width/nHistos), 0);
			parent.translate(25, 25);
			parent.scale((float) 1.0/nHistos, (float) 1.0);
			parent.scale((float) 0.8, (float) 0.8);
			histos.get(j).display();
			parent.popMatrix();
		}
	}

}
