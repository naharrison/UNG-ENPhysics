package edu.ung.phys.hist;

import org.junit.Test;
import static org.junit.Assert.*;

public class HistogramTest {

	@Test
	public void test1() {
		Axis xAxis = new Axis(10, 0.0, 100.0);
		Axis yAxis = new Axis(4, 10.0, 20.0);
		Axis zAxis = new Axis(5, 0.0, 1.0);
		
		Histogram hist = new Histogram(xAxis, yAxis, zAxis);
		hist.fill(5.0, 11.0, 0.1); // (0, 0, 0)
		hist.fill(5.0, 11.0, 0.1); // (0, 0, 0)
		hist.fill(11.0, 13.0, 0.3); // (1, 1, 1)
		hist.fill(11.0, 16.0, 0.3); // (1, 2, 1)
		hist.fill(99.0, 19.0, 0.9); // (9, 3, 4)
		hist.fill(200.0, 13.0, 0.3); // (out of range, 1, 1)
		hist.fill(200.0, 9.0, 0.3); // (out of range, out of range, 1)
		
		assertEquals(hist.getBinContent(0, 0, 0), 2);
		assertEquals(hist.getBinContent(0, 0, 1), 0);
		assertEquals(hist.getBinContent(1, 1, 1), 1);
		assertEquals(hist.getBinContent(1, 2, 1), 1);
		assertEquals(hist.getBinContent(9, 3, 4), 1);
		assertEquals(hist.outOfRangeCount, 2);
		
		assertEquals(hist.getBinContent(hist.getBinIndices(11.0, 16.0, 0.3)), 1);
		assertEquals(hist.getBinContent(hist.getBinIndices(5.0, 11.0, 0.1)), 2);
	}
	
}
