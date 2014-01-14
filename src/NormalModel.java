//package models;
import Jama.Matrix;

public class NormalModel implements IModel {
	public Matrix ﬁrstDerivative(Matrix x, Matrix y, Matrix parameters) {
		// TODO Auto-generated method stub
		int n = x.getRowDimension();
		int k = x.getColumnDimension();
		Matrix beta = new Matrix(k, 1);
		for(int i = 0; i < k; i++) {
			beta.set(i, 0, parameters.get(i, 0));
		}
		
		double sigma2 = parameters.get(k, 0);
		Matrix xBeta = x.times(beta);
		Matrix yMinusXBeta = y.minus(xBeta);
		double sigma4 = sigma2 * sigma2;
		Matrix xte = x.transpose().times(yMinusXBeta);
		Matrix ete = yMinusXBeta.transpose().times(yMinusXBeta);
		Matrix firstDeriv = new Matrix(parameters.getRowDimension(), 1);
		for(int i = 0; i < k; i++) {
			firstDeriv.set(i, 0, xte.get(i, 0) / sigma2);
		}
		firstDeriv.set(k, 0, ete.get(0, 0) / (2.0*sigma4) - n / (2.0 * sigma2));
		return firstDeriv;
	}
	
	public Matrix secondDerivative(Matrix x, Matrix y, Matrix parameters){
		int n = x.getRowDimension();
		int k = x.getColumnDimension();
		Matrix beta = new Matrix(k, 1);
		for(int i = 0; i < k; i++) {
			beta.set(i, 0, parameters.get(i, 0));
		}
		double sigma2 = parameters.get(k, 0);
		Matrix xBeta = x.times(beta);
		Matrix yMinusXBeta = y.minus(xBeta);
		Matrix xtx = x.transpose().times(x);
		Matrix ete = yMinusXBeta.transpose().times(yMinusXBeta);
		Matrix xte = x.transpose().times(yMinusXBeta);
		double sigma4 = sigma2 * sigma2;
		double sigma6 = sigma2 * sigma2 * sigma2;
		Matrix secondDeriv = new Matrix(parameters.getRowDimension(),
		parameters.getRowDimension());
		for(int i = 0; i < k; i++) {
			for(int j = 0; j < k; j++) {
				secondDeriv.set(i, j, -xtx.get(i, j) / sigma2);
			}
		}
		for(int i = 0; i < k; i++) {
			secondDeriv.set(i, k, -xte.get(i, 0) / sigma4);
			secondDeriv.set(k, i, -xte.get(i, 0) / sigma4);
		}
		secondDeriv.set(k, k, n / (2.0 * sigma4) - ete.get(0, 0) / sigma6);
		return secondDeriv;
	}



}
