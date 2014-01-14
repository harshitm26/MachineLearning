package build;
import java.io.*;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.unsupervised.instance.RemovePercentage;
import Parts.*;


public class Arrhythimia {
	public static void main(String args[]){
		try{
			BufferedReader reader = new BufferedReader(new FileReader("Data/arrhythmia.arff"));
			Instances data = new Instances(reader);
			reader.close();	

			int noTrees = 37;
			PartA partA = new PartA(data);
			partA.execute();

			double setDeltaError = 0.1;
			PartB partB = new PartB(data,setDeltaError);
			noTrees = partB.execute();
			
			noTrees = 74;
			PartC partC = new PartC(data, noTrees);
			partC.execute();
			
			PartD partD = new PartD(data);
			partD.execute();
			
			PartE partE = new PartE(data, noTrees);
			partE.executeRandomize();
			partE.execute();
			
			PartF partF = new PartF(data, noTrees);
			partF.execute();
			
			PartG partG = new PartG(data);
			partG.execute();
			
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
