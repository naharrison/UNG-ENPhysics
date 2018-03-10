package edu.ung.phys;

import org.junit.Test;
import static org.junit.Assert.*;

public class AxisTest {
	
	@Test
	public void test1() {
		Axis axis = new Axis(10, 0.0, 1.0);
		assertEquals(axis.getBinWidth(0), 0.1, 1e-6); // 3rd arg is tolerence (delta)
		assertEquals(axis.getBin(0.0), 0);
		assertEquals(axis.getBin(-5.5), -1);
		assertEquals(axis.getBin(1.0), 10);
		assertEquals(axis.getBin(3.0), 10);
		assertEquals(axis.getBin(0.1), 1);
		assertEquals(axis.getBin(0.25), 2);
	}
	
	@Test
	public void test2() {
		Axis axis = new Axis(20, 1.0, 2.0);
		assertEquals(axis.getBinWidth(0), 0.05, 1e-6); // 3rd arg is tolerence (delta)
		assertEquals(axis.getBin(1.0), 0);
		assertEquals(axis.getBin(0.5), -1);
		assertEquals(axis.getBin(2.0), 20);
		assertEquals(axis.getBin(3.0), 20);
		assertEquals(axis.getBin(1.05), 1);
		assertEquals(axis.getBin(1.11), 2);
	}


}