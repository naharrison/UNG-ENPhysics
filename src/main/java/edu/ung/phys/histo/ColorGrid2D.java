package edu.ung.phys.histo;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;

public class ColorGrid2D {
	
	public PApplet parent;
	public ArrayList<ArrayList<Integer>> values;
	public boolean rainbow;
	
	private Integer minValue;
	private Integer maxValue;

	
	public ColorGrid2D(PApplet parent, ArrayList<ArrayList<Integer>> values) {
		this.parent = parent;
		this.values = values;
		rainbow = false;
		setMinAndMax();
	}


	private void setMinAndMax() {
		ArrayList<Integer> rowMinimums = new ArrayList<>();
		ArrayList<Integer> rowMaximums = new ArrayList<>();
		
		for(ArrayList<Integer> row : values) {
			rowMinimums.add(Collections.min(row));
			rowMaximums.add(Collections.max(row));
		}
		
		this.minValue = Collections.min(rowMinimums);
		this.maxValue = Collections.max(rowMaximums);
	}

	
	private double getWavelength(Integer value) {
		return 390.0 + 310.0*(((double) (value - minValue))/(maxValue - minValue));
	}
	
	
	public void display() {
		parent.noStroke();
		
		int yWidth = parent.height/values.size();
		int xWidth = parent.width/values.get(0).size();
		
		for(int j = 0; j < values.size(); j++) {
			int yStart = j*yWidth;
			for(int k = 0; k < values.get(0).size(); k++) {
				int xStart = k*xWidth;
				int value = values.get(values.size() - j - 1).get(k);
				int[] colors = ColorCalc.waveLengthToRGB(getWavelength(value));
				if(rainbow) parent.fill(colors[0], colors[1], colors[2]);
				else parent.fill(0, 0, (int) (255*(((double) (value - minValue))/(maxValue - minValue))));
				parent.rect(xStart, yStart, xWidth, yWidth);
			}
		}
	}

	
}
