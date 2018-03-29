package edu.ung.phys.hist;

import java.util.ArrayList;
import java.util.Collections;

import edu.ung.phys.Axis;
import edu.ung.phys.graphics.GHorizontalAxis;
import edu.ung.phys.graphics.GVerticalAxis;
import processing.core.PApplet;

public class GHist1D {

	public PApplet parent;
	public GHorizontalAxis gaxisX;
	public ArrayList<Integer> counts;

	private GVerticalAxis gaxisY;
	private int max;
	

	public GHist1D(PApplet parent, GHorizontalAxis gaxisX, ArrayList<Integer> counts) {
		this.parent = parent;
		this.gaxisX = gaxisX;
		this.counts = counts;
		this.max = Collections.max(counts);
		
		gaxisY = new GVerticalAxis(parent, new Axis(2, 0, max));
	}

	
	public void display() {
		gaxisX.display();
		gaxisY.display();

		parent.stroke(50);
		parent.strokeWeight((float) 4.00);

		int boxWidth = (int) (((double) parent.width)/((double) counts.size()));
		int boxHeight = 8;
		for(int j = 0; j < counts.size(); j++) {
			int ycoord = (int) (parent.height - (((double) counts.get(j))/((double) max))*parent.height);
			parent.rect(j*boxWidth, ycoord - boxHeight/2, boxWidth, boxHeight);
		}
	}

}
