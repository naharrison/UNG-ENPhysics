package edu.ung.phys.basicSim;

import org.junit.Test;
import static org.junit.Assert.*;

public class Vector3DTest {
	
	@Test
	public void test1() {
		Vector3D vec = new Vector3D(1.0, 1.0, 1.0);
		assertEquals(vec.magnitude(), Math.sqrt(3.0), 1e-6); // 3rd arg is tolerence (delta)
		assertEquals(vec.transverseComponent(), Math.sqrt(2.0), 1e-6);
		assertEquals(vec.phi(), Math.toRadians(45.0), 1e-6);
		assertEquals(vec.theta(), Math.atan2(Math.sqrt(2.0), 1.0), 1e-6);
	}

}
