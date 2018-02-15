package edu.ung.phys.hist;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author naharrison
 * Histogram class for an arbitrary number of dimensions, x1, x2, x3, ...
 * Bin numbers start at 0.
 */
public class Histogram {

	public ArrayList<Axis> axes;
	public NDimensionalArray<Integer> counts;
	public int outOfRangeCount;
	
	private int nTotalBins;
	private ArrayList<Integer> nBinsList;
	

	public Histogram(ArrayList<Axis> axes) {
		this.axes = axes;
		this.outOfRangeCount = 0;
		this.nBinsList = new ArrayList<>();
		int totBins = 1;
		for(Axis axis : axes) {
			totBins = totBins * axis.nDivisions;
			nBinsList.add(axis.nDivisions);
		}
		this.nTotalBins = totBins;
		this.counts = new NDimensionalArray<Integer>(nBinsList);
		for(int j = 0; j < this.counts.objects.size(); j++) {
			this.counts.set(j, 0);
		}
	}


	public Histogram(Axis...axes) {
		this(new ArrayList<Axis>(Arrays.asList(axes)));
	}

	
	public int getTotalBins() {
		return nTotalBins;
	}
	

	public ArrayList<Integer> getBinIndices(ArrayList<Double> values) {
		ArrayList<Integer> result = new ArrayList<>();
		for(int j = 0; j < values.size(); j++) {
			result.add(axes.get(j).getBin(values.get(j)));
		}
		return result;
	}


	public ArrayList<Integer> getBinIndices(Double...values) {
		return getBinIndices(new ArrayList<Double>(Arrays.asList(values)));
	}
	

	public void fill(ArrayList<Double> values) {
		ArrayList<Integer> indices = getBinIndices(values);
		for(int j = 0; j < indices.size(); j++) {
			if(indices.get(j) == -1 || indices.get(j) == axes.get(j).nDivisions) {
				outOfRangeCount++;
				return;
			}
		}
		int index = counts.getIndexFromIndices(indices);
		counts.set(index, counts.get(index) + 1);
	}
	
	
	public void fill(Double...values) {
		fill(new ArrayList<Double>(Arrays.asList(values)));
	}
	

	public int getBinContent(ArrayList<Integer> indices) {
		int index = counts.getIndexFromIndices(indices);
		return counts.objects.get(index);
	}


	public int getBinContent(Integer...indices) {
		return getBinContent(new ArrayList<Integer>(Arrays.asList(indices)));
	}
	
}
