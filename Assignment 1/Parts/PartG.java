package Parts;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.MarginCurve;
import weka.classifiers.trees.RandomForest;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class PartG {
	Instances data;
	//constructor
		public PartG(Instances data){
			this.data = data;
		}
		//main executing body
		public void execute() throws Exception{
			System.out.println("______________________PART G__________________");
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
			
			try{
				RandomForest randomForest = new RandomForest();
				randomForest.buildClassifier(dataTraining);
				
				Evaluation evaluation = new Evaluation(dataTraining);
				evaluation.evaluateModel(randomForest, dataTest);
				FastVector predictions = evaluation.predictions();
				
				MarginCurve marginCurve = new MarginCurve();
				Instances margins = marginCurve.getCurve(predictions);
				
				double weightedSum =0, weightedSumOfSquaredMargins = 0;
				for(int i=0; i<margins.numInstances()-1; i++) {
					Instance instance = margins.instance(i);
					double margin = instance.value(0);
					int numOfInstances = (int)instance.value(1);
					weightedSum += margin * (double)numOfInstances;
					weightedSumOfSquaredMargins += margin * margin * (double)numOfInstances;
				}
				
				
				double expectationOfMargins = weightedSum / (double)(margins.numInstances()-1);
				double strength = expectationOfMargins;
				double expectationOfMarginsSquared = weightedSumOfSquaredMargins / (double)(margins.numInstances()-1);

				double varianceOfMargins = expectationOfMarginsSquared - (expectationOfMargins * expectationOfMargins);
				double strengthSq = strength*strength;
				double correlation = varianceOfMargins / ( 1.0 - strengthSq);
				
				double upperErrorBound = correlation * (1 - strengthSq)/strengthSq;
				System.out.println("Strength: " + expectationOfMargins);
				System.out.println("Correlation: " + correlation);
				System.out.println("Upper bound on error: " + upperErrorBound);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
}
