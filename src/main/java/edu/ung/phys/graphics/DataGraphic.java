package edu.ung.phys.graphics;

import edu.ung.phys.plot.Function1D;
import processing.core.PApplet;
import processing.core.PImage;

public class DataGraphic extends PApplet {
	
	
	public PImage pimg;
	
	
	public DataGraphic(PImage pimg, int width, int height) {
		super();
		this.pimg = pimg;
		this.width = width;
		this.height = height;
	}
	
	
	public void settings() {
		size(width, height);
	}
	
	
	public void setup() {
		frameRate(10);
		Function1D ff = new Function1D("2*x^2 - 4*x + 3", -1, 3);
		pimg = ff.getPImage();
	}
	
	
	public void draw() {
		image(pimg, 0, 0, width, height);
	}

}

