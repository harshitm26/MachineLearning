package Parts;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.SimpleCart;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.instance.RemovePercentage;
import java.util.Arrays;

public class PartD {
	Instances data;
	
	//constructor
	public PartD(Instances data){
		this.data = data;
	}
	private Instances setMedians(Instances data1, double[] mediansOrMode){
		for(int j=0; j<data1.numInstances(); j++){
			for(int i=0; i<data1.numAttributes(); i++){
				if(data1.instance(j).isMissing(i)){
					data1.instance(j).setValue(i, mediansOrMode[i]);
				}
			}
		}
		return data1;
	}
	
	private double[] getMedianValues(Instances missingValues){
		double[] mediansOrMode = new double[missingValues.numAttributes()];
		for(int i=0; i<missingValues.numAttributes(); i++){
			if(missingValues.classAttribute().isNominal()){
				mediansOrMode[i] = missingValues.meanOrMode(i);
			}
			else if(missingValues.classAttribute().isNumeric()){
				double[] values = missingValues.attributeToDoubleArray(i);
				Arrays.sort(values);
				int middleIndex;
				if(missingValues.numAttributes()%2 == 0){
					middleIndex = missingValues.numAttributes()/2-1;
				}
				else{
					middleIndex = missingValues.numAttributes()/2;
				}
				mediansOrMode[i] = values[middleIndex];
			}
		}
		
		return mediansOrMode;
	}
	//main executing body	
	public void execute() throws Exception{
		System.out.println("_____________PART D________________");
		data.setClassIndex(data.numAttributes() - 1);
		
		//selection of --------------TRAINING data --------------
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
		//missing value -----MEDIAN------------
		//using median values for the missing values 
		double[] medians = getMedianValues(dataTraining);
		Instances dataTrainingNew = setMedians(dataTraining, medians);
		Instances dataTestNew = setMedians(dataTest, medians);
		
		SimpleCart tree1 = new SimpleCart();
		tree1.buildClassifier(dataTrainingNew);
		
		Evaluation eTest = new Evaluation(dataTrainingNew);
        eTest.evaluateModel(tree1, dataTestNew);
        
        String strSummary = eTest.toSummaryString();
        System.out.println("Replace data with MEDIAN _________");
		System.out.println("Correctly classified percentage: " + (1-eTest.errorRate())*100);
//		System.out.println(strSummary);
		
		
		//missing value ---- MEANS ---------------
		//using mean values for the missing values 
		ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();
		SimpleCart tree = new SimpleCart();
		FilteredClassifier filteredClassifier = new FilteredClassifier();
		filteredClassifier.setFilter(replaceMissingValues);
		
		filteredClassifier.setClassifier(tree);
		filteredClassifier.buildClassifier(dataTraining);
		
		double correctlyClassified = 0;
		for (int i = 0; i < dataTest.numInstances(); i++) {
			   double pred = filteredClassifier.classifyInstance(dataTest.instance(i));
			   if(dataTest.classAttribute().value((int) dataTest.instance(i).classValue()) == dataTest.classAttribute().value((int) pred)){
				   correctlyClassified ++;
			   }
			   
//			   System.out.print(", actual: " + dataTest.classAttribute().value((int) dataTest.instance(i).classValue()));
//			   System.out.println("predicted: " + dataTest.classAttribute().value((int) pred));
		}
		System.out.println("Replace data with MEAN _________");
		System.out.println("Correctly classified percentage : " + (correctlyClassified/dataTest.numInstances())*100);
		
				
		//missing value ---- MEDIAN -------
		
	}
}
