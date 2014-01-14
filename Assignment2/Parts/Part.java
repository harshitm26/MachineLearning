package Parts;

import java.util.ArrayList;

import JSci.maths.vectors.DoubleVector;



public class Part implements Function{
	//constructor
	DoubleVector[] featureSet = new DoubleVector[1000];
	DoubleVector[] featureSetMisclassfied = new DoubleVector[1000];
	public Part(DoubleVector[] featureSet , DoubleVector[] featureSetMisclassified){
		this.featureSet = featureSet;
		this.featureSetMisclassfied = featureSetMisclassified; 
	}
	
	
	
	private ArrayList<DoubleVector> noMisclassified(DoubleVector w , DoubleVector[] features){
		ArrayList<DoubleVector> Y = new ArrayList<DoubleVector>();
		int w1 = 0,w2=0;
		for(int i=0; i<features.length; i++){
			if(i%2==0){ //w1
				if(w.scalarProduct(features[i]) > 0){
					Y.add(features[i]);
					w1++;
				}
			}
			else{//w2
				if(w.scalarProduct(features[i]) < 0){			
					Y.add((DoubleVector) features[i].negate());
					w2++;
				}
			}
		}
//		System.out.println("w1: " + w1 + " w2: " + w2);
//		System.out.println("misclassified: " + Y.size());
		return Y;
	}
	
	public DoubleVector initialize(double number, int dimensions){
		DoubleVector a = new DoubleVector(dimensions);
		for(int i=0;i<dimensions; i++){
			a.setComponent(i, number);
		}
		return a;
	}
	
	public DoubleVector randomize(int dimensions){
		DoubleVector a = new DoubleVector(dimensions);
		for(int i=0; i<dimensions; i++){
			a.setComponent(i, -1 + Math.random()*2);
		}
		return a;
	}
	
	//Batched perceptron
	public void findHyperPlane(){
		DoubleVector wIteration = new DoubleVector(51);
		int  iteration = 2;
		double rho;
		wIteration = randomize(51);
		ArrayList<DoubleVector> Y = new ArrayList<DoubleVector>();
		while((Y=noMisclassified(wIteration , featureSet)).size()!=0){
			iteration++;
			rho = 1/(double)(iteration);
			DoubleVector sum = new DoubleVector(51);
			sum = initialize(0,51);
			for(int i=0; i<Y.size(); i++){
				 sum = sum.add(Y.get(i));
			}
			if(Y.size() == 0){
				break;
			}
			else{
//				System.out.println("its: " + wIteration);
				wIteration = (DoubleVector) wIteration.subtract(sum.scalarMultiply(rho));
//				System.out.println("its: " +  wIteration);
			}
		}
		System.out.println("No. of Iterations required: " + iteration + "\n" + wIteration);
	}
	//Incremental perceptron
	public void findHyperPlaneIncremental(double a){
		DoubleVector wIteration = new DoubleVector(51);
		wIteration = randomize(51);
		int iteration = 0,w1 = 0, w2=0;
		double rho;
		while(true){
			boolean change = false;
			w1 = 0; 
			w2 = 0;
			rho = 1/(double)(iteration+a);
			for(int i=0; i<featureSet.length; i++){
				if(wIteration.scalarProduct(featureSet[i]) > 0 && i%2==0){
					wIteration = (DoubleVector) wIteration.subtract(featureSet[i].scalarMultiply(rho));
					change = true;
					w1 ++;
				}
				if(i%2==1 && wIteration.scalarProduct(featureSet[i]) < 0){
					wIteration = (DoubleVector) wIteration.add(featureSet[i].scalarMultiply(rho));
					change = true;
					w2 ++;
				}
			}
			if(!change){
				break;
			}
			iteration++;
			System.out.println("w1:  " + w1 + " w2: " + w2 );
		}
		System.out.println("iteration: " + iteration );
		System.out.println("witeration: " + wIteration);
	}
	
	public void pocketAlgo(){
		DoubleVector wIteration = new DoubleVector(51);
		DoubleVector ws = new DoubleVector(51);
		int  iteration = 2, unchanged  = 0 , convergence  = 200 , hs = 1001;
		double rho;
		wIteration = randomize(51);
		ArrayList<DoubleVector> Y = new ArrayList<DoubleVector>();
		while(true){
			Y = noMisclassified(wIteration, featureSetMisclassfied);
			iteration++;
			rho = .01/(double)(iteration);
			DoubleVector sum = new DoubleVector(51);
			sum = initialize(0,51);
			for(int i=0; i<Y.size(); i++){
				sum = sum.add(Y.get(i));
			}
			if(Y.size() == 0){
				System.out.println("Correctly classified all");
				break;
			}
			else if(unchanged > convergence){
				System.out.println("Unchanged for " + unchanged + " iterations");
				break;
			}
			else{
				wIteration = (DoubleVector) wIteration.subtract(sum.scalarMultiply(rho));
			}
			if(Y.size() < hs){
				hs = Y.size();
				ws= wIteration;
				unchanged = 0;
//				System.out.println("updated!!");
			}
			else{
				unchanged ++;
//				System.out.println(unchanged);
			}
		}
//		System.out.println("No. of Iterations required: " + iteration + "\n" + wIteration);
//		System.out.println("No. of misclassifcations: " + Y.size());
		System.out.println("hs: " + hs);
		System.out.println("W is: \n" + ws);
	}
	
	public void widrowHoff(DoubleVector[] dataSet){
		DoubleVector wIteration = new DoubleVector(51);
		int  iteration = 1;
		double rho;
		int noIterations = 300;
		wIteration = randomize(51);
		ArrayList<DoubleVector> Y = new ArrayList<DoubleVector>();
		while(true){
			iteration++;
			rho = 1/(double)(iteration+200);
//			rho = .01;
//			DoubleVector sum = new DoubleVector(51);
//			sum = initialize(0,51);
//			for(int i=0; i<Y.size(); i++){
//				 sum = sum.add(Y.get(i));
//			}	
//			System.out.println("No of misclassifications: " +  noMisclassified(wIteration, dataSet).size());
			if((iteration+200+1) > noIterations ||  (noMisclassified(wIteration, dataSet).size()==0)){
				break;
			}
			else{
				for(int i=0; i<dataSet.length; i++){
	//				System.out.println("its: " + wIteration);
					double error;
					if(i%2 == 0){	
						error = (-1-dataSet[i].scalarProduct(wIteration));
					}
					else{
						error = (1-dataSet[i].scalarProduct(wIteration));
					}
					wIteration = (DoubleVector) wIteration.add (featureSet[i].scalarMultiply((rho*error)));
//					wIteration = (DoubleVector) wIteration.normalize();
//						System.out.println("its: " +  wIteration);
				}
			}
		}
		System.out.println("No. of Iterations required: " + iteration + "\n" + wIteration);
		System.out.println("No of misclassifications: " +  noMisclassified(wIteration, dataSet).size());
	}



	public double rho(int iteration) {
		
		// TODO Auto-generated method stub
		return 0;
	}
	
}
