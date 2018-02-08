package edu.ung.phys.basicSim.detector;

import java.util.Arrays;
import java.util.List;

public class CalorimeterResponse implements DetectorResponse {
	
	public double ein, eout;
	
	public CalorimeterResponse(double ein, double eout) {
		this.ein = ein;
		this.eout = eout;
	}

	@Override
	public void show() {
		System.out.println("ECR: ein = " + ein + "; eout = " + eout);
	}

	@Override
	public String getString() {
		return Double.toString(ein) + " " + Double.toString(eout);
	}

	@Override
	public List<Double> getValues() {
		return (List<Double>) Arrays.asList(ein, eout);
	}

}
