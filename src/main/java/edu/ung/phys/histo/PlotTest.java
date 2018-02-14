package edu.ung.phys.histo;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.*;

public class PlotTest extends PApplet {

	public static void main(String[] args) {
		PApplet.main("edu.ung.phys.histo.PlotTest");
	}
	
	ColorGrid2D grid_a;
	ColorGrid2D grid_b;

	GHorizontalAxis gaxisX;
	GVerticalAxis gaxisY;

	GHisto2D ghist2D_a;
	GHisto2D ghist2D_b;
	ArrayList<GHisto2D> ghist2D_list_a;
	ArrayList<GHisto2D> ghist2D_list_b;

	GHisto3D ghist3D_a;
	GHisto3D ghist3D_b;
	ArrayList<GHisto3D> ghist3D_list_a;
	ArrayList<GHisto3D> ghist3D_list_b;

	GHisto4D ghist4D_a;
	GHisto4D ghist4D_b;
	ArrayList<GHisto4D> ghist4D_list;

	GHisto5D ghist5D;
	
	public void settings() {
		size(800, 600);
	}
	
	public void setup() {
		ArrayList<Integer> r1 = new ArrayList<Integer>(Arrays.asList(20, 40, 60, 10, 30));
		ArrayList<Integer> r2 = new ArrayList<Integer>(Arrays.asList(80, 100, 120, 23, 60));
		ArrayList<Integer> r3 = new ArrayList<Integer>(Arrays.asList(80, 50, 120, 180, 30));
		ArrayList<Integer> r4 = new ArrayList<Integer>(Arrays.asList(30, 60, 90, 140, 60));
		ArrayList<Integer> r5 = new ArrayList<Integer>(Arrays.asList(145, 25, 99, 12, 70));
		ArrayList<ArrayList<Integer>> values_a = new ArrayList<ArrayList<Integer>>(Arrays.asList(r1, r2, r3, r4));
		ArrayList<ArrayList<Integer>> values_b = new ArrayList<ArrayList<Integer>>(Arrays.asList(r5, r4, r1, r3));

		grid_a = new ColorGrid2D(this, values_a);
		grid_b = new ColorGrid2D(this, values_b);
		gaxisX = new GHorizontalAxis(this, new Axis(20, 45, 55));
		gaxisY = new GVerticalAxis(this, new Axis(10, 0, 10));

		ghist2D_a = new GHisto2D(this, grid_a, gaxisX, gaxisY);
		ghist2D_b = new GHisto2D(this, grid_b, gaxisX, gaxisY);

		ghist2D_list_a = new ArrayList<>();
		ghist2D_list_a.add(ghist2D_a);
		ghist2D_list_a.add(ghist2D_b);
		ghist2D_list_a.add(ghist2D_a);
		
		ghist2D_list_b = new ArrayList<>();
		ghist2D_list_b.add(ghist2D_b);
		ghist2D_list_b.add(ghist2D_a);
		ghist2D_list_b.add(ghist2D_b);
		
		ghist3D_a = new GHisto3D(this, ghist2D_list_a);
		ghist3D_b = new GHisto3D(this, ghist2D_list_b);

		ghist3D_list_a = new ArrayList<>();
		ghist3D_list_a.add(ghist3D_a);
		ghist3D_list_a.add(ghist3D_a);
		
		ghist3D_list_b = new ArrayList<>();
		ghist3D_list_b.add(ghist3D_b);
		ghist3D_list_b.add(ghist3D_b);
		
		ghist4D_a = new GHisto4D(this, ghist3D_list_a);
		ghist4D_b = new GHisto4D(this, ghist3D_list_b);

		ghist4D_list = new ArrayList<>();
		ghist4D_list.add(ghist4D_a);
		ghist4D_list.add(ghist4D_b);
		ghist4D_list.add(ghist4D_a);
		
		ghist5D = new GHisto5D(this, ghist4D_list);
	}
	
	public void draw() {
		background(220);
		ghist5D.display();
	}

}
