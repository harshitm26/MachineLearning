import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FeatureFile {
	String header;
	String features;
	String filename;
	BufferedWriter bw;
	
	public FeatureFile(String filename) throws IOException{
		this.filename = filename;
		this.header = "@RELATION ImageFeatures\n";
		this.features = "";
		bw = new BufferedWriter(new FileWriter(filename));
	}
	
	public void addAttribute(String attributeName , int numberOfFeatures){
		for(int i=0; i<numberOfFeatures; i++){
			this.header += "@ATTRIBUTE " + attributeName + "_" + i +  " REAL\n";
		}
	}
	
	
	public void addFeature(double ... feature){
		for(double number: feature){
			features += number + ",";
		}
	}
	
	public void startData(int noClass) throws IOException{
		bw.write(header);
		String classAttribute = "@ATTRIBUTE CLASS{ ";
		for(int i=0; i<noClass; i++){
			if(i == noClass-1){
				classAttribute += (i+ "}\n");
				break;
			}
			classAttribute += (i+",");
		}
		bw.write(classAttribute);
		bw.write("\n@DATA\n");
	}
	
	public void addFeature(double[] ...feature){
		for(double[] numbers: feature){
			for(int i=0; i<numbers.length; i++){
				features += numbers[i] + ",";
			}
		}
	}
	public void addClass(int classname){
		features +=classname;
	}
	
	public void addNewFeature() throws IOException{
		features += "\n";
		bw.write(features);
		features = "";
		
	}
	
	public String getFeatures(){
		return features;
	}
	
	public String getHeader(){
		return header;
	}
	
	public void createArffFile(String header, String data) throws IOException{
		System.out.println("File Writing Completed " + filename);
		bw.close();
	}
}
