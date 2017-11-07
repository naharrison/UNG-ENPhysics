package ungphys.lund;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LUNDReader {
	FileReader myReader;
	BufferedReader bufferedReader;
	int numEvents;
	
	public LUNDReader (String fileName, int numEvents) throws FileNotFoundException {
		myReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(myReader);
		this.numEvents = numEvents;
	}
	public LUNDEvent getNextEvent() throws IOException {
		String sCurrentLine = bufferedReader.readLine();
		LUNDHeader header = new LUNDHeader(sCurrentLine); 
		ArrayList<LUNDParticle> particles = new ArrayList<>();
		
		for (int p=0; p<header.getNumParticles(); p++) {
			
			sCurrentLine = bufferedReader.readLine();
			LUNDParticle particle = new LUNDParticle(sCurrentLine);
			particles.add(particle);
			
		}
		LUNDEvent event = new LUNDEvent(header, particles);
		return event;
	}
}
