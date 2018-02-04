package edu.ung.phys.basicSim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DetectorParticle {
	
	public Particle particle;
	public double pathLength;
	
	public DetectorParticle(Particle particle, double pathLength) {
		this.particle = particle;
		this.pathLength = pathLength;
	}
	
	public static DetectorParticle getRandom() {
		Random r = new Random();
		ArrayList<ParticleType> particleTypes = new ArrayList<>(Arrays.asList(ParticleType.values()));
		ParticleType randomParticleType = particleTypes.get(r.nextInt(particleTypes.size()));
		Vector3D randomVector = new Vector3D(3.0*r.nextDouble(), 3.0*r.nextDouble(), 3.0*r.nextDouble());
		Particle randomParticle = new Particle(randomParticleType, randomVector);
		double randomPathLength = 10.0*r.nextDouble();
		return new DetectorParticle(randomParticle, randomPathLength);
	}

}
