package Parts;

import weka.classifiers.trees.SimpleCart;


public class SimpleCartEntropy extends SimpleCart {
	public double computeGini(double[] dist, double total){
	    if (total==0) return 0;
	    double val = 0;
	    for (int i=0; i<dist.length; i++) {
	      val += (dist[i]/total)*Math.log(dist[i]/total)/Math.log(2);
	    }
	    return val;
	}
}




