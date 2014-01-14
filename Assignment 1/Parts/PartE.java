package Parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Random;


import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemovePercentage;

public class PartE {
Instances data;
int noTrees;
	//constructor
	public PartE(Instances data, int noTrees){
		this.data = data;
		this.noTrees = noTrees;
	}
	private Instances randomizeAttributes(int i, Instances data4){
		Attribute attr = data4.attribute(i);
		if(data4.attribute(i).isNominal()){
			ArrayList accValues = Collections.list(attr.enumerateValues());
			for(int j=0; j< data4.numInstances(); j++){
				String  value  =  (String) accValues.get((int)(Math.random() * accValues.size()));
				data4.instance(j).setValue(i, value);
			}
		}
		else if(data4.attribute(i).isNumeric()){
			double lower=10000, upper=0;
			for(int j=0; j<data4.numAttributes(); j++){
				if(data4.instance(j).value(i) < lower){
					lower = data4.instance(j).value(i);
				}
				if(data4.instance(j).value(i) > upper){
					upper = data4.instance(j).value(i);
				}
			}
			for(int j=0; j<data4.numInstances(); j++){
				double value = lower + Math.random()*(upper - lower);
				data4.instance(j).setValue(i, value);
			}
		}
		return data4;
	}
	
	public double getError(Instances data1) throws Exception{
		String[] options = new String[2];
		options[0] = "-P";
		options[1] = "10";
		RemovePercentage removePercentage = new RemovePercentage();
		removePercentage.setOptions(options);
		removePercentage.setInputFormat(data1);
		Instances dataTraining = Filter.useFilter(data1, removePercentage);
		dataTraining.setClassIndex(dataTraining.numAttributes() - 1);
		
		removePercentage.setInputFormat(data1);
		removePercentage.setInvertSelection(true);
		Instances dataTest = Filter.useFilter(data1, removePercentage);
		dataTest.setClassIndex(dataTest.numAttributes() - 1);
		
		RandomForest randomForest = new RandomForest();
		String[] options1 = weka.core.Utils.splitOptions("-I "+ noTrees);
		randomForest.setOptions(options1);
		randomForest.buildClassifier(dataTraining);
		
		Evaluation eTest = new Evaluation(dataTraining);
//		eTest.crossValidateModel(randomForest, data, 5, new Random(1));
        eTest.evaluateModel(randomForest, dataTest);
//        System.out.println(eTest.rootMeanSquaredError());
        return eTest.rootMeanSquaredError();
//        return eTest.errorRate();
	}
	static class columnComparator implements Comparator {
	    private int columnToSortOn;
	    //contructor to set the column to sort on.
	    columnComparator(int columnToSortOn) {
	      this.columnToSortOn = columnToSortOn;
	    }
	    // Implement the abstract method which tells
	    // how to order the two elements in the array.
	    public int compare(Object o1, Object o2) {
	    // cast the object args back to string arrays
	        double[] row1 = (double[])o1;
	        double[] row2 = (double[])o2;
	        // compare the desired column values
	        if(row1[columnToSortOn] < row2[columnToSortOn]){
	        	return 1;
	        }
	        else if(row1[columnToSortOn] == row2[columnToSortOn]){
	        	return 0;
	        }
	        else{
	        	return -1;
	        }
//	        return row1[columnToSortOn].compareTo(row2[columnToSortOn]);
	    }
	}
//This execute will randomize the values and then set accordingly 
	public void executeRandomize() throws Exception{
		System.out.println("_____________PART E____________");
		System.out.println("__________Will randomize the attributes____________");
		data.setClassIndex(data.numAttributes() - 1);
		double standardError = getError(data);
		double [][] errors = new double[data.numAttributes()][2];
		for(int j=0;j<data.numAttributes() - 1; j++){
			Instances dataNew1 = new Instances(data);
			Instances dataNew = randomizeAttributes(j,dataNew1);
			double error = getError(dataNew);
	        errors[j][1] =  error ;//- standardError;
	        errors[j][0] =  j;
//	        System.out.println(errors[j][0] + " " + 
//	        	data.attribute((int)errors[j][0]).name() + " " + errors[j][1]);
		}
		Arrays.sort(errors,new columnComparator(1));
		for(int i=0;i<errors.length;i++){
//			System.out.println(data.attribute((int)errors[i][0]).name() + " " + errors[i][1]);
			
		}
		System.out.println("The three most influential attributes are:\n ");
		int best=3;  //can change to get diff no of best features
		
		for(int i=0; i<best; i++){
			System.out.println(errors[i][0] + " " + data.attribute((int)errors[i][0]).name() + " " + errors[i][1]);
		}
		Remove remove = new Remove();
		String options4 = "-R "; 
		options4 += (int)errors[0][0];
		for(int i=1; i<best; i++){
			options4 += "," + (int)errors[i][0];
		}
//		System.out.println("Going to test with these attributes only: " + options4);
		String[] options2 = weka.core.Utils.splitOptions(options4 + " V");
		remove.setOptions(options2);
		remove.setInputFormat(data);
		Instances data3Attribs = Filter.useFilter(data, remove);
		double newError = getError(data3Attribs);
		System.out.println("Error old: " + standardError + "\nvs\nError new:" + newError);
//		
	}
	
	
	
	
	
	//main executing body
	public void execute() throws Exception{
		System.out.println("_____________PART E____________");
		data.setClassIndex(data.numAttributes() - 1);
		double standardError = getError(data);
		double [][] errors = new double[data.numAttributes()][2];
		for(int j=0;j<data.numAttributes() - 1; j++){
			Remove remove = new Remove();
			String[] options1 = weka.core.Utils.splitOptions("-R "+ (j+1));
			remove.setOptions(options1);
			remove.setInputFormat(data);

			Instances dataNew = Filter.useFilter(data, remove);
//			System.out.println(data.numAttributes()+" "+dataNew.numAttributes());
			double error = getError(dataNew);
	        //System.out.println(j + "  " + standardError + "  " + error);
	        errors[j][1] =  error ;//- standardError;
	        errors[j][0] =  j;
//	       String strSummary = eTest.toSummaryString();
		}
		Arrays.sort(errors,new columnComparator(1));
		for(int i=0;i<errors.length;i++){
//			System.out.println(data.attribute((int)errors[i][0]).name() + " " + errors[i][1]);
			
		}
		System.out.println("The three most influential attributes are: ");
		for(int i=0; i<3; i++){
			System.out.println(data.attribute((int)errors[i][0]).name() + " " + errors[i][1]);
		}
		Remove remove = new Remove();
		String options4 = "-R "; 
		options4 += (int)errors[0][0];
		for(int i=1; i<3; i++){
			options4 += "," + (int)errors[i][0];
		}
//		System.out.println("Going to test with these attributes only: " + options4);
		String[] options2 = weka.core.Utils.splitOptions(options4 + " V");
		remove.setOptions(options2);
		remove.setInputFormat(data);
		Instances data3Attribs = Filter.useFilter(data, remove);
		double newError = getError(data3Attribs);
		System.out.println("Error old: " + standardError + "\nvs\nError new:" + newError);
//		
	}
}
