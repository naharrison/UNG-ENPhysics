package edu.ung.phys;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PidStatTrackerTest {

	@Test
	public void test1() {
    ArrayList<Integer> pids = new ArrayList<>();
    pids.add(101);
    pids.add(102);
    pids.add(103);

    double ebarScale = 0.0;

    PidStatTracker statTracker = new PidStatTracker(pids, ebarScale);
    statTracker.addAxis(new Axis(4, 0.0, 4.0));
    statTracker.addAxis(new Axis(5, 100.0, 105.0));
    statTracker.initializeHistos();

    for(int k = 0; k < 100; k++) statTracker.trainWithEvent(101, 0.2, 104.2);
    for(int k = 0; k < 50; k++) statTracker.trainWithEvent(102, 0.2, 104.2);
    statTracker.trainWithEvent(102, 1.2, 101.2);
    statTracker.trainWithEvent(103, 1.2, 101.2);

    PredictionConfidencePair pcPair1 = statTracker.getPredictionConfidencePair(0.3, 104.3);
    assertEquals(ConfidenceLevel.HIGH, pcPair1.confidence);
    assertEquals((int) 101, (int) pcPair1.prediction);

    PredictionConfidencePair pcPair2 = statTracker.getPredictionConfidencePair(3.3, 100.3);
    assertEquals(ConfidenceLevel.UNKNOWN, pcPair2.confidence);
    assertEquals(null, pcPair2.prediction);

    PredictionConfidencePair pcPair3 = statTracker.getPredictionConfidencePair(1.3, 101.3);
    assertEquals(ConfidenceLevel.TIE, pcPair3.confidence);
    assertEquals(null, pcPair3.prediction);

    statTracker.testWithEvent(101, 0.3, 104.3);
    statTracker.testWithEvent(102, 0.3, 104.3);
    System.out.println("");
    statTracker.printTestResults();
  }

}
