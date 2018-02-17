package edu.ung.phys.hist;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;

public class PlotTry extends PApplet {

	public static void main(String[] args) {
		PApplet.main("edu.ung.phys.hist.PlotTry");
	}
	
	
	ColorGrid2D grid;
	GHorizontalAxis xAxis;
	GVerticalAxis yAxis;
	GHist2D ghist2d;
	GHist1D ghist1d;


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
		
		ArrayList<Integer> h1dValues = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 2, 1, 0, 5));
		
		grid = new ColorGrid2D(this, gridValues);
		
		xAxis = new GHorizontalAxis(this, new Axis(4, 10, 20));
		yAxis = new GVerticalAxis(this, new Axis(5, 40, 90));
		
		ghist2d = new GHist2D(this, grid, xAxis, yAxis);

		ghist1d = new GHist1D(this, xAxis, h1dValues);
	}
	
	
	public void draw() {
		background(220);

		pushMatrix();
		translate(50, 25);
		scale((float) 0.4, (float) 0.4);
		ghist1d.display();
		popMatrix();

		pushMatrix();
		translate(440, 320);
		scale((float) 0.4, (float) 0.4);
		ghist2d.display();
		popMatrix();
	}

}
