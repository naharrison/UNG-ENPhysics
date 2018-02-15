package edu.ung.phys.hist;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class NDimensionalArrayTest {

	@Test
	public void test1() {
		NDimensionalArray<Integer> nArray = new NDimensionalArray<Integer>(3, 3, 3);

		assertEquals(nArray.getTotalElements(), 27);

		assertEquals(nArray.getIndexFromIndices(0, 0, 0), 0);
		assertEquals(nArray.getIndexFromIndices(2, 2, 2), 26);
		assertEquals(nArray.getIndexFromIndices(0, 2, 0), 6);
		assertEquals(nArray.getIndexFromIndices(0, 2, 1), 15);

		ArrayList<Integer> indices1 = nArray.getIndicesFromIndex(0);
		assertEquals(indices1.get(0), (Integer) 0);
		assertEquals(indices1.get(1), (Integer) 0);
		assertEquals(indices1.get(2), (Integer) 0);

		ArrayList<Integer> indices2 = nArray.getIndicesFromIndex(15);
		assertEquals(indices2.get(0), (Integer) 0);
		assertEquals(indices2.get(1), (Integer) 2);
		assertEquals(indices2.get(2), (Integer) 1);
	}
	

	@Test
	public void test2() {
		NDimensionalArray<Integer> nArray = new NDimensionalArray<Integer>(2, 3, 2);

		assertEquals(nArray.getTotalElements(), 12);

		assertEquals(nArray.getIndexFromIndices(0, 0, 0), 0);
		assertEquals(nArray.getIndexFromIndices(1, 2, 1), 11);
		assertEquals(nArray.getIndexFromIndices(0, 2, 0), 4);
		assertEquals(nArray.getIndexFromIndices(0, 2, 1), 10);
		
		ArrayList<Integer> indices1 = nArray.getIndicesFromIndex(10);
		assertEquals(indices1.get(0), (Integer) 0);
		assertEquals(indices1.get(1), (Integer) 2);
		assertEquals(indices1.get(2), (Integer) 1);
	}

}
