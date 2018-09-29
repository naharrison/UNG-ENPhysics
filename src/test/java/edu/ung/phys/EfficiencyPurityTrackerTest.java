package edu.ung.phys;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class EfficiencyPurityTrackerTest {

  @Test
  public void test1() {
    ArrayList<Integer> ids = new ArrayList<>();
    ids.add(101);
    ids.add(102);
    ids.add(103);
    EfficiencyPurityTracker eptracker = new EfficiencyPurityTracker(ids);

    for(int k = 0; k < 20; k++) eptracker.trackActualPredicted(101, 101, ConfidenceLevel.HIGH);
    for(int k = 0; k < 4; k++) eptracker.trackActualPredicted(102, 101, ConfidenceLevel.HIGH);
    for(int k = 0; k < 3; k++) eptracker.trackActualPredicted(103, 101, ConfidenceLevel.HIGH);

    for(int k = 0; k < 30; k++) eptracker.trackActualPredicted(102, 102, ConfidenceLevel.HIGH);
    for(int k = 0; k < 3; k++) eptracker.trackActualPredicted(101, 102, ConfidenceLevel.HIGH);
    for(int k = 0; k < 6; k++) eptracker.trackActualPredicted(103, 102, ConfidenceLevel.HIGH);

    for(int k = 0; k < 25; k++) eptracker.trackActualPredicted(103, 103, ConfidenceLevel.HIGH);
    for(int k = 0; k < 1; k++) eptracker.trackActualPredicted(101, 103, ConfidenceLevel.HIGH);
    for(int k = 0; k < 1; k++) eptracker.trackActualPredicted(102, 103, ConfidenceLevel.HIGH);

    assertEquals(20.0/24.0, eptracker.getEfficiency(101), 0.00001);
    assertEquals(20.0/27.0, eptracker.getPurity(101), 0.00001);

    assertEquals(30.0/35.0, eptracker.getEfficiency(102), 0.00001);
    assertEquals(30.0/39.0, eptracker.getPurity(102), 0.00001);

    assertEquals(25.0/34.0, eptracker.getEfficiency(103), 0.00001);
    assertEquals(25.0/27.0, eptracker.getPurity(103), 0.00001);
  }


  @Test
  public void test2() {
    ArrayList<Integer> ids = new ArrayList<>();
    ids.add(101);
    ids.add(102);
    ids.add(103);
    EfficiencyPurityTracker eptracker = new EfficiencyPurityTracker(ids);

    for(int k = 0; k < 20; k++) eptracker.trackActualPredicted(101, 101, ConfidenceLevel.HIGH);
    for(int k = 0; k < 4; k++) eptracker.trackActualPredicted(102, 101, ConfidenceLevel.HIGH);
    for(int k = 0; k < 3; k++) eptracker.trackActualPredicted(103, 101, ConfidenceLevel.HIGH);

    for(int k = 0; k < 30; k++) eptracker.trackActualPredicted(102, 102, ConfidenceLevel.HIGH);
    for(int k = 0; k < 3; k++) eptracker.trackActualPredicted(101, 102, ConfidenceLevel.HIGH);
    for(int k = 0; k < 6; k++) eptracker.trackActualPredicted(103, 102, ConfidenceLevel.HIGH);

    for(int k = 0; k < 25; k++) eptracker.trackActualPredicted(103, 103, ConfidenceLevel.HIGH);
    for(int k = 0; k < 1; k++) eptracker.trackActualPredicted(101, 103, ConfidenceLevel.HIGH);
    for(int k = 0; k < 1; k++) eptracker.trackActualPredicted(102, 103, ConfidenceLevel.HIGH);

    eptracker.trackActualPredicted(101, 101, ConfidenceLevel.LOW);
    eptracker.trackActualPredicted(101, 102, ConfidenceLevel.LOW);
    eptracker.trackActualPredicted(101, 102, ConfidenceLevel.UNKNOWN);
    eptracker.trackActualPredicted(101, 102, ConfidenceLevel.TIE);

    assertEquals(20.0/28.0, eptracker.getEfficiency(101), 0.00001);
    assertEquals(20.0/27.0, eptracker.getPurity(101), 0.00001);

    assertEquals(30.0/35.0, eptracker.getEfficiency(102), 0.00001);
    assertEquals(30.0/39.0, eptracker.getPurity(102), 0.00001);

    assertEquals(25.0/34.0, eptracker.getEfficiency(103), 0.00001);
    assertEquals(25.0/27.0, eptracker.getPurity(103), 0.00001);
  }

}
