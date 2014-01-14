import Jama.Matrix;

public interface IModel{
	public Matrix ﬁrstDerivative(Matrix x, Matrix y, Matrix parameters);
	public Matrix secondDerivative(Matrix x, Matrix y, Matrix parameters);
}