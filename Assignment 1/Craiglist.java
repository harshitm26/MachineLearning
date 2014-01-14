package build;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class Craiglist {		
	public static String[] parse(String temp){
		temp = temp.substring(1, temp.length()-1);
		String [] arr= temp.split(",");
		for(int i=0; i<4; i++){
			arr[i] = arr[i].split(":")[1];
//			System.out.print(arr[i] +"  ");
			if(arr[i].length() > 3){
				arr[i] = arr[i].substring(1, arr[i].length()-1);
			}
		}
//		System.out.println();
		return arr;
	}
	
	public static String correct(String words){
		words = words.toLowerCase();
		words = words.replaceAll("\\s\\s+","");
		words = words.replaceAll("^\\s","");
		words = words.replaceAll("[^\\w ]","");
		words = words.replaceAll("[0-9*/;]", "");
		return words;
	}
	
	public static String decompose(String s) {
	    return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
	}
	
	public static void generatefile() throws IOException{
		
		String[] stopwords = {"a","able","about","above","abst","accordance","according",
				"accordingly","across","act","actually","added","adj","affected","affecting",
				"affects","after","afterwards","again","against","ah","all","almost","alone",
				"along","already","also","although","always","am","among","amongst","an","and"
				,"announce","another","any","anybody","anyhow","anymore","anyone","anything",
				"anyway","anyways","anywhere","apparently","approximately","are","aren","arent"
				,"arise","around","as","aside","ask","asking","at","auth","available","away",
				"awfully","b","back","be","became","because","become","becomes","becoming","been",
				"before","beforehand","begin","beginning","beginnings","begins","behind","being",
				"believe","below","beside","besides","between","beyond","biol","both","brief",
				"briefly","but","by","c","ca","came","can","cannot","can't","cause","causes",
				"certain","certainly","co","com","come","comes","contain","containing",
				"contains","could","couldnt","d","date","did","didn't","different","do","does",
				"doesn't","doing","done","don't","down","downwards","due","during","e","each",
				"ed","edu","effect","eg","eight","eighty","either","else","elsewhere","end","ending",
				"enough","especially","et","et-al","etc","even","ever","every","everybody",
				"everyone","everything","everywhere","ex","except","f","far","few","ff","fifth"
				,"first","five","fix","followed","following","follows","for","former","formerly"
				,"forth","found","four","from","further","furthermore","g","gave","get","gets"
				,"getting","give","given","gives","giving","go","goes","gone","got","gotten",
				"h","had","happens","hardly","has","hasn't","have","haven't","having","he",
				"hed","hence","her","here","hereafter","hereby","herein","heres","hereupon",
				"hers","herself","hes","hi","hid","him","himself","his","hither","home","how"
				,"howbeit","however","hundred","i","id","ie","if","i'll","im","immediate",
				"immediately","importance","important","in","inc","indeed","index","information"
				,"instead","into","invention","inward","is","isn't","it","itd","it'll","its",
				"itself","i've","j","just","k","keep","keeps","kept","kg","km","know","known",
				"knows","l","largely","last","lately","later","latter","latterly","least","less"
				,"lest","let","lets","like","liked","likely","line","little","'ll","look","" +
						"looking","looks","ltd","m","made","mainly","make","makes","many","may"
						,"maybe","me","mean","means","meantime","meanwhile","merely","mg","might"
				,"million","miss","ml","more","moreover","most","mostly","mr","mrs","much","mug"
				,"must","my","myself","n","na","name","namely","nay","nd","near","nearly",
				"necessarily","necessary","need","needs","neither","never","nevertheless","new",
				"next","nine","ninety","no","nobody","non","none","nonetheless","noone","nor",
				"normally","nos","not","noted","nothing","now","nowhere","o","obtain","obtained"
				,"obviously","of","off","often","oh","ok","okay","old","omitted","on","once","one"
				,"ones","only","onto","or","ord","other","others","otherwise","ought","our","ours"
				,"ourselves","out","outside","over","overall","owing","own","p","page","pages","part"
				,"particular","particularly","past","per","perhaps","placed","please","plus",
				"poorly","possible","possibly","potentially","pp","predominantly","present",
				"previously","primarily","probably","promptly","proud","provides","put","q",
				"que","quickly","quite","qv","r","ran","rather","rd","re","readily","really",
				"recent","recently","ref","refs","regarding","regardless","regards","related",
				"relatively","research","respectively","resulted","resulting","results","right",
				"run","s","said","same","saw","say","saying","says","sec","section","see","seeing"
				,"seem","seemed","seeming","seems","seen","self","selves","sent","seven","several",
				"shall","she","shed","she'll","shes","should","shouldn't","show","showed","shown",
				"showns","shows","significant","significantly","similar","similarly","since","six",
				"slightly","so","some","somebody","somehow","someone","somethan","something",
				"sometime","sometimes","somewhat","somewhere","soon","sorry","specifically",
				"specified","specify","specifying","still","stop","strongly","sub",
				"substantially","successfully","such","sufficiently","suggest","sup","sure","t"
				,"take","taken","taking","tell","tends","th","than","thank","thanks","thanx","that",
				"that'll","thats","that've","the","their","theirs","them","themselves","then",
				"thence","there","thereafter","thereby","thered","therefore","therein","there'll",
				"thereof","therere","theres","thereto","thereupon","there've","these","they","theyd",
				"they'll","theyre","they've","think","this","those","thou","though","thoughh",
				"thousand","throug","through","throughout","thru","thus","til","tip","to","together",
				"too","took","toward","towards","tried","tries","truly","try","trying","ts","twice",
				"two","u","un","under","unfortunately","unless","unlike","unlikely","until","unto",
				"up","upon","ups","us","use","used","useful","usefully","usefulness","uses",
				"using","usually","v","value","various","'ve","very","via","viz","vol","vols",
				"vs","w","want","wants","was","wasn't","way","we","wed","welcome","we'll","went",
				"were","weren't","we've","what","whatever","what'll","whats","when","whence",
				"whenever","where","whereafter","whereas","whereby","wherein","wheres","whereupon",
				"wherever","whether","which","while","whim","whither","who","whod","whoever","whole",
				"who'll","whom","whomever","whos","whose","why","widely","willing","wish","with",
				"within","without","won't","words","world","would","wouldn't","www","x","y","yes",
				"yet","you","youd","you'll","your","youre","yours","yourself","yourselves","you've",
				"z","zero"};
		String [] sect = {"for-sale","housing","community","services"};
		String [] categ = {"activities","appliances","artists","automotive","cell-phones",
				"childcare","general","household-services","housing","photography",
				"real-estate","shared","temporary","therapeutic","video-games","wanted-housing"};
		
		HashMap section = new HashMap <String, Integer>();
		HashMap cities = new HashMap <String, Integer>();
		HashMap category = new HashMap <String, Integer>();
		HashMap sw = new HashMap<String, Integer>();
		HashMap hm = new HashMap<String, Integer>();
		
		for(int i=0; i<sect.length; i++){
			section.put(sect[i], section.size());
		}
		for(int i=0; i<categ.length; i++){
			category.put(categ[i], category.size());
		}
		for(int i=0;i <stopwords.length;i++){
			sw.put(stopwords[i],1);
		}
		FileInputStream fis = new FileInputStream("training.json");;
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		
		BufferedReader br = new BufferedReader(isr);
		BufferedWriter bw = new BufferedWriter(new FileWriter("Data/craiglist.arff"));
		String lines = br.readLine();
		Integer line = Integer.valueOf(lines);

		String [][] arr = new String[line][4];
		for(int i=0; i<line; i++){
			String temp = br.readLine();
			temp = decompose(temp);
//			System.out.println(temp);
			
			arr[i]= parse(temp);
			arr[i][3] = correct(arr[i][3]);
			if( !cities.containsKey(arr[i][0])){
				cities.put(arr[i][0],cities.size());
			}
			String [] words = arr[i][3].split(" ");
			for(int j=0;j<words.length;j++){
				if( (!hm.containsKey(words[j])) && (!sw.containsKey(words[j]))){
					 hm.put(words[j], hm.size());
				}
			}
		}
		Instances data = new Instances(data, 500);
		bw.write("@RELATION craiglist\n");
		bw.write("@ATTRIBUTE cityname {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}\n");
		bw.write("@ATTRIBUTE section {0,1,2,3}\n");
		
//		System.out.println("no of different words: " +hm.size());
//		System.out.println("@RELATION craiglist");
//		System.out.println("@ATTRIBUTE cityname {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}");
//		System.out.println("@ATTRIBUTE section {0,1,2,3}");
		Object[] temp = hm.keySet().toArray();
		for(int i=0; i<temp.length; i++){
			bw.write("@ATTRIBUTE c_" + temp[i] +" {0,1}\n");
//			System.out.println("@ATTRIBUTE c_" + temp[i] +" {0,1}");
		}
		bw.write("@ATTRIBUTE CLASS {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}\n");
		bw.write("@DATA\n");
		
//		System.out.println("@ATTRIBUTE CLASS {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}");
//		System.out.println("@DATA");
		String [][] finala = new String[line][hm.size()+3];
		for(int i=0; i<line; i++){
			finala[i][0] = String.valueOf(cities.get(arr[i][0]));
			finala[i][hm.size() + 2] = String.valueOf(category.get(arr[i][1]));
			finala[i][1] = String.valueOf(section.get(arr[i][2]));
			String [] words = arr[i][3].split(" ");
			for(int j=2; j<hm.size() + 2; j++){
				finala[i][j]="0";
			}
			for(int j=0; j<words.length; j++){
				if( !sw.containsKey(words[j])){
					finala[i][ ((Integer) hm.get(words[j]) + 2)] = "1";
				}
			}
		}
		for(int i=0; i<line; i++){
			for(int j=0; j<hm.size()+3; j++){
				if(j==hm.size()+2){
					bw.write(finala[i][j]);
				}
				else{
					bw.write(finala[i][j] + ",");
				}
//				System.out.print(finala[i][j] + " ");
			}
			bw.write("\n");
			System.out.println();
		}
		bw.close();
		br.close();
	}
	
	public static void main(String args[]) throws Exception{
//		generatefile();
		BufferedReader reader = new BufferedReader(new FileReader("Data/craiglist.arff"));
		Instances data = new Instances(reader);
		reader.close();
		
		data.setClassIndex(data.numAttributes() - 1);
		RemovePercentage removePercentage = new RemovePercentage();
		String[] options = new String[2];
		options[0] = "-P";
		options[1] = "10";
		removePercentage.setOptions(options);
		removePercentage.setInputFormat(data);
		Instances dataTraining = Filter.useFilter(data, removePercentage);
		dataTraining.setClassIndex(dataTraining.numAttributes() - 1);
		 
		removePercentage.setInputFormat(data);

		removePercentage.setInvertSelection(true);
		Instances dataTest = Filter.useFilter(data, removePercentage);
		dataTest.setClassIndex(dataTest.numAttributes() - 1);

        System.out.println("_________________PART A _______________");
		
		SimpleCart tree = new SimpleCart();
		try{
			tree.buildClassifier(dataTraining);			
			Evaluation eTest = new Evaluation(dataTraining);
	        eTest.evaluateModel(tree, dataTest);
	        String strSummary = eTest.toSummaryString();
	        System.out.println(strSummary);
		}
		catch(Exception c){
			c.printStackTrace();
		}
		
	}		
}
