package Parts;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class PartF{
	Instances data;
	int noTrees;
	
	//constructor
	public PartF(Instances data, int noTrees){
		this.data = data;
		this.noTrees = noTrees;
	}
	//main executing body
	public void execute() throws Exception{
		System.out.println("_____________PART F________________");
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
		
		String[] options1 = weka.core.Utils.splitOptions("-I "+ noTrees);
		randomForest.setOptions(options1);
		randomForest.buildClassifier(dataTraining);
		
		Evaluation eTest = new Evaluation(dataTraining);
        eTest.evaluateModel(randomForest, dataTest);
        
//        String strSummary = eTest.toSummaryString();
//		System.out.println(strSummary);
		System.out.println("Out of bag Error as the classifier was build: " + randomForest.measureOutOfBagError()*100);
	}
}
