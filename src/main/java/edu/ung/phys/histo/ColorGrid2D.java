package edu.ung.phys.histo;

import java.util.ArrayList;

import processing.core.PApplet;

public class ColorGrid2D {
	
	public PApplet parent;
	public ArrayList<ArrayList<Integer>> values;
	
	public ColorGrid2D(PApplet parent, ArrayList<ArrayList<Integer>> values) {
		this.parent = parent;
		this.values = values;
	}
	
	public void display() {
		parent.noStroke();
		
		int yWidth = parent.height/values.size();
		int xWidth = parent.width/values.get(0).size();
		
		for(int j = 0; j < values.size(); j++) {
			int yStart = j*yWidth;
			for(int k = 0; k < values.get(0).size(); k++) {
				int xStart = k*xWidth;
				parent.fill(values.get(j).get(k));
				parent.rect(xStart, yStart, xWidth, yWidth);
			}
		}
	}

}
