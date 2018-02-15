package edu.ung.phys.hist;

import processing.core.PApplet;

public class PlotTry extends PApplet {

	public static void main(String[] args) {
		PApplet.main("edu.ung.phys.hist.PlotTry");
	}
	
	
	ColorGrid2D grid;
	GHorizontalAxis xAxis;
	GVerticalAxis yAxis;
	GHist2D ghist2d;


	public void settings() {
		size(800, 600);
	}


	public void setup() {
		NDimensionalArray<Integer> gridValues = new NDimensionalArray<Integer>(3, 4);
		gridValues.set(25, 0, 0);
		gridValues.set(50, 1, 0);
		gridValues.set(75, 2, 0);
		gridValues.set(100, 0, 1);
		gridValues.set(35, 1, 1);
		gridValues.set(3, 2, 1);
		gridValues.set(25, 0, 2);
		gridValues.set(50, 1, 2);
		gridValues.set(75, 2, 2);
		gridValues.set(100, 0, 3);
		gridValues.set(35, 1, 3);
		gridValues.set(3, 2, 3);
		
		grid = new ColorGrid2D(this, gridValues);
		
		xAxis = new GHorizontalAxis(this, new Axis(4, 10, 20));
		yAxis = new GVerticalAxis(this, new Axis(5, 40, 90));
		
		ghist2d = new GHist2D(this, grid, xAxis, yAxis);
	}
	
	
	public void draw() {
		background(220);
		pushMatrix();
		translate(100, 50);
		scale((float) 0.8, (float) 0.8);
		ghist2d.display();
		popMatrix();
	}

}
