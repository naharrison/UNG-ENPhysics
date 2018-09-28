package edu.ung.phys;

public class PosParticleID {

	double posStrict, piStrict, kStrict, protStrict;

	public PosParticleID(double posStrict, double piStrict, double kStrict, double protStrict) {
		// TODO put conditions to make sure these are > 0 and < 0.5
		this.posStrict = posStrict;
		this.piStrict = piStrict;
		this.kStrict = kStrict;
		this.protStrict = protStrict;
	}

	// TODO finish this
	public int getPID(double p, double v) {
	}

}
