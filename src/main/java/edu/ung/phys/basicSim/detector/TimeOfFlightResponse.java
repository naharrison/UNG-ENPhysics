package edu.ung.phys.basicSim.detector;

import java.util.List;
import java.util.Arrays;

public class TimeOfFlightResponse implements DetectorResponse {
	
	public double tof;
	
	public TimeOfFlightResponse(double tof) {
		this.tof = tof;
	}

	@Override
	public void show() {
		System.out.println("TOFR: tof = " + tof);
	}

	@Override
	public String getString() {
		return Double.toString(tof);
	}
	
	@Override
	public List<Double> getValues() {
		return (List<Double>) Arrays.asList(tof);
	}

}
