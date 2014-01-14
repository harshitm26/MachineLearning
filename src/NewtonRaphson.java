import Jama.Matrix;

public class NewtonRaphson{
IModel model;
	public NewtonRaphson(IModel model){
		this.model = model;
	}
	public Matrix run(Matrix x, Matrix y, Matrix startValues){
		int maxNum = 100;
		double eps = 0.01;
		int counter = 0;
		Matrix parameters = startValues.copy();
		do {
			Matrix H = model.secondDerivative(x, y, parameters);
			if(Math.abs(H.det()) < eps) {
				System.out.println("Hessian is singular!");
				H.print(2,2);
				counter = -1;
				break;
			}
			Matrix Hi = H.inverse();
			Matrix fd = model.ﬁrstDerivative(x, y, parameters);
			parameters = parameters.minus(Hi.times(fd));
			counter++;
		}
		while(counter < maxNum);
		System.out.println("counter: "+counter);
		return parameters;
	}
}