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
	GHisto2D ghist2D;
	ArrayList<GHisto2D> ghist2D_list;
	GHisto3D ghist3D;
	ArrayList<GHisto3D> ghist3D_list;
	GHisto4D ghist4D;
	ArrayList<GHisto4D> ghist4D_list;
	GHisto5D ghist5D;
	
	public void settings() {
		size(1200, 800);
	}
	
	public void setup() {
		ArrayList<Integer> r1 = new ArrayList<Integer>(Arrays.asList(20, 40, 60, 10, 30));
		ArrayList<Integer> r2 = new ArrayList<Integer>(Arrays.asList(80, 100, 120, 23, 60));
		ArrayList<Integer> r3 = new ArrayList<Integer>(Arrays.asList(80, 50, 120, 180, 30));
		ArrayList<Integer> r4 = new ArrayList<Integer>(Arrays.asList(30, 60, 90, 140, 60));
		ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>(Arrays.asList(r1, r2, r3, r4));

		grid = new ColorGrid2D(this, values);
		gaxisX = new GHorizontalAxis(this, new Axis(20, 45, 55));
		gaxisY = new GVerticalAxis(this, new Axis(10, 0, 10));

		ghist2D = new GHisto2D(this, grid, gaxisX, gaxisY);

		ghist2D_list = new ArrayList<>();
		ghist2D_list.add(ghist2D);
		ghist2D_list.add(ghist2D);
		ghist2D_list.add(ghist2D);
		
		ghist3D = new GHisto3D(this, ghist2D_list);

		ghist3D_list = new ArrayList<>();
		ghist3D_list.add(ghist3D);
		ghist3D_list.add(ghist3D);
		
		ghist4D = new GHisto4D(this, ghist3D_list);

		ghist4D_list = new ArrayList<>();
		ghist4D_list.add(ghist4D);
		ghist4D_list.add(ghist4D);
		ghist4D_list.add(ghist4D);
		
		ghist5D = new GHisto5D(this, ghist4D_list);
	}
	
	public void draw() {
		background(220);
		ghist5D.display();
	}

}
