package edu.ung.phys.histo;

import java.util.ArrayList;

import processing.core.PApplet;

public class GHisto4D {

	public PApplet parent;
	public ArrayList<GHisto3D> histos;
	

	public GHisto4D(PApplet parent, ArrayList<GHisto3D> histos) {
		this.parent = parent;
		this.histos = histos;
	}
	

	public void display() {
		int nHistos = histos.size();
		for(int j = 0; j < nHistos; j++) {
			parent.pushMatrix();
			parent.translate(0, j*(parent.height/nHistos));
			parent.scale((float) 1.0, (float) 1.0/nHistos);
			histos.get(nHistos - j - 1).display();
			parent.popMatrix();
		}
	}
	
}
