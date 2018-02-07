package edu.ung.phys.basicSim.detector;

import java.util.List;
import java.util.Arrays;

public class CerenkovResponse implements DetectorResponse {
	
	public double nphe;
	
	public CerenkovResponse(double nphe) {
		this.nphe = nphe;
	}

	@Override
	public void show() {
		System.out.println("CR: nphe = " + nphe);
	}

	@Override
	public String getString() {
		return Double.toString(nphe);
	}
	
	@Override
	public List<Double> getValues() {
		return (List<Double>) Arrays.asList(nphe);
	}

}
