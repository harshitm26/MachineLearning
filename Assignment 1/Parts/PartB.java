package Parts;

import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.*;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class PartB {
	Instances data;
	Double setDeltaError;
	//constuctor
	public PartB(Instances data, Double setDeltaError){
		this.data = data;
		this.setDeltaError = setDeltaError;
	}
	//main executing body
	public int execute()throws Exception{
		data.setClassIndex(data.numAttributes() - 1);
		
		RemovePercentage removePercentage = new RemovePercentage();
		String[] options = new String[2];
		options[0] = "-P";
		options[1] = "10";
		removePercentage.setOptions(options);
		removePercentage.setInputFormat(data);
		Instances dataTraining = Filter.useFilter(data, removePercentage);
		dataTraining.setClassIndex(dataTraining.numAttributes() - 1);
		
		removePercentage.setInputFormat(data);
		removePercentage.setInvertSelection(true);
		Instances dataTest = Filter.useFilter(data, removePercentage);
		dataTest.setClassIndex(dataTest.numAttributes() - 1);
		
		RandomForest randomForest = new RandomForest();
		int cN = 1;
		double[] prevErrors = new double[cN];
		System.out.println("_________________PART B _______________");
		int noTrees;
		boolean end = true;
		int startValue = 600;
		for(int i=startValue;;){
			end = true;
			String[] options1 = weka.core.Utils.splitOptions("-I "+ i);
			randomForest.setOptions(options1);
			
			try{
				randomForest.buildClassifier(dataTraining);
				
				Evaluation eTest = new Evaluation(dataTraining);
		        eTest.evaluateModel(randomForest, dataTest);
		        
		        String strSummary = eTest.toSummaryString();
		        System.out.println("Number of trees:" + i + "_____");
//        		System.out.println(strSummary);
        		System.out.println("root mean squared error           "+eTest.rootMeanSquaredError());
        		System.out.println("mean squared error                "+Math.pow(2,eTest.rootMeanSquaredError()));

//		        if(i!=512 && i!=256){
		        if(i!=startValue){
		        	for(int j=0;j<cN-1;j++){
		        		if(Math.abs(prevErrors[j] - prevErrors[j+1]) < setDeltaError){
		        			end = false;
		        			System.out.println("Still less");
		        		}
		        	}
		        	if(Math.abs(prevErrors[cN - 1] - eTest.errorRate()) < setDeltaError){
		        		System.out.println("Still less");
		        		end = false;
		        	}
		        	if(end){
		        		System.out.println("Number of Trees required: " + i);
		        		noTrees = i;
		        		break;
		        	}
		        	else{
		        		for(int j=0;j<cN-1;j++){
		        			System.out.println("Error " + j + " " + Math.abs(prevErrors[j]-prevErrors[j+1]));
		        		}
		        		System.out.println("Error "+ (cN-1) +" " + Math.abs(prevErrors[cN-1] - eTest.errorRate()));
		        	}
		        }
		       for(int j=0;j<prevErrors.length -1;j++){
		    	   prevErrors[j] = prevErrors[j+1];
		       }
//		       prevErrors[prevErrors.length-1] = eTest.errorRate();
		       prevErrors[prevErrors.length-1] = Math.pow(eTest.rootMeanSquaredError(),2);
		       i = i/2;
			}
			catch(Exception c){
				c.printStackTrace();
			}
		}
		return noTrees;
	}
}



