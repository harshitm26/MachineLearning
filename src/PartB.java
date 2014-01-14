import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import jsat.classifiers.CategoricalData;
import jsat.classifiers.ClassificationDataSet;
import jsat.classifiers.DataPoint;
import jsat.distributions.multivariate.NormalM;
import jsat.graphing.CategoryPlot;
import jsat.linear.DenseMatrix;
import jsat.linear.DenseVector;
import jsat.linear.Matrix;
import jsat.linear.Vec;

public class PartB {
/*	
	public static void buildArffFile(int size, int num, String filename) throws IOException{
        ClassificationDataSet dataSet = new ClassificationDataSet(2, new CategoricalData[0], new CategoricalData(num));
        NormalM normal;
        Matrix covariance = new DenseMatrix(new double[][]
        {
            {1.0, 0.0},
            {0.0, 1.0}  
        });
        Vec[] means = new DenseVector[num];
        for(int i=0; i<num; i++){
        	means[i] = DenseVector.toDenseVec(i,i);
        }
        for(int i = 0; i < means.length; i++){
            normal = new NormalM(means[i], covariance);
            int eachSample = size/num;
            for(Vec sample : normal.sample(eachSample,  new Random(1)))
                dataSet.addDataPoint(sample, new int[0], i);
        }
        FeatureFile dataFeature = new FeatureFile(filename);
		dataFeature.addAttribute("feature", 2);
		dataFeature.startData(num);
		int sampleSize = dataSet.getSampleSize();        

        for(int i = 0; i<sampleSize; i++){
            DataPoint dataPoint = dataSet.getDataPoint(i); 
            int classAcutal = dataSet.getDataPointCategory(i);
            Vec sample = dataPoint.getNumericalValues();
            double[] s = sample.arrayCopy();
            dataFeature.addFeature(s);
            dataFeature.addClass(classAcutal);
            dataFeature.addNewFeature();
        }
        dataFeature.createArffFile(dataFeature.header, dataFeature.features);
	}

	public static void runNaiveBayes(Instances data) throws Exception{
		NaiveBayes nb = new NaiveBayes(); 
		System.out.println("Bulding Naive Bayes...");
		nb.buildClassifier(data);
		System.out.println("The parameters are: ");
		System.out.println(nb.toString());
	}
	
	public static void buildArffFilePart2(int size, int num, String filename)throws Exception{
        ClassificationDataSet dataSet = new ClassificationDataSet(2, new CategoricalData[0], new CategoricalData(num)); 
        NormalM normal;
        Matrix covariance = new DenseMatrix(new double[][]
        {
            {1.0, 0.0},
            {0.0, 1.0} 
        });
        Vec[] means = new DenseVector[num];
        for(int i=0; i<num; i++){
        	means[i] = DenseVector.toDenseVec(i,i);
        }
        for(int i = 0; i < means.length; i++){
            normal = new NormalM(means[i], covariance);
            int eachClass = size/num;
            for(Vec sample : normal.sample(size/num, new Random()))
                dataSet.addDataPoint(sample, new int[0], i);
        }
        FeatureFile dataFeature = new FeatureFile(filename);
        dataFeature.addAttribute("feature", 2);
        dataFeature.startData(num);
		
        for(int i = 0; i < dataSet.getSampleSize(); i++){
            DataPoint dataPoint = dataSet.getDataPoint(i); 
            int classActual = dataSet.getDataPointCategory(i);
            Vec sample = dataPoint.getNumericalValues();
            double[] s = sample.arrayCopy();
            dataFeature.addFeature(s);
            dataFeature.addClass(classActual);
            dataFeature.addNewFeature();
        }
        
        dataFeature.createArffFile(dataFeature.header, dataFeature.features);
        
	}

	
	public static void main(String args[]) throws Exception{
//		int sample = 500;
//		int num = 4;
//		buildArffFile(sample, num, "covarianceData.arff");
//		System.out.println("PartB (1) start");
//		BufferedReader br = new BufferedReader(new FileReader("covarianceData.arff"));
//		Instances data = new Instances(br);
//		data.setClassIndex(data.numAttributes()-1);
//		runNaiveBayes(data);
		
		int sample = 500;
		int num  = 4;
		buildArffFilePart2(sample, num, "covarianceDataDiagonal.arff");
		System.out.println("PartB (2) start");
		BufferedReader br = new BufferedReader(new FileReader("covarianceDataDiagonal.arff"));
		Instances data = new Instances(br);
		data.setClassIndex(data.numAttributes()-1);
		runNaiveBayes(data);
		
		
	}
	
	*/
}
