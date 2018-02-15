package edu.ung.phys.hist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author naharrison
 * Stores objects in an array with an arbitrary number of dimensions, x1, x2, x3, ...
 * Works by converting N-indices to a single index and vice versa.
 * All indices start from 0.
 * e.g. the first mini-cube of a rubik's cube can be expressed (0,0,0) or 0.
 * e.g. the last mini-cube of a rubik's cube can be expressed (2,2,2) or 26.
 */
public class NDimensionalArray<T> {
	
	public ArrayList<T> objects;
	public ArrayList<Integer> sizes; // sizes.size() is the number of dimensions, sizes.get(i) is the size of dimension i
	private int nDimensions;
	private int totalElements;

	
	public NDimensionalArray(ArrayList<Integer> sizes) {
		this.sizes = sizes;
		this.nDimensions = sizes.size();
		int totalElements = 1;
		for(Integer size : sizes) totalElements = totalElements * size;
		this.totalElements = totalElements;
		this.objects = new ArrayList<T>(Collections.nCopies(totalElements, null));
	}

	
	public NDimensionalArray(Integer...sizes) {
		this(new ArrayList<Integer>(Arrays.asList(sizes)));
	}
	
	
	public int getTotalElements() {
		return totalElements;
	}
	
	
	/**
	 * e.g. for 4 dimensions w/ indices w,x,y,z
	 * and sizes Nw, Nx, Ny, Nz:
	 * index = w + Nw*x + Nw*Nx*y + Nw*Nx*Ny*z
	 */
	public int getIndexFromIndices(ArrayList<Integer> indices) {
		int index = 0;
		for(int j = 0; j < nDimensions; j++) {
			int thisTerm = indices.get(j);
			for(int k = 0; k < j; k++) {
				thisTerm = thisTerm * sizes.get(k);
			}
			index = index + thisTerm;
		}
		return index;
	}
	
	
	public int getIndexFromIndices(Integer...indices) {
		return getIndexFromIndices(new ArrayList<Integer>(Arrays.asList(indices)));
	}

	
	/**
	 * Does the reverse of getIndexFromIndices (see above)
	 * e.g.   iz = I/(Nw*Nx*Ny)  rounded down if any remainder
	 *        iy = (I - Nw*Nx*Ny*iz) / (Nw*Nx)  rounded down if any remainder
	 *        ix = (I - Nw*Nx*Ny*iz - Nw*Nx*iy) / (Nw)  rounded down if any remainder
	 *        iw = (I - Nw*Nx*Ny*iz - Nw*Nx*iy - Nw*ix) / (1)  rounded down if any remainder
	 */
	public ArrayList<Integer> getIndicesFromIndex(int index) {
		ArrayList<Integer> indices = new ArrayList<>();
		int reducedIndex = index;
		for(int j = nDimensions-1; j >= 0; j--) {
			int numerator = reducedIndex;
			int denominator = 1;
			for(int k = 0; k < j; k++) {
				denominator = denominator * sizes.get(k);
			}
			int remainder = numerator%denominator;
			int thisIndex;
			if(remainder == 0) thisIndex = numerator/denominator;
			else thisIndex = (int) Math.floor(((double) numerator)/((double) denominator));
			indices.add(thisIndex);
			reducedIndex = reducedIndex - denominator*(thisIndex);
		}
		Collections.reverse(indices);
		return indices;
	}
	
	
}
