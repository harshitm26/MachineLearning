import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.unsupervised.attribute.Normalize;
import mnist.tools.MnistManager;


public class PartA {
	public static double[] linearize(int[][] matrix){
		int height = matrix.length; 
		int width = matrix[0].length;
		double[] array = new double[height*width];
		int count=0;
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				array[count++] = matrix[i][j];
			}
		}
		return array;
	}
	
	public static void buildArffFile(String filename, String imageFile, String labelFile, int dataNumber) throws IOException{
		FeatureFile imageFeature = new FeatureFile(filename);
		imageFeature.addAttribute("pixel", 28*28);
		imageFeature.startData(10);
		MnistManager m = new MnistManager(imageFile, labelFile);
		long startTime = System.currentTimeMillis();
		for(int i=1; i<= dataNumber; i++){
			if(i%100 == 0){
				System.out.println("Done for " + i + "images in time: " + (System.currentTimeMillis() - startTime)/1000);
			}
			m.setCurrent(i);
			int[][] image = m.readImage();
			for(int j=0; j<image.length; j++){
				for(int k=0; k<image[0].length; k++){
				}
			}
			imageFeature.addFeature(linearize(image));
			imageFeature.addClass(m.readLabel());
			imageFeature.addNewFeature();
		}
	}
	
	public static Instances getNormalizedData(Instances data) throws Exception{
		Normalize norm=new Normalize();
        norm.setInputFormat(data);
        Instances dataNormalized=Normalize.useFilter(data, norm);
        return dataNormalized;
	}
	
	private  static void runNeuralNetwork(Instances dataTrain, Instances dataTest) throws Exception{
//		Neural Network
	  System.out.println("NN started...");
      MultilayerPerceptron mlp = new MultilayerPerceptron();
      String[] options = weka.core.Utils.splitOptions("-H "+ 25 + " -V 20");
      mlp.setOptions(options);
      mlp.setHiddenLayers("20,20");
      mlp.buildClassifier(dataTrain);
      System.out.println("NN trained... Now CrossValidating data...");
      Evaluation eTest = new Evaluation (dataTrain);
      eTest.evaluateModel(mlp, dataTest);
      eTest.crossValidateModel(mlp,  dataTest,  10,  new Random(1));
      System.out.println("NN:\n" + eTest.toSummaryString());
      System.out.println("NN ended");
	}
	
	private static void runSVM(Instances dataTrain, Instances dataTest) throws Exception{
//      SVM
	  System.out.println("SVM started...");
	  RBFKernel kernel = new RBFKernel();	
	  String[] options = weka.core.Utils.splitOptions("-G "+ 1);
	  kernel.setOptions(options);
	  SMO svm = new SMO();
	  options = weka.core.Utils.splitOptions("-N 0 -V 5 -C 2");
	  svm.setOptions(options);
	  svm.setKernel(kernel);
	  svm.buildClassifier(dataTrain);
	  System.out.println("SVM build... Now Cross Validating data...");
	  Evaluation eTest = new Evaluation (dataTrain);
	  eTest.crossValidateModel(svm,  dataTest,  10,  new Random(1));
	  System.out.println("SVM:\n" + eTest.toSummaryString());
	  System.out.println("SVM ended.");
	}
	
	private static void runKNN(Instances dataTrain, Instances dataTest) throws Exception{
//      K-NN
	  System.out.println("K-NN started...");
      IBk knn = new IBk(1);
//      String[] options = weka.core.Utils.splitOptions("-G "+ 1);
//      knn.setOptions(options);
      knn.buildClassifier(dataTrain);
      System.out.println("K-NN build... Now cross Validating... ");
      Evaluation eTest = new Evaluation(dataTrain);
      eTest.crossValidateModel(knn,  dataTest,  10,  new Random(1));
      System.out.println("knn:\n" + eTest.toSummaryString());
      System.out.println("K-NN ended.");
	}
	
	public static void main(String args[]) throws Exception{
//		buildArffFile("imageFeature.arff","Data//t10k-images.idx3-ubyte", "Data//t10k-labels.idx1-ubyte",10000);
//		buildArffFile("imageFeatureTrain.arff","Data//train-images.idx3-ubyte","Data//train-labels.idx1-ubyte",60000);
		System.out.println("PartA start");
		BufferedReader br = new BufferedReader(new FileReader("imageFeature.arff"));
		Instances dataTest = new Instances(br);
		br = new BufferedReader(new FileReader("imageFeatureTrain.arff"));
		Instances dataTrain = new Instances(br);
		dataTrain = getNormalizedData(dataTrain);
		dataTest = getNormalizedData(dataTest);
		/* Partly used*/
//		dataTrain = new Instances(dataTrain,0,10000);
//		dataTest = new Instances(dataTest,0,2000);
		
		dataTrain.setClassIndex(dataTrain.numAttributes()-1);
		dataTest.setClassIndex(dataTest.numAttributes() - 1);
		System.out.println("Data Processing Done");
//		runNeuralNetwork(dataTrain, dataTest);
		runSVM(dataTrain, dataTest);
//		runKNN(dataTrain, dataTest);
	}
}
