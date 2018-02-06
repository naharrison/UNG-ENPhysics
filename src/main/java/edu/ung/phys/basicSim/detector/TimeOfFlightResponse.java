package edu.ung.phys.basicSim.detector;

import java.util.ArrayList;
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
	public ArrayList<Double> getValues() {
		return (ArrayList<Double>) Arrays.asList(tof);
	}

}
