package edu.ung.phys.lund;

public class LUNDHeader {

	public int numParticles;
	public LUNDHeader(String headerRow) {
		String[] parts = headerRow.split("\\s+");
		numParticles = Integer.parseInt(parts[1]);
	}
	public LUNDHeader() {}
	public int getNumParticles() {
		return numParticles;
	}
	public void setNumParticles(int numParticles) {
		this.numParticles = numParticles;
	}
	
	
}
