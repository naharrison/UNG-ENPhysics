package edu.ung.phys;

import java.io.IOException;
// see http://neuroph.sourceforge.net/javadoc/index.html
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import edu.ung.phys.basicSim.PidTestDataReader;

public class NeurophPid {

  public static void main(String[] args) throws IOException {
    // create training set (logical XOR function)
    DataSet trainingSet = new DataSet(6, 4);
    DataSet testingSet = new DataSet(6,4);
    int n = 150;
    
    String[] files = {System.getenv("DATASAMPLES")+"/e1f/Pid-Data/pidout-6537.txt", System.getenv("DATASAMPLES") + "/e1f/Pid-Data/pidout-327307.txt"};
//This loop is for reading in both files for training and testing respectively
//Loop 0< 1 because training data set i used for testing and training
    
	PidTestDataReader reader = new PidTestDataReader(files[0], n);
	for(int j = 0; j < n; j++) {
		String[] values = reader.getNextEvent();
		int particleID = Integer.parseInt(values[0]);
		double v1 = Double.parseDouble(values[1]);
		double v2 = Double.parseDouble(values[2]);
		double v3 = Double.parseDouble(values[3]);
		double v4 = Double.parseDouble(values[4]);
		double v5 = Double.parseDouble(values[5]);
		double v6 = Double.parseDouble(values[6]);
		
		//Putting the Data in with its pid. the 1 corresponds to the pid from the left starting at 0
		if(particleID == 211) {
			trainingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{1, 0, 0, 0}));
		}
		else if(particleID == 321) {
			trainingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{0, 1, 0, 0}));
		}
		else if(particleID == 2212) {
			trainingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{0, 0, 1, 0}));
		}
		else if(particleID == -11) {
			trainingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{0, 0, 0, 1}));
		}
	}
	/*
    PidTestDataReader readert = new PidTestDataReader(files[1], n);
	for(int j = 0; j < 10000; j++) {
		String[] values = readert.getNextEvent();
		int particleID = Integer.parseInt(values[0]);
		double v1 = Double.parseDouble(values[1]);
		double v2 = Double.parseDouble(values[2]);
		double v3 = Double.parseDouble(values[3]);
		double v4 = Double.parseDouble(values[4]);
		double v5 = Double.parseDouble(values[5]);
		double v6 = Double.parseDouble(values[6]);
		
		//Putting the Data in with its pid. the 1 corresponds to the pid from the left starting at 0
		if(particleID == 211) {
			testingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{1, 0, 0, 0}));
		}
		else if(particleID == 321) {
			testingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{0, 1, 0, 0}));
		}
		else if(particleID == 2212) {
			testingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{0, 0, 1, 0}));
		}
		else if(particleID == -11) {
			testingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5, v6}, new double[]{0, 0, 0, 1}));
		}
}
		*/
    // vv original training set line vv
	//trainingSet.addRow(new DataSetRow(new double[]{v1, v2, v3, v4, v5}, new double[]{0 , 1, 0}));
     
    // create multi layer perceptron
		//Look at Resilient Backpropogation
//the 6 and 4 correspond to input and output     middle number is H layer
    System.out.println("Generating Neural Network");
    MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 6, 8, 4);
    // learn the training set
    myMlPerceptron.learn(trainingSet);
    
    // test perceptron
    //System.out.println("Testing trained neural network");
    //testNeuralNetwork(myMlPerceptron, testingSet);
    
    //save trained neural network
    myMlPerceptron.save("myMlPerceptron.nnet");
    System.out.println("Saved!");
    /*
    // load saved neural network
    NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");
    
    // test loaded neural network
    System.out.println("Testing loaded neural network");
    testNeuralNetwork(loadedMlPerceptron, trainingSet);
    */
    //System.out.println(loadedMlPerceptron.getLearningRule().toString());
    
    }
	
	
	
  
  public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {

      int pid0Correct = 0;
      int pid1Correct = 0;
      int pid2Correct = 0;
      int pid3Correct = 0;
      int pid0Incorrect = 0;
      int pid1Incorrect = 0;
      int pid2Incorrect = 0;
      int pid3Incorrect = 0;
      int pid0PIncorrect = 0;
      int pid1PIncorrect = 0;
      int pid2PIncorrect = 0;
      int pid3PIncorrect = 0;
      int incorrect = 0;
    		  
    for(DataSetRow dataRow : testSet.getRows()) {
      nnet.setInput(dataRow.getInput());
      nnet.calculate();
      double[ ] networkOutput = nnet.getOutput();
      //System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
      double maxO = networkOutput[0];
      double max = dataRow.getDesiredOutput()[0];
      int indexOutput = 0;
      int indexDesired = 0;

      for (int i = 0; i < networkOutput.length; i++) 
      {
      	if (maxO < networkOutput[i]) 
      	{
      		maxO = networkOutput[i];
      		indexOutput = i;
      	}
      	
      	if (max < dataRow.getDesiredOutput()[i]) 
      	{
      		max = dataRow.getDesiredOutput()[i];
      		indexDesired = i;
      	}
      	
      	{
      	
      	}
      }
      //System.out.println(Arrays.toString(networkOutput));
      /*System.out.print(" Output: " + indexOutput );
      System.out.println(" " + "PID" + " " + dataRow.getDesiredOutput()[indexOutput]);
      */
      if (dataRow.getDesiredOutput()[indexOutput] ==  1.0)
      {
    	  if (indexOutput == 0){
    		  pid0Correct++;
    	  }
    	  else if(indexOutput == 1){
    		  pid1Correct++;
    	  }
    	  else if(indexOutput == 2){
    		  pid2Correct++;
    	  }
    	  else if(indexOutput == 3){
    		  pid3Correct++;
    	  } 
      }
      else {
    	  if (indexOutput == 0){
    		  pid0PIncorrect++;
    		  if (indexDesired == 0) {
    			  pid0Incorrect++;
    		  }
    		  else if (indexDesired == 1) {
    			  pid1Incorrect++;
    		  }
    		  else if (indexDesired == 2) {
    			  pid2Incorrect++;
    		  }
    		  else if (indexDesired == 3) {
    			  pid3Incorrect++;
    		  }
    		  
    	  }
    	  else if(indexOutput == 1){
    		  pid1PIncorrect++;
    		  if (indexDesired == 0) {
    			  pid0Incorrect++;
    		  }
    		  else if (indexDesired == 1) {
    			  pid1Incorrect++;
    		  }
    		  else if (indexDesired == 2) {
    			  pid2Incorrect++;
    		  }
    		  else if (indexDesired == 3) {
    			  pid3Incorrect++;
    		  }
    	  }
    	  else if(indexOutput == 2){
    		  pid2PIncorrect++;
    		  if (indexDesired == 0) {
    			  pid0Incorrect++;
    		  }
    		  else if (indexDesired == 1) {
    			  pid1Incorrect++;
    		  }
    		  else if (indexDesired == 2) {
    			  pid2Incorrect++;
    		  }
    		  else if (indexDesired == 3) {
    			  pid3Incorrect++;
    		  }

    	  }
    	  else if(indexOutput == 3){
    		  pid3PIncorrect++;
    		  if (indexDesired == 0) {
    			  pid0Incorrect++;
    		  }
    		  else if (indexDesired == 1) {
    			  pid1Incorrect++;
    		  }
    		  else if (indexDesired == 2) {
    			  pid2Incorrect++;
    		  }
    		  else if (indexDesired == 3) {
    			  pid3Incorrect++;
    		  }

    	  }

    	  //incorrect++;
	  }
      
      
    	  
      //add confidence by comparing the NN outputs to each other
      
    }
    incorrect = pid0Incorrect + pid1Incorrect + pid2Incorrect + pid3Incorrect;
    int totalentries = pid0Correct + pid1Correct + pid2Correct + pid3Correct + incorrect;
    System.out.println( "PID|");
    System.out.println( "0  | " + pid0Correct + " out of " + (pid0Incorrect + pid0Correct) + " Correct and " + pid0PIncorrect + " Incorrectly Identified as pid0");
    System.out.println( "1  | " + pid1Correct + " out of " + (pid1Incorrect + pid1Correct) + " Correct and " + pid1PIncorrect + " Incorrectly Identified as pid1");
    System.out.println( "2  | " + pid2Correct + " out of " + (pid2Incorrect + pid2Correct) + " Correct and " + pid2PIncorrect + " Incorrectly Identified as pid2");
    System.out.println( "3  | " + pid3Correct + " out of " + (pid3Incorrect + pid3Correct) + " Correct and " + pid3PIncorrect + " Incorrectly Identified as pid3");
    
    System.out.println("Incorrect " + incorrect);
    
    System.out.println("");

    
    double Purity0 = (double) pid0Correct / (double) (pid0Correct + pid0PIncorrect);
    double Purity1 = (double) pid1Correct / (double) (pid1Correct + pid1PIncorrect);
    double Purity2 = (double) pid2Correct / (double) (pid2Correct + pid2PIncorrect);
    double Purity3 = (double) pid3Correct / (double) (pid3Correct + pid3PIncorrect);
    
    double Efficiency0 = (double) pid0Correct / (double) (pid0Correct + pid0Incorrect);
    double Efficiency1 = (double) pid1Correct / (double) (pid1Correct + pid1Incorrect);
    double Efficiency2 = (double) pid2Correct / (double) (pid2Correct + pid2Incorrect);
    double Efficiency3 = (double) pid3Correct / (double) (pid3Correct + pid3Incorrect);
    
    System.out.println("Efficiency |" + Efficiency0 + "|" + Efficiency1 + "|" + Efficiency2 + "|" + Efficiency3 + "|");
    System.out.println("Purity     |" + Purity0 + "|" + Purity1 + "|" + Purity2 + "|" + Purity3 + "|");
    
    //Analysing the output of the Neural net
    
  
  }
  
}
