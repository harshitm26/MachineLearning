package Parts;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.SimpleCart;
//import weka.classifiers.trees.j48.PruneableClassifierTree;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class PartA {
	Instances data;
	
	//constructor
	public PartA(Instances data){
		this.data = data;
	}
	//main executing body
	public void execute() throws Exception{
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

        System.out.println("_________________PART A _______________");
		
		SimpleCart tree = new SimpleCart();
		try{
			tree.buildClassifier(dataTraining);			
			Evaluation eTest = new Evaluation(dataTraining);
	        eTest.evaluateModel(tree, dataTest);
	        String strSummary = eTest.toSummaryString();
	        System.out.println("Output using Gini impurity function___________________________");
	        System.out.println(strSummary);
		}
		catch(Exception c){
			c.printStackTrace();
		}
		
		SimpleCartEntropy treeEntropy = new SimpleCartEntropy();
		try{
			treeEntropy.buildClassifier(dataTraining);
			Evaluation eTest = new Evaluation(dataTraining);
	        eTest.evaluateModel(treeEntropy, dataTest);
	       
	        String strSummary = eTest.toSummaryString();
	        System.out.println("Output using Entropy impurity function________________________");
	        System.out.println(strSummary);
		}
		catch(Exception c){
			c.printStackTrace();
		}
		
		
	
	}
}
