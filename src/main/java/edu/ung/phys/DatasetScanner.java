package edu.ung.phys;

import java.io.IOException;

import edu.ung.phys.basicSim.*;

public class DatasetScanner {
	public static void main(String[] args) throws IOException {
		
		int n = 6537;
		PidTestDataReader reader = new PidTestDataReader("/Users/Kalanie/data-samples/e1f/Pid-Data/pidout-327307.txt", n);
		//PidTestDataReader reader = new PidTestDataReader("/Users/Kalanie/data-samples/e1f/Pid-Data/pidout-6537.txt", n);
		
		double v1min = Double.MAX_VALUE;
		double v1max = Double.MIN_VALUE;
		double v2min = Double.MAX_VALUE;
		double v2max = Double.MIN_VALUE;
		double v3min = Double.MAX_VALUE;
		double v3max = Double.MIN_VALUE;
		double v4min = Double.MAX_VALUE;
		double v4max = Double.MIN_VALUE;
		double v5min = Double.MAX_VALUE;
		double v5max = Double.MIN_VALUE;
		double v6min = Double.MAX_VALUE;
		double v6max = Double.MIN_VALUE;

		for(int k = 0; k < n; k++) {
			String[] values = reader.getNextEvent();
			double v1 = Double.parseDouble(values[1]);
			double v2 = Double.parseDouble(values[2]);
			double v3 = Double.parseDouble(values[3]);
			double v4 = Double.parseDouble(values[4]);
			double v5 = Double.parseDouble(values[5]);
			double v6 = Double.parseDouble(values[6]);
			if(v1 < v1min) v1min = v1;
			if(v1 > v1max) v1max = v1;
			if(v2 < v2min) v2min = v2;
			if(v2 > v2max) v2max = v2;
			if(v3 < v3min) v3min = v3;
			if(v3 > v3max) v3max = v3;
			if(v4 < v4min) v4min = v4;
			if(v4 > v4max) v4max = v4;
			if(v5 < v5min) v5min = v5;
			if(v5 > v5max) v5max = v5;
			if(v6 < v6min) v6min = v6;
			if(v6 > v6max) v6max = v6;
		}

	    System.out.println("Results for Variable 1 ");
	    System.out.println("");
		System.out.println(v1min);
		System.out.println(v1max);
		System.out.println("");
		System.out.println("Results for Variable 2 ");
	    System.out.println("");
		System.out.println(v2min);
		System.out.println(v2max);
		System.out.println("");
		System.out.println("Results for Variable 3 ");
	    System.out.println("");
		System.out.println(v3min);
		System.out.println(v3max);
		System.out.println("");
		System.out.println("Results for Variable 4 ");
	    System.out.println("");
		System.out.println(v4min);
		System.out.println(v4max);
		System.out.println("");
		System.out.println("Results for Variable 5 ");
	    System.out.println("");
		System.out.println(v5min);
		System.out.println(v5max);
		System.out.println("");
		System.out.println("Results for Variable 6 ");
	    System.out.println("");
		System.out.println(v6min);
		System.out.println(v6max);
		
	}
}
