package edu.ung.phys;

import edu.ung.phys.basicSim.*;
import edu.ung.phys.plot.Histogram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class PidAnalysis1 {
	
	public static void main(String[] args) throws IOException {
		
		// define axes and histogram:
		ArrayList<Axis> axes = new ArrayList<>();
		axes.add(new Axis(15, 0, 1)); // 10 bins from 0 to 1 (width = 0.1)
		axes.add(new Axis(15, 0, 1)); // 10 bins from 0 to 1 (width = 0.1)
		axes.add(new Axis(15, 0, 1)); // 10 bins from 0 to 1 (width = 0.1)
		axes.add(new Axis(15, 0, 1)); // 10 bins from 0 to 1 (width = 0.1)
		axes.add(new Axis(15, 0, 1)); // 10 bins from 0 to 1 (width = 0.1)
		int nVars = axes.size();
		
		double eBarScale = 0.0;
		int nParticleTypes = 3;
		ArrayList<Histogram> particleHistos = new ArrayList<>();
		for(int k = 0; k < nParticleTypes; k++) particleHistos.add(new Histogram(axes));
		
		// Training data
		int n = 200000;
		PidTestDataReader reader = new PidTestDataReader("/Users/Kalanie/neural-nets/particle-id/data/myFile.txt", n);
		for(int j = 0; j < n; j++) {
			String[] values = reader.getNextEvent();
			int particleID = Integer.parseInt(values[0]);
			ArrayList<Double> vars = new ArrayList<>();
			for(int k = 1; k <= nVars; k++) vars.add(Double.parseDouble(values[k]));

			particleHistos.get(particleID).fill(vars);
		}
		
		// Test data:
		int m = 5000;
		PidTestDataReader testreader = new PidTestDataReader("/Users/Kalanie/neural-nets/particle-id/data/data_test_5000.txt", m);
		
		ArrayList<Integer> totalOccurances = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		ArrayList<Integer> nUnknown = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		ArrayList<Integer> nTies = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		ArrayList<Integer> nLowCon_corr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		ArrayList<Integer> nLowCon_incorr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		ArrayList<Integer> nHighCon_corr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		ArrayList<Integer> nHighCon_incorr = new ArrayList<Integer>(Collections.nCopies(nParticleTypes, 0));
		
		
		for(int j = 0; j < m; j++) {
			String[] values = testreader.getNextEvent();
			int trueID = Integer.parseInt(values[0]);
			ArrayList<Double> vars =new ArrayList<>();
			for(int k = 1; k <= nVars; k++) vars.add(Double.parseDouble(values[k]));
			
			ArrayList<Integer> binIndices = particleHistos.get(0).getBinIndices(vars);
			
			totalOccurances.set(trueID, totalOccurances.get(trueID)+1);
			HashMap<Integer, Integer> countsForParticles = new HashMap<Integer, Integer>();
			for(int k = 0; k< nParticleTypes; k++) countsForParticles.put(k, particleHistos.get(k).getBinContent(binIndices));
			
			int pid1;
			//int pid2;
			//pid1 = pid2 = -1;
			pid1 = -1;
			int high1, high2;
			high1 = high2 = Integer.MIN_VALUE;
			for(Entry<Integer, Integer> entry : countsForParticles.entrySet()) {
				int pid = entry.getKey();
				int counts = entry.getValue();
				if(counts > high1) {
					high2 = high1;
					//pid2 = pid1;
					high1 = counts;
					pid1 = pid;
				}
				else if(counts > high2) {
					high2 = counts;
					//pid2 = pid;
				}
			}
			
			double eBar1 = eBarScale*Math.sqrt(high1);
			double eBar2 = eBarScale*Math.sqrt(high2);
			
			if(high1 == 0) nUnknown.set(trueID, nUnknown.get(trueID) + 1);
			else if(high1 == high2) nTies.set(trueID, nTies.get(trueID) + 1);
			else if(high1 - eBar1 < high2 + eBar2 && pid1 == trueID) nLowCon_corr.set(trueID, nLowCon_corr.get(trueID)+1);
			else if(high1 - eBar1 < high2 + eBar2 && pid1 != trueID) nLowCon_incorr.set(trueID, nLowCon_incorr.get(trueID)+1);
			else if(high1 - eBar1 > high2 + eBar2 && pid1 == trueID) nHighCon_corr.set(trueID,  nHighCon_corr.get(trueID)+1);
			else if(high1 - eBar1 > high2 + eBar2 && pid1 != trueID) nHighCon_incorr.set(trueID, nHighCon_incorr.get(trueID)+1);
		
			
			////System.out.println("Test event number " + j);
			////System.out.println("Correct answer is " + trueID);
			////System.out.println("values = " + vars);
			////System.out.println("indices = " + particleHistos.get(0).getBinIndices(vars));
			////System.out.println("Training results:");
			////for(int k = 0; k < nParticleTypes; k++) {
			////	System.out.println("  " + k + ": " + countsForParticles.get(k));
			////}
			////System.out.println("Rankings:");
			////System.out.println("  " + pid1 + ": " + high1);
			////System.out.println("  " + pid2 + ": " + high2);
			////System.out.println("");
		}

		System.out.println("Total Occurances " + totalOccurances);
		System.out.println("Low Confidence Correct " + nLowCon_corr);
		System.out.println("Low Confidence Incorrect " + nLowCon_incorr);
		System.out.println("High Confidence Correct " + nHighCon_corr);
		System.out.println("High Confidence Incorrect " + nHighCon_incorr);
		System.out.println("Number Unknown " + nUnknown);
		System.out.println("Number of Ties " +nTies);
		System.out.println("");
		System.out.println("EBar Scale " + eBarScale);
		System.out.println("");
		System.out.println("Efficiency Particle 0 = " + ((double) nHighCon_corr.get(0)/totalOccurances.get(0)));
		System.out.println("Purity Particle 0 = " + ((double) nHighCon_corr.get(0)/(nHighCon_corr.get(0)+nHighCon_incorr.get(0))));
		System.out.println("");
		System.out.println("Efficiency Particle 1 = " + ((double) nHighCon_corr.get(1)/totalOccurances.get(1)));
		System.out.println("Purity Particle 1 = " + ((double) nHighCon_corr.get(1)/(nHighCon_corr.get(1)+nHighCon_incorr.get(1))));
		System.out.println("");
		System.out.println("Efficiency Particle 2 = " + ((double) nHighCon_corr.get(2)/totalOccurances.get(2)));
		System.out.println("Purity Particle 2 = " + ((double) nHighCon_corr.get(2)/(nHighCon_corr.get(2)+nHighCon_incorr.get(2))));
		
	}




}
