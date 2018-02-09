package edu.ung.phys.histo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author naharrison
 * Histogram class for an arbitrary number of dimensions, x1, x2, x3, ...
 * Bin numbers start at 1; 0 is reserved for counting out of range values.
 * Converts a bin w/ N-indices to a single index and vice versa.
 * e.g. the last bin of a rubik's cube can be expressed (3,3,3) or 27.
 */
public class Histogram {
	
	public ArrayList<Axis> axes;
	public ArrayList<Integer> counts;
	public int outOfRangeCount;
	
	private int nDimensions;
	private int totalBins;
	private ArrayList<Integer> nBinsList;
	
	public Histogram(ArrayList<Axis> axes) {
		this.axes = axes;
		this.outOfRangeCount = 0;
		this.nDimensions = axes.size();
		this.nBinsList = new ArrayList<>();
		int totBins = 1;
		for(Axis axis : axes) {
			totBins = totBins * axis.nDivisions;
			nBinsList.add(axis.nDivisions);
		}
		this.counts = new ArrayList<Integer>(Collections.nCopies(totBins, 0));
		this.totalBins = totBins;
	}
	
	public Histogram(Axis...axes) {
		this(new ArrayList<Axis>(Arrays.asList(axes)));
	}
	
	public int getTotalBins() {
		return totalBins;
	}
	
	/**
	 * e.g. for 4 dimensions w/ indices w,x,y,z
	 * and number of bin Nw, Nx, Ny, Nz:
	 * index = w + Nw*(x-1) + Nw*Nx*(y-1) + Nw*Nx*Ny*(z-1)
	 *   = (w-1) + Nw*(x-1) + Nw*Nx*(y-1) + Nw*Nx*Ny*(z-1) + 1
	 */
	public int getIndexFromIndices(ArrayList<Integer> indicies) {
		int index = 1;
		for(int j = 0; j < nDimensions; j++) {
			int thisTerm = indicies.get(j) - 1;
			for(int k = 0; k < j; k++) {
				thisTerm = thisTerm * nBinsList.get(k);
			}
			index = index + thisTerm;
		}
		return index;
	}
	
	/**
	 * Does the reverse of getIndexFromIndicies (see above)
	 * e.g.   iz = I/(Nw*Nx*Ny)  rounded up if any remainder
	 *        iy = (I - Nw*Nx*Ny*(iz-1)) / (Nw*Nx)  rounded up if any remainder
	 *        ix = (I - Nw*Nx*Ny*(iz-1) - Nw*Nx*(iy-1)) / (Nw)  rounded up if any remainder
	 *        iw = (I - Nw*Nx*Ny*(iz-1) - Nw*Nx*(iy-1) - Nw*(ix-1)) / (1)  rounded up if any remainder
	 */
	public ArrayList<Integer> getIndicesFromIndex(int index) {
		ArrayList<Integer> indices = new ArrayList<>();
		int reducedIndex = index;
		for(int j = nDimensions-1; j >= 0; j--) {
			int numerator = reducedIndex;
			int denominator = 1;
			for(int k = 0; k < j; k++) {
				denominator = denominator * nBinsList.get(k);
			}
			int remainder = numerator%denominator;
			int thisIndex;
			if(remainder == 0) thisIndex = numerator/denominator;
			else thisIndex = (int) Math.ceil(((double) numerator)/((double) denominator));
			indices.add(thisIndex);
			reducedIndex = reducedIndex - denominator*(thisIndex-1);
		}
		Collections.reverse(indices);
		return indices;
	}

	public ArrayList<Integer> getBinIndices(double...values) {
		ArrayList<Integer> result = new ArrayList<>();
		for(int j = 0; j < values.length; j++) {
			result.add(axes.get(j).getBin(values[j]));
		}
		return result;
	}

	public ArrayList<Integer> getBinIndices(ArrayList<Double> values) {
		ArrayList<Integer> result = new ArrayList<>();
		for(int j = 0; j < values.size(); j++) {
			result.add(axes.get(j).getBin(values.get(j)));
		}
		return result;
	}

}