import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import JSci.maths.vectors.DoubleVector;
import Parts.Part;




public class Assignment2 {
	private static DoubleVector[] generateData() throws IOException{
		DoubleVector[] featureSet = new DoubleVector[1000];
		double[] aArray = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
		int m = aArray.length;
		for(int i=0; i<1000; i++){
			DoubleVector array = new DoubleVector(51);
			for(int j=0; j<51; j++){
				if(j < m){
					if(i%2==0){
						array.setComponent(j, (-1)*(aArray[j] + Math.random()*(aArray[j])));
					}
					else{
						array.setComponent(j, aArray[j] + Math.random()*(aArray[j]));
					}
				}
				else if(j == 50){
					array.setComponent(j, 1.0);
				}
				else{
					array.setComponent(j, (-1 + Math.random()*2));
				}
			}
			featureSet[i] = array;
		}
		return featureSet;
	}
	private static DoubleVector[] generateNonSeprableData(DoubleVector[] featureSetOriginal1 , int number) throws IOException{
		DoubleVector[] featureSetOriginal = new DoubleVector[1000];
		featureSetOriginal = featureSetOriginal1.clone();
		double[] aArray = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
		int m = aArray.length;
		for(int i=featureSetOriginal.length - number; i<featureSetOriginal.length; i++){
			DoubleVector array = new DoubleVector(51);
			for(int j=0; j<51; j++){
				if(j < m){
					if(i%2==1){
						array.setComponent(j, (-1)*(aArray[j] + Math.random()*(aArray[j])));
					}
					else{
						array.setComponent(j, aArray[j] + Math.random()*(aArray[j]));
					}
				}
				else if(j == 50){
					array.setComponent(j, 1.0);
				}
				else{
					array.setComponent(j, (-1 + Math.random()*2));
				}
			}
			featureSetOriginal[i] = array;
		}
		return featureSetOriginal;
	}
	
	private static void printFeatures(DoubleVector[] featureSet) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));
		for(int j=0; j<featureSet.length; j++){
			for(int i=0; i<51; i++){
				System.out.print(featureSet[j].getComponent(i) + " ");
				bw.write(featureSet[j].getComponent(i) + ",");
			}
			System.out.println();
			bw.write("\n");
		}
		bw.close();
	}
	
	public static void main(String args[]) throws IOException{
		int nonSeperableSamples = 40;
		DoubleVector[] featureSet = new DoubleVector[1000];
		featureSet = generateData();
		DoubleVector[] featureSetMisclassified = new DoubleVector[1000];
		featureSetMisclassified = generateNonSeprableData(featureSet, nonSeperableSamples);
//		printFeatures(featureSet1);
		
		Part start = new Part(featureSet,featureSetMisclassified);
//		start.findHyperPlane();
//		start.findHyperPlaneIncremental(2);
//		start.findHyperPlaneIncremental(3);
//		start.pocketAlgo();
//		start.widrowHoff(featureSet);
//		start.widrowHoff(featureSetMisclassified);
	}
}
