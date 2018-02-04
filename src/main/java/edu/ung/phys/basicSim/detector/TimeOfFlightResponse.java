package edu.ung.phys.basicSim.detector;

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

}
