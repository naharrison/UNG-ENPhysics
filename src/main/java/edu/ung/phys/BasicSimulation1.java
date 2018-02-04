package edu.ung.phys;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ung.phys.basicSim.DetectorParticle;
import edu.ung.phys.basicSim.Vector3D;
import edu.ung.phys.basicSim.detector.Cerenkov;
import edu.ung.phys.basicSim.detector.Detector;
import edu.ung.phys.basicSim.detector.DetectorResponse;
import edu.ung.phys.basicSim.detector.DriftChamber;
import edu.ung.phys.basicSim.detector.TimeOfFlight;

public class BasicSimulation1 {

	public static final ArrayList<Detector> detectors = new ArrayList<>();
	
	public static void addDetectors() {
		detectors.add(new DriftChamber(new Vector3D(0.25, 0.25, 0.25), new Vector3D(0.0, 0.0, 0.0), 1.0, 0.0));
		detectors.add(new TimeOfFlight(1.0, 0.0));
		detectors.add(new Cerenkov(0.96, 5.0, 1.0, 20, 2.5));
		detectors.add(new Cerenkov(0.88, 4.0, 0.9, 22, 2.0));
	}

	public static void processEvent() {
		DetectorParticle detectorParticle = DetectorParticle.getRandom();
		//System.out.println(detectorParticle.particle.particleType);

		String detectorResponsesString = "";
		for(Detector detector:detectors) {
			DetectorResponse detectorResponse = detector.getResponse(detectorParticle);
			//detectorResponse.show();
			//System.out.print(detectorResponse.getString() + " ");
			detectorResponsesString += detectorResponse.getString() + " ";
		}
		ArrayList<Double> detectorResponses = detectorResponseStringToDoubleArray(detectorResponsesString);
		//System.out.println(Arrays.toString(detectorResponses.toArray()));
		analyzeEvent(detectorParticle, detectorResponses);
	}
	
	public static ArrayList<Double> detectorResponseStringToDoubleArray(String detectorResponsesString) {
		ArrayList<Double> result = new ArrayList<>();
		String[] stringList = detectorResponsesString.split(" ");
		for(int k = 0; k < stringList.length; k++) {
			result.add(Double.parseDouble(stringList[k]));
		}
		return result;
	}

	public static void analyzeEvent(DetectorParticle detectorParticle, ArrayList<Double> detectorResponses) {
		System.out.println(detectorParticle.particle.particleType);
		System.out.println(Arrays.toString(detectorResponses.toArray()));
	}

	public static void main(String[] args) {
		
		addDetectors();
		
		for(int k = 0; k < 20; k++) {
			processEvent();
		}
		
	}

}
