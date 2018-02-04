package edu.ung.phys.basicSim.detector;

import java.util.Random;

import edu.ung.phys.basicSim.DetectorParticle;

public class TimeOfFlight implements Detector {
	
	double tofResolution;
	double tofShift;
	Random r;

	public TimeOfFlight(double tofResolution, double tofShift) {
		this.tofResolution = tofResolution;
		this.tofShift = tofShift;
		r = new Random();
	}

	@Override
	public DetectorResponse getResponse(DetectorParticle detectorParticle) {
		double exactTof = detectorParticle.pathLength/detectorParticle.particle.velocity();
		double tof = exactTof + tofResolution*r.nextGaussian() + tofShift;
		return new TimeOfFlightResponse(tof);
	}

}
