package edu.ung.phys.basicSim.detector;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ung.phys.basicSim.Vector3D;

public class DriftChamberResponse implements DetectorResponse {
	
	public double chargeMeasured;
	public Vector3D pMeasured;
	public double pathLengthMeasured;
	
	public DriftChamberResponse(double chargeMeasured, Vector3D pMeasured, double pathLengthMeasured) {
		this.chargeMeasured = chargeMeasured;
		this.pMeasured = pMeasured;
		this.pathLengthMeasured = pathLengthMeasured;
	}

	@Override
	public void show() {
		System.out.println("DCR: q = " + chargeMeasured + "; p = (" + pMeasured.x + ", " + pMeasured.y + ", " + pMeasured.z + "); s = " + pathLengthMeasured);
	}

	@Override
	public String getString() {
		return Double.toString(chargeMeasured) + " " + Double.toString(pMeasured.x) + " " + Double.toString(pMeasured.y) + " " + Double.toString(pMeasured.z)
		+ " " + Double.toString(pathLengthMeasured);
	}
	
	@Override
	public ArrayList<Double> getValues() {
		return (ArrayList<Double>) Arrays.asList(chargeMeasured, pMeasured.x, pMeasured.y, pMeasured.z, pathLengthMeasured);
	}

}
