package ungphys.lund;

import java.util.ArrayList;

public class LUNDEvent {
	LUNDHeader header;
	ArrayList<LUNDParticle> particles;
	
	public LUNDEvent (LUNDHeader header, ArrayList<LUNDParticle> particles) {
		this.header = header;
		this.particles = particles;
	}

	public void printActiveParticles() {	
		for (LUNDParticle particle : particles) {
			if (particle.getType()==1) {
				System.out.println("  The particle ID is "+particle.getPid());				
			}				
		}		
	} 
	
	public FinalStateTopology getFinalStateTopology() {
		ArrayList<Integer> final_pids = new ArrayList<>();
		for (LUNDParticle particle : particles) {
			if (particle.getType()==1) {
				
				final_pids.add(particle.getPid());
			}
		}
		FinalStateTopology topology = new FinalStateTopology (final_pids);
		return topology;
	}
}
