package ungphys.lund;

import java.io.IOException;
import java.util.ArrayList;

public class lundPIDfunction {

	public static void main(String[] args) throws IOException {
		 
			LUNDReader myReader = new LUNDReader("short_lund_file.dat", 9);
			
			ArrayList <Integer> test_list = new ArrayList<>();
			test_list.add(11);
			test_list.add(211);
			test_list.add(-211);
			test_list.add(2212);
			test_list.add(22);
			test_list.add(22);
			
			FinalStateTopology test_topology = new FinalStateTopology(test_list);
			
			 for (int i=1; i<=myReader.numEvents; i++) {
				System.out.println("This is event number "+i);
				LUNDEvent event = myReader.getNextEvent();
				FinalStateTopology current_topology = event.getFinalStateTopology();
				if (current_topology.isEqualTo(test_topology)) event.printActiveParticles();
				//event.printActiveParticles();
			}
			
	}
	
}