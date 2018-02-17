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
	GHist1D ghist1d1;
	GHist1D ghist1d2;
	GHist1DCollection1D ghist1dcoll;


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
		
		ArrayList<Integer> h1dValues1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 2, 1, 0, 5));
		ArrayList<Integer> h1dValues2 = new ArrayList<Integer>(Arrays.asList(5, 3, 1, 8, 5, 5));
		
		grid = new ColorGrid2D(this, gridValues);
		
		xAxis = new GHorizontalAxis(this, new Axis(4, 10, 20));
		yAxis = new GVerticalAxis(this, new Axis(5, 40, 90));
		
		ghist2d = new GHist2D(this, grid, xAxis, yAxis);

		ghist1d1 = new GHist1D(this, xAxis, h1dValues1);
		ghist1d2 = new GHist1D(this, xAxis, h1dValues2);
		
		ArrayList<GHist1D> hist1ds = new ArrayList<>();
		hist1ds.add(ghist1d1);
		hist1ds.add(ghist1d2);
		
		ghist1dcoll = new GHist1DCollection1D(this, hist1ds);
	}
	
	
	public void draw() {
		background(220);

		//pushMatrix();
		//translate(50, 25);
		//scale((float) 0.4, (float) 0.4);
		//ghist1d1.display();
		//popMatrix();

		//pushMatrix();
		//translate(440, 320);
		//scale((float) 0.4, (float) 0.4);
		//ghist2d.display();
		//popMatrix();
		
		ghist1dcoll.display();
	}

}
