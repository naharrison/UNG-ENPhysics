package edu.ung.phys.basicSim.detector;

import edu.ung.phys.basicSim.DetectorParticle;

public interface Detector {
	
	public DetectorResponse getResponse(DetectorParticle detectorParticle);

}
