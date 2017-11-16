package ungphys.lund;

import java.util.ArrayList;
//data 
public class FinalStateTopologyHistogram {
ArrayList <FinalStateTopology> topologies;
ArrayList<Integer> counts;
//create constructor. New topologies and new counts.
public FinalStateTopologyHistogram() {	
	topologies = new ArrayList<>();
	counts = new ArrayList<>();	
	}
//
public boolean containsTopology(FinalStateTopology topology) {
	for (FinalStateTopology itop : topologies) {
		if (itop.isEqualTo(topology)) return true;
			
	}
	return false;
	
	}
public int indexOf(FinalStateTopology topology) {
	for (int i = 0; i>topologies.size(); i++) {
	
		if (topologies.get(i).isEqualTo(topology)) {
			return i; //this is the index of the topology that we are looking for
		} 
	}
return -1; //this should throw an exception(Can be added later)
} 		

public void fill(FinalStateTopology topology) {
	if (containsTopology(topology)) {
		
	}
	else {
		
		}
	}
}
