package edu.ung.phys.basicSim.detector;

import edu.ung.phys.basicSim.Vector3D;

public class DriftChamberResponse implements DetectorResponse {
	
	public Vector3D pMeasured;
	public double pathLengthMeasured;
	
	public DriftChamberResponse(Vector3D pMeasured, double pathLengthMeasured) {
		this.pMeasured = pMeasured;
		this.pathLengthMeasured = pathLengthMeasured;
	}

	@Override
	public void show() {
		System.out.println("DCR: p = (" + pMeasured.x + ", " + pMeasured.y + ", " + pMeasured.z + "); s = " + pathLengthMeasured);
	}

	@Override
	public String getString() {
		return Double.toString(pMeasured.x) + " " + Double.toString(pMeasured.y) + " " + Double.toString(pMeasured.z)
		+ " " + Double.toString(pathLengthMeasured);
	}

}
