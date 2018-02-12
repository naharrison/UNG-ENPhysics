package edu.ung.phys.histo;

import java.util.ArrayList;
import processing.core.PApplet;


public class GHisto5D {

	public PApplet parent;
	public ArrayList<GHisto4D> histos;
	
	private int sliderX;
	private int variable5index;
	
	
	public GHisto5D(PApplet parent, ArrayList<GHisto4D> histos) {
		this.parent = parent;
		this.histos = histos;
		this.sliderX = 0;
		this.variable5index = 0;
	}
	

	public void display() {
		int nHistos = histos.size();

		parent.pushMatrix();
		parent.scale((float) 1.0, (float) 0.9);
		histos.get(variable5index).display();
		parent.popMatrix();

		if(parent.mouseY > 0.9*parent.height) {
			variable5index = (int) (nHistos*(((double) parent.mouseX)/((double )parent.width)));
			sliderX = parent.mouseX;
		}
		parent.fill(80, 120, 80);
		parent.rect(sliderX, parent.height - 20, 20, 20);
	}

}
