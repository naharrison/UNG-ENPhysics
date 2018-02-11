package edu.ung.phys.histo;

import processing.core.PApplet;

public class GHisto2D {
	
	public PApplet parent;
	public ColorGrid2D grid;
	public GHorizontalAxis gaxisX;
	public GVerticalAxis gaxisY;

	public GHisto2D(PApplet parent, ColorGrid2D grid, GHorizontalAxis gaxisX, GVerticalAxis gaxisY) {
		this.parent = parent;
		this.grid = grid;
		this.gaxisX = gaxisX;
		this.gaxisY = gaxisY;
	}
	
	public void display() {
		grid.display();
		gaxisX.display();
		gaxisY.display();
	}

}
