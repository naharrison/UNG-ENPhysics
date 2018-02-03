package edu.ung.phys.lund;

import java.io.IOException;
import java.util.ArrayList;

public class LundPIDfunction {

	public static void main(String[] args) throws IOException {
		 
			LUNDReader myReader = new LUNDReader("pythia_lund_11.0GeV_5k.dat", 5000);
			
			FinalStateTopologyHistogram myHistogram = new FinalStateTopologyHistogram();
			
			
			 for (int i=1; i<=myReader.numEvents; i++) {
				
				LUNDEvent event = myReader.getNextEvent();
				FinalStateTopology current_topology = event.getFinalStateTopology();
				myHistogram.fill(current_topology);
			}
			myHistogram.show();
	}
	
}
