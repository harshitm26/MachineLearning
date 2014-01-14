	package build;
	import java.io.*;
	import java.util.*;
	import org.json.simple.JSONObject;
	import org.json.simple.JSONValue;
	import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;
	
	public class test {	
		
		static HashMap<String, Integer> hm = new HashMap<String, Integer>();
		static HashMap<String, Integer> category = new HashMap <String, Integer>();
		
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
		
		public static Instances generateArff() throws IOException{
			BufferedReader br = new BufferedReader(new FileReader("training.json"));
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
			HashMap<String, Integer> section = new HashMap <String, Integer>();
			HashMap<String, Integer> cities = new HashMap <String, Integer>();
			HashMap<String, Integer> sw = new HashMap<String, Integer>();
			for(int i=0; i<sect.length; i++){
				section.put(sect[i], section.size());
			}
			for(int i=0; i<categ.length; i++){
				category.put(categ[i], category.size());
			}
			for(int i=0;i <stopwords.length;i++){
				sw.put(stopwords[i],1);
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter("craiglist_harshit.arff"));
			String totallines = br.readLine();
			int line = Integer.valueOf(totallines);
			int range = line/500;
	//		System.out.println("range: " + range);
			
			String [][] arr = new String[line][4];
			for(int i=0; i<line; i++){
				String temp = br.readLine();
				if(i%range != 0){
	//				System.out.println("i: " + i + " range: " + range + " mod: " + i%range);
					continue;
				}
	//			System.out.println(temp);
				temp = decompose(temp);
	//			System.out.println(temp);
				Object obj = JSONValue.parse(temp);
				JSONObject object = (JSONObject)obj;
				arr[i][0]= (String) object.get("city");
				arr[i][1]= (String) object.get("category");
				
	//			System.out.println("category: " + arr[i][1] + " i: " + i);
				
				arr[i][2]= (String) object.get("section");
				arr[i][3]= (String) object.get("heading");
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
			bw.write("@RELATION craiglist\n");
			bw.write("@ATTRIBUTE cityname {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}\n");
			bw.write("@ATTRIBUTE section {0,1,2,3}\n");
			Object[] temp = hm.keySet().toArray();
			for(int i=0; i<temp.length; i++){
				bw.write("@ATTRIBUTE c_" + temp[i] +" {0,1}\n");
			}
			bw.write("@ATTRIBUTE CLASS {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}\n");
			bw.write("@DATA\n");
			String [][] finala = new String[line][hm.size()+3];
			for(int i=0; i<line; i++){
				if(i%range != 0){
	//				System.out.println("i: " + i + " range: " + range + " mod: " + i%range);
					continue;
				}
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
				if(i%range != 0){
	//				System.out.println("i: " + i + " range: " + range + " mod: " + i%range);
					continue;
				}
				for(int j=0; j<hm.size()+3; j++){
					if(j==hm.size()+2){
						bw.write(finala[i][j]);
					}
					else{
						bw.write(finala[i][j] + ",");
					}
				}
				bw.write("\n");
	//			System.out.println();
			}
			bw.close();
			br.close();
			br = new BufferedReader(new FileReader("craiglist_harshit.arff"));
			Instances data = new Instances(br);
			return data;
		}
		
		public static Instances generateTestArff() throws IOException{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
			HashMap<String, Integer> section = new HashMap <String, Integer>();
			HashMap<String, Integer> cities = new HashMap <String, Integer>();
			HashMap<String, Integer> category = new HashMap <String, Integer>();
			HashMap<String, Integer> sw = new HashMap<String, Integer>();
	//		HashMap hm = new HashMap<String, Integer>();
			for(int i=0; i<sect.length; i++){
				section.put(sect[i], section.size());
			}
			for(int i=0; i<categ.length; i++){
				category.put(categ[i], category.size());
			}
			for(int i=0;i <stopwords.length;i++){
				sw.put(stopwords[i],1);
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter("craiglist_harshit_test.arff"));
			String lines = br.readLine();
			Integer line = Integer.valueOf(lines);
	
			String [][] arr = new String[line][4];
			for(int i=0; i<line; i++){
				String temp = br.readLine();
				temp = decompose(temp);
	//			System.out.println(temp);
				Object obj = JSONValue.parse(temp);
				JSONObject object = (JSONObject)obj;
				arr[i][0]= (String) object.get("city");
				arr[i][1]= (String) object.get("category");
				arr[i][2]= (String) object.get("section");
				arr[i][3]= (String) object.get("heading");
				arr[i][3] = correct(arr[i][3]);
				if( !cities.containsKey(arr[i][0])){
					cities.put(arr[i][0],cities.size());
				}
				String [] words = arr[i][3].split(" ");
	//			for(int j=0;j<words.length;j++){
	//				if( (!hm.containsKey(words[j])) && (!sw.containsKey(words[j]))){
	//					 hm.put(words[j], hm.size());
	//				}
	//			}
			}
			bw.write("@RELATION craiglist\n");
			bw.write("@ATTRIBUTE cityname {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}\n");
			bw.write("@ATTRIBUTE section {0,1,2,3}\n");
			Object[] temp = hm.keySet().toArray();
			for(int i=0; i<temp.length; i++){
				bw.write("@ATTRIBUTE c_" + temp[i] +" {0,1}\n");
			}
			bw.write("@ATTRIBUTE CLASS {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}\n");
			bw.write("@DATA\n");
			String [][] finala = new String[line][hm.size()+3];
			for(int i=0; i<line; i++){
				finala[i][0] = String.valueOf(cities.get(arr[i][0]));
				finala[i][hm.size() + 2] = "?";//String.valueOf(category.get(arr[i][1]));
				finala[i][1] = String.valueOf(section.get(arr[i][2]));
				String [] words = arr[i][3].split(" ");
				for(int j=2; j<hm.size() + 2; j++){
					finala[i][j]="0";
				}
				for(int j=0; j<words.length; j++){
					if( !sw.containsKey(words[j])){
						if(hm.get(words[j]) != null){
							finala[i][ ((Integer) hm.get(words[j]) + 2)] = "1";
						}
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
				}
				bw.write("\n");
	//			System.out.println();
			}
			bw.close();
			br.close();
			br = new BufferedReader(new FileReader("craiglist_harshit_test.arff"));
			Instances data = new Instances(br);
			return data;
		}
		/**
		 * @param args
		 * @throws Exception
		 */
		
		private static String getKey(double value){
		    for(Object s :category.keySet()){
		        if(category.get(s).equals((int)value)) return (String)s;
		    }
		    return null;
		}
		
		public static void main(String[] args) throws Exception {
	        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
	        Instances data = generateArff();
	        Instances dataTest = generateTestArff();
	
	        data.setClassIndex(data.numAttributes() - 1);
	        dataTest.setClassIndex(dataTest.numAttributes() - 1);
	        
	        
	        String[] options1 = weka.core.Utils.splitOptions("-I "+ 500);
//			RandomForest tree = new RandomForest();
			SimpleCart tree = new SimpleCart();
//			tree.setOptions(options1);
	        tree.buildClassifier(data);			
	        for(int i=0; i<dataTest.numInstances(); i++){
	        	hm.keySet();
	//        	BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
	//        	log.write(getKey(tree.classifyInstance(dataTest.instance(i))) + "\n");
	//        	log.flush();
//	        	System.out.println("cell-phones");
	        	System.out.println(getKey(tree.classifyInstance(dataTest.instance(i))));
	        	System.out.flush();
	        }
	    }
	}
	
