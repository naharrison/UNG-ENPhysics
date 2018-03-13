package edu.ung.phys.analyses;

import edu.ung.phys.graphics.DataGraphic;
import edu.ung.phys.plot.Function1D;
import processing.core.PApplet;

public class PlotTry2 {
public static void main(String[] args) {
	
	Function1D ff = new Function1D("2*x^2 - 4*x + 3", -1, 3);
	DataGraphic gr = new DataGraphic(ff.getPImage(500, 300), 500, 300);
	PApplet.runSketch(new String[] {""}, gr);
	
}
}
