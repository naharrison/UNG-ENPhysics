package edu.ung.phys.histo;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.*;

public class PlotTest extends PApplet {

	public static void main(String[] args) {
		PApplet.main("edu.ung.phys.histo.PlotTest");
	}
	
	ColorGrid2D grid;
	GHorizontalAxis gaxisX;
	GVerticalAxis gaxisY;
	GHisto2D ghist;
	
	public void settings() {
		size(500, 250);
	}
	
	public void setup() {
		ArrayList<Integer> r1 = new ArrayList<Integer>(Arrays.asList(20, 40, 60));
		ArrayList<Integer> r2 = new ArrayList<Integer>(Arrays.asList(80, 100, 120));
		ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>(Arrays.asList(r1, r2));

		grid = new ColorGrid2D(this, values);
		gaxisX = new GHorizontalAxis(this, new Axis(20, 45, 55));
		gaxisY = new GVerticalAxis(this, new Axis(10, 0, 10));
		ghist = new GHisto2D(this, grid, gaxisX, gaxisY);
	}
	
	public void draw() {
		background(200);
		if(mouseX > 400) background(100, 140, 30);

		pushMatrix();
		translate(125, 62);
		scale((float) 0.5, (float) 0.5);
		ghist.display();
		popMatrix();
	}

}
