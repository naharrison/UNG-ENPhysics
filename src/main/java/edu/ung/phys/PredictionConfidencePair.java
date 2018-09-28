package edu.ung.phys;

public class PredictionConfidencePair {

  Integer prediction;
  ConfidenceLevel confidence;

  public PredictionConfidencePair(Integer prediction, ConfidenceLevel confidence) {
    this.prediction = prediction;
    this.confidence = confidence;
  }

}
