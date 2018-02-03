package edu.ung.phys.lund;

import java.util.ArrayList;
import java.util.Collections;

public class FinalStateTopology implements Comparable<FinalStateTopology> {

	ArrayList<Integer> final_pids = new ArrayList<>();
	
	public FinalStateTopology(ArrayList<Integer> finalPids) {
		final_pids = finalPids;
	}
	
	public ArrayList<Integer> getPidList(){
		return final_pids;
	}
	
	@Override
	public int compareTo(FinalStateTopology compareState) {
		ArrayList<Integer> compare_list = compareState.getPidList();
		Collections.sort(compare_list);
		
		ArrayList<Integer> final_pids_copy = new ArrayList<>(final_pids);
		Collections.sort(final_pids_copy);
		
		if (compare_list.equals (final_pids_copy)) return 0;
		else return -1;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object) return true;
		if(!(object instanceof FinalStateTopology)) return false;

		FinalStateTopology compareState = (FinalStateTopology) object;
		if(compareTo(compareState) == 0) return true;
		return false;
	}

	@Override
	public int hashCode() {
		// recommended from http://findbugs.sourceforge.net/bugDescriptions.html (HE_EQUALS_USE_HASHCODE)
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}
	
	public boolean isEqualTo(FinalStateTopology compareState) {
		return this.compareTo(compareState) == 0;
	}
	
	public void show() {
		for(Integer iparticle : final_pids) {
			System.out.print(iparticle+" ");
			
		}
	}
}
