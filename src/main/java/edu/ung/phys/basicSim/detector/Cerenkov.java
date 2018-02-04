package edu.ung.phys.basicSim.detector;

import java.util.Random;

import edu.ung.phys.basicSim.DetectorParticle;

public class Cerenkov implements Detector {
	
	double velocityThreshold;
	double belowThresholdMu;
	double belowThresholdSigma;
	double aboveThresholdMu;
	double aboveThresholdSigma;
	Random r;

	public Cerenkov(double velocityThreshold, double belowThresholdMu, double belowThresholdSigma, double aboveThresholdMu, double aboveThresholdSigma) {
		this.velocityThreshold = velocityThreshold;
		this.belowThresholdMu = belowThresholdMu;
		this.belowThresholdSigma = belowThresholdSigma;
		this.aboveThresholdMu = aboveThresholdMu;
		this.aboveThresholdSigma = aboveThresholdSigma;
		r = new Random();
	}

	@Override
	public DetectorResponse getResponse(DetectorParticle detectorParticle) {
		if(detectorParticle.particle.particleType.getCharge() == 0) return new CerenkovResponse(0);

		if(detectorParticle.particle.velocity() < velocityThreshold) {
			return new CerenkovResponse(5 + 0.25*r.nextGaussian());
		}
		else return new CerenkovResponse(25 + 2.5*r.nextGaussian());
	}

}
