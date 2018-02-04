package edu.ung.phys.basicSim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Particle {
	
	public ParticleType particleType;
	public Vector3D p;
	
	public Particle(ParticleType particleType, Vector3D p) {
		this.particleType = particleType;
		this.p = p;
	}
	
	public double velocity() {
		return p.magnitude()/Math.sqrt(p.magnitude()*p.magnitude() + particleType.getMass()*particleType.getMass());
	}
	
	public static Particle getRandom() {
		Random r = new Random();
		ArrayList<ParticleType> particleTypes = new ArrayList<>(Arrays.asList(ParticleType.values()));
		ParticleType randomParticleType = particleTypes.get(r.nextInt(particleTypes.size()));
		Vector3D randomVector = new Vector3D(3.0*r.nextDouble(), 3.0*r.nextDouble(), 3.0*r.nextDouble());
		return new Particle(randomParticleType, randomVector);
	}

}
