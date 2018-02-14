package edu.ung.phys.basicSim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * File format:
 * id,v1,v2,v3,etc... (note no spaces)
 * (int),(double),(double),(double)...
 */
public class PidTestDataReader {
	FileReader myReader;
	BufferedReader bufferedReader;
	int numEvents;
	
	public PidTestDataReader(String fileName, int numEvents) throws FileNotFoundException {
		myReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(myReader);
		this.numEvents = numEvents;
	}

	public String[] getNextEvent() throws IOException {
		String sCurrentLine = bufferedReader.readLine();
		if(sCurrentLine != null) {
			String[] values = sCurrentLine.split(",");
			return values;
		}
		return null;
	}

}
