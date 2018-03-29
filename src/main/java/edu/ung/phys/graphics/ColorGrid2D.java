package edu.ung.phys.graphics;

import java.util.Collections;

import edu.ung.phys.NDimensionalArray;
import processing.core.PApplet;

public class ColorGrid2D {

	public PApplet parent;
	public NDimensionalArray<Integer> values; // 2D
	public boolean rainbow;
	
	private Integer minValue;
	private Integer maxValue;

	
	public ColorGrid2D(PApplet parent, NDimensionalArray<Integer> values) {
		this.parent = parent;
		this.values = values;
		rainbow = false;
		this.minValue = Collections.min(values.objects);
		this.maxValue = Collections.max(values.objects);
	}


	private double getWavelength(Integer value) {
		return 390.0 + 310.0*(((double) (value - minValue))/(maxValue - minValue));
	}

	
	public void display() {
		parent.noStroke();
		
		int nx = values.sizes.get(0);
		int ny = values.sizes.get(1);
		int xWidth = parent.width/nx;
		int yWidth = parent.height/ny;
		
		for(int j = 0; j < ny; j++) {
			int yStart = parent.height - (j+1)*yWidth;
			for(int k = 0; k < nx; k++) {
				int xStart = k*xWidth;
				int value = values.get(k, j);
				int[] colors = ColorCalc.waveLengthToRGB(getWavelength(value));
				if(rainbow) parent.fill(colors[0], colors[1], colors[2]);
				else parent.fill(0, 0, (int) (255*(((double) (value - minValue))/(maxValue - minValue))));
				parent.rect(xStart, yStart, xWidth, yWidth);
			}
		}
	}


}
