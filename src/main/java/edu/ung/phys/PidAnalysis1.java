package edu.ung.phys;

import edu.ung.phys.basicSim.*;
import edu.ung.phys.hist.Axis;
import edu.ung.phys.hist.Histogram;

import java.io.IOException;
import java.util.ArrayList;

public class PidAnalysis1 {
	
	public static int getIndexOfMax(ArrayList<Integer> counts) {
		int max = Integer.MIN_VALUE;
		int maxIndex = -1;
		for(int j = 0; j < counts.size(); j++) {
			if(counts.get(j) > max) {
				max = counts.get(j);
				maxIndex = j;
			}
		}
		if(max == 0) return -1;
		return maxIndex;
	}

	public static void main(String[] args) throws IOException {
		
		
		// define axes and histogram:
		Axis v1Axis = new Axis(8, 0, 1); // 10 bins from 0 to 1 (width = 0.1)
		Axis v2Axis = new Axis(8, 0, 1); // 10 bins from 0 to 1 (width = 0.1)
		Axis v3Axis = new Axis(8, 0, 1); // 10 bins from 0 to 1 (width = 0.1)
		Axis v4Axis = new Axis(8, 0, 1); // 10 bins from 0 to 1 (width = 0.1)
		Axis v5Axis = new Axis(8, 0, 1); // 10 bins from 0 to 1 (width = 0.1)
		
		Histogram hist = new Histogram(v1Axis, v2Axis, v3Axis, v4Axis, v5Axis);
		Histogram histpart0 = new Histogram(v1Axis, v2Axis, v3Axis, v4Axis, v5Axis);
		Histogram histpart1 = new Histogram(v1Axis, v2Axis, v3Axis, v4Axis, v5Axis);
		Histogram histpart2 = new Histogram(v1Axis, v2Axis, v3Axis, v4Axis, v5Axis);
		

		
			int n = 5000;
			PidTestDataReader reader = new PidTestDataReader("/Users/Kalanie/neural-nets/particle-id/data/data_test_5000.txt", n);
			for(int j = 0; j < n; j++) {
				String[] values = reader.getNextEvent();
				int particleID = Integer.parseInt(values[0]);
				double v1 = Double.parseDouble(values[1]);
				double v2 = Double.parseDouble(values[2]);
				double v3 = Double.parseDouble(values[3]);
				double v4 = Double.parseDouble(values[4]);
				double v5 = Double.parseDouble(values[5]);
				if(particleID == 0) {
					histpart0.fill(v1 , v2, v3, v4, v5);
				}
				else if(particleID == 1) {
					histpart1.fill(v1 , v2, v3, v4, v5);
				}
				else if(particleID == 2) {
					histpart2.fill(v1 , v2, v3, v4, v5);
				}
				hist.fill(v1, v2, v3, v4, v5);
			}
			
			// Test data:
			int nRight = 0;
			int nWrong = 0;
			int nUnknown = 0;
			int m = 200;
			PidTestDataReader testreader = new PidTestDataReader("/Users/Kalanie/UNG-ENPhysics/test-data/pid_sim_200.txt", m);
			for(int j = 0; j < m; j++) {
				String[] values = testreader.getNextEvent();
				int particleID = Integer.parseInt(values[0]);
				double v1 = Double.parseDouble(values[1]);
				double v2 = Double.parseDouble(values[2]);
				double v3 = Double.parseDouble(values[3]);
				double v4 = Double.parseDouble(values[4]);
				double v5 = Double.parseDouble(values[5]);
				
				int p0counts = histpart0.getBinContent(hist.getBinIndices(v1, v2, v3, v4, v5));
				int p1counts = histpart1.getBinContent(hist.getBinIndices(v1, v2, v3, v4, v5));
				int p2counts = histpart2.getBinContent(hist.getBinIndices(v1, v2, v3, v4, v5));
				
				ArrayList<Integer> counts = new ArrayList<>();
				counts.add(p0counts);
				counts.add(p1counts);
				counts.add(p2counts);
				int maxIndex = getIndexOfMax(counts);
				
				if(maxIndex == particleID) nRight++;
				else if(maxIndex == -1) nUnknown++;
				else nWrong++;

				System.out.println("Test event number " + j);
				System.out.println("Correct answer is " + particleID);
				System.out.println(v1 + " " + v2 + " " + v3 + " " + v4 + " " + v5);
				System.out.println(p0counts);
				System.out.println(p1counts);
				System.out.println(p2counts);
				System.out.println(hist.getBinContent(hist.getBinIndices(v1, v2, v3, v4, v5)));
				System.out.println("Best guess is " + maxIndex);
				System.out.println("");
			}
			
			System.out.println("Final results:");
			System.out.println("Number right: " + nRight);
			System.out.println("Number wrong: " + nWrong);
			System.out.println("Number unknown: " + nUnknown);
			
		}
	}
