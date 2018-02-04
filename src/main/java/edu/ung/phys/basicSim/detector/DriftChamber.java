package edu.ung.phys.basicSim.detector;

import java.util.Random;

import edu.ung.phys.basicSim.DetectorParticle;
import edu.ung.phys.basicSim.Vector3D;

public class DriftChamber implements Detector {
	
	Vector3D pResolution;
	Vector3D pShift;
	double pathLengthResolution;
	double pathLengthShift;
	Random r;

	public DriftChamber(Vector3D pResolution, Vector3D pShift, double pathLengthResolution, double pathLengthShift) {
		this.pResolution = pResolution;
		this.pShift = pShift;
		this.pathLengthResolution = pathLengthResolution;
		this.pathLengthShift = pathLengthShift;
		r = new Random();
	}

	@Override
	public DetectorResponse getResponse(DetectorParticle detectorParticle) {
		double px = detectorParticle.particle.p.x + pResolution.x*r.nextGaussian() + pShift.x;
		double py = detectorParticle.particle.p.y + pResolution.y*r.nextGaussian() + pShift.y;
		double pz = detectorParticle.particle.p.z + pResolution.z*r.nextGaussian() + pShift.z;
		double pathLength = detectorParticle.pathLength + pathLengthResolution*r.nextGaussian() + pathLengthShift;
		return new DriftChamberResponse(new Vector3D(px, py, pz), pathLength);
	}

}
