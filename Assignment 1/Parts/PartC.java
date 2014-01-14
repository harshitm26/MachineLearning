package Parts;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;

public class PartC {
	Instances data;
	int noTrees;
	
	//constructor
	public PartC(Instances data, int noTrees){
		this.data = data;
		this.noTrees = noTrees;
	}
	//main executing body
	public void execute() throws Exception{
		SimpleCart tree = new SimpleCart();
		data.setClassIndex(data.numAttributes() - 1);
		Evaluation eval1 = new Evaluation(data);
		eval1.crossValidateModel(tree, data, 5, new Random(1));
		System.out.println("_________________PART C _______________");
		System.out.println("SimpleCart Tree using 5 fold Cross Validation: " + eval1.toSummaryString(true));
		
		RandomForest randomForest = new RandomForest();
		String[] options1 = weka.core.Utils.splitOptions("-I "+ noTrees);

		Evaluation eval2 = new Evaluation(data);
		eval2.crossValidateModel(randomForest, data, 5, new Random(1));
		System.out.println("Random Forests using 5 fold Cross Validation: " + eval2.toSummaryString(true));
		System.out.println("Relative Errors: ");
		System.out.println("SimpleCart Tree:	" + eval1.errorRate()*100);
		System.out.println("RandomForests Tree:	" + eval2.errorRate()*100);
	
	}
}
