package edu.ung.phys.basicSim;

public class Vector2D {

	public double x, y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y);
	}

	public double theta() {
		return Math.atan2(y, x);
	}
	
}
