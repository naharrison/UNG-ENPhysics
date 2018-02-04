package edu.ung.phys.basicSim;

public class Vector3D {
	
	public double x, y, z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double transverseComponent() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double magnitude() {
		return Math.sqrt(this.transverseComponent()*this.transverseComponent() + z*z);
	}

	public double theta() {
		return Math.atan2(this.transverseComponent(), z);
	}
	
	public double phi() {
		return Math.atan2(y, x);
	}
}
