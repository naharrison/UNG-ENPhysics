package edu.ung.phys.basicSim;

public enum ParticleType {
	
	Electron(11, -1, 0.000511), Proton(2212, 1, 0.938), PiPlus(211, 1, 0.14), PiMinus(-211, -1, 0.14),
	Photon(22, 0, 0), Neutron(2112, 0, 0.940);
	
	private int id, charge;
	private double mass;
	
	ParticleType(int id, int charge, double mass) {
		this.id = id;
		this.charge = charge;
		this.mass = mass;
	}

	public int getId() {
		return id;
	}

	public int getCharge() {
		return charge;
	}

	public double getMass() {
		return mass;
	}

}
