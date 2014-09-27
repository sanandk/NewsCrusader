/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.index.FileUtilities.DictType;

/**
 * @author nikhillo Class responsible for writing indexes to disk
 */
public class IndexWriter {
	
	String outputDir;
	
	/**
	 * Default constructor
	 * 
	 * @param indexDir
	 *            : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {
		// TODO : YOU MUST IMPLEMENT THIS
		outputDir=indexDir;
		File theDir = new File(outputDir);
		 
		  // if the directory does not exist, create it
		  if (!theDir.exists())
		  {
		    System.out.println("creating directory: " + outputDir);
		    theDir.mkdir();
		  }
		  FileUtilities.setOutputDir(indexDir);
		  
	}

	/**
	 * Method to add the given Document to the index This method should take
	 * care of reading the filed values, passing them through corresponding
	 * analyzers and then indexing the results for each indexable field within
	 * the document.
	 * 
	 * @param d
	 *            : The Document to be added
	 * @throws IndexerException
	 *             : In case any error occurs
	 */
	static int i = 1;
	static List<String> a = new ArrayList<String>();
	static List<String> b = new ArrayList<String>();
//	public static int tokenSize =0;

	public static TreeMap<String,LinkedList<String>> docList= new TreeMap<String,LinkedList<String>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexAC = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexDG = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexHK = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexLP = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexQS = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexTZ = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexMisc = new TreeMap<String, HashMap<Integer, Integer>>();
	
	public void addDocument(Document d) throws IndexerException {
		// TODO : YOU MUST IMPLEMENT THIS
		// Updated by anand on Sep 14
		
//		FileUtilities.writeToDic(d.getField(FieldNames.CATEGORY)[0]+File.separator+d.getField(FieldNames.FILEID)[0]  , DictType.DOC);
		
		TokenStream t_stream = null;
		Tokenizer t=new Tokenizer();
		AnalyzerFactory fact = AnalyzerFactory.getInstance();
		try {
			if(d.getField(FieldNames.CONTENT)!=null){
			t_stream=t.consume(d.getField(FieldNames.CONTENT)[0]);
			Analyzer analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
			
	//		t_stream=t.consume(d.getField(FieldNames.CONTENT)[0]);
	//		Analyzer analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
			
			while (analyzer.increment()) {
				
			}
//			tokenSize+=t_stream.my_stream.size();
//			System.out.println("FileID:"+d.getField(FieldNames.FILEID)[0]+" Tokens:"+t_stream.my_stream.size()+" total:"+tokenSize);
			t_stream.reset();
//			System.out.println("Content is "+d.getField(FieldNames.CONTENT)[0]);
//			System.out.print("Content is ");
			Token tp;
			HashMap<Integer,Integer> termPosting ;
			Integer frequency=0;
			while(t_stream.hasNext())
			{
				tp=t_stream.next();
				termPosting= new HashMap<Integer,Integer>();
				String termValue;
				if(tp!=null && (termValue=tp.toString()).length()>0){
					
					//A-C D-G H-K L-P Q-S T-Z
					switch(Character.toLowerCase(termValue.charAt(0))){
						case 'a': case 'b': case 'c':
							if(termIndexAC.containsKey(termValue)){
								termPosting=termIndexAC.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexAC.put(termValue,termPosting );
							break;
						case 'd': case 'e': case 'f': case 'g':
							if(termIndexDG.containsKey(termValue)){
								termPosting=termIndexDG.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexDG.put(termValue,termPosting );
							break;
						case 'h': case 'i': case 'j': case 'k':
							if(termIndexHK.containsKey(termValue)){
								termPosting=termIndexHK.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexHK.put(termValue,termPosting );
							break;
						case 'l': case 'm': case 'n': case 'o': case 'p':
							if(termIndexLP.containsKey(termValue)){
								termPosting=termIndexLP.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexLP.put(termValue,termPosting );
							break;
						case 'q': case 'r': case 's':
							if(termIndexQS.containsKey(termValue)){
								termPosting=termIndexQS.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexQS.put(termValue,termPosting );
							break;
						case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
							if(termIndexTZ.containsKey(termValue)){
								termPosting=termIndexTZ.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexTZ.put(termValue,termPosting );
							break;
						default :
							if(termIndexMisc.containsKey(termValue)){
//								termPosting=termIndexMisc.get(termValue);
//								
//							}
//							termPosting.append(","+FileUtilities.docId);
//							termIndexMisc.put(termValue,termPosting );
								termPosting=termIndexMisc.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							termIndexMisc.put(termValue,termPosting );
					}
					
					
					
				}
//				System.out.print(tp.toString()+"|");
			}
			}
//			System.out.print(termIndex.size());
		}
		catch (TokenizerException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception!");
			e.printStackTrace();
		}
		
//		while (t_stream.hasNext()) {
//            System.out.println("Item is: " + t_stream.next());
//        }

	
		// Code added by Karthik-J on Sept 9, 2014 - Starts
//		printDoc(d);
//		System.out.println("File Parsed " + i++ + "==> "
//				+ d.getField(FieldNames.CATEGORY)[0] + File.separator
//				+ d.getField(FieldNames.FILEID)[0]);
//		System.out.println(d.getField(FieldNames.TITLE)[0]);
//		// System.out.println(d.getField(FieldNames.AUTHOR)[0]);
//		// System.out.println(d.getField(FieldNames.AUTHORORG)[0]);
//		if (d.getField(FieldNames.PLACE) != null) {
//			System.out.println(d.getField(FieldNames.PLACE)[0]);
//			if (d.getField(FieldNames.NEWSDATE) != null) {
//				System.out.println(d.getField(FieldNames.NEWSDATE)[0]);
//			} else {
//				b.add(d.getField(FieldNames.CATEGORY)[0] + File.separator
//						+ d.getField(FieldNames.FILEID)[0]);
//			}
//		} else {
//			a.add(d.getField(FieldNames.CATEGORY)[0] + File.separator
//					+ d.getField(FieldNames.FILEID)[0]);
//		}
//		System.out.println(d.getField(FieldNames.CONTENT)[0]);
//		System.out
//				.println("==============================================================================================================");
		// Code added by Karthik-J on Sept 9, 2014 - Ends
	}

	public void printDoc(Document d) {
		try {
			File DocumentContent = new File(
					"D:\\UB\\Project\\IR\\project dataset\\DocOutput.txt");
			FileWriter docFW = new FileWriter(DocumentContent,true);
			BufferedWriter docBW = new BufferedWriter(docFW);
			
			docBW.write("File Parsed " + i++ + "==> "
					+ d.getField(FieldNames.CATEGORY)[0] + File.separator
					+ d.getField(FieldNames.FILEID)[0]);
			docBW.write("\nTitle==>"+d.getField(FieldNames.TITLE)[0]);
			if(d.getField(FieldNames.AUTHOR) != null){
				docBW.write("\nAuthor==>"+d.getField(FieldNames.AUTHOR)[0]);
				if(d.getField(FieldNames.AUTHORORG) != null){
					docBW.write("\nAuthorOrg==>"+d.getField(FieldNames.AUTHORORG)[0]);
				}	
			}
			if (d.getField(FieldNames.PLACE) != null) {
				docBW.write("\nPlace==>"+d.getField(FieldNames.PLACE)[0]);
				if (d.getField(FieldNames.NEWSDATE) != null) {
					docBW.write("\nNewsDate==>"+d.getField(FieldNames.NEWSDATE)[0]);
				} else {
					b.add(d.getField(FieldNames.CATEGORY)[0] + File.separator
							+ d.getField(FieldNames.FILEID)[0]);
				}
			} else {
				a.add(d.getField(FieldNames.CATEGORY)[0] + File.separator
						+ d.getField(FieldNames.FILEID)[0]);
			}
			docBW.write("\nContent==>"+d.getField(FieldNames.CONTENT)[0]);
			docBW.write("\n==============================================================================================================\n");
			docBW.close();
		} catch (Exception e) {

		}
	}

	public void printA() {
		for (String d : a) {
			System.out.println(d);
		}
	}

	public void printB() {
		for (String d : b) {
			System.out.println(d);
		}

	}

	/**
	 * Method that indicates that all open resources must be closed and cleaned
	 * and that the entire indexing operation has been completed.
	 * 
	 * @throws IndexerException
	 *             : In case any error occurs
	 */
	public void close() throws IndexerException {
		// TODO
		FileUtilities.writeToDic(docList, DictType.DOC);
		FileUtilities.writeIndexFile(termIndexAC,FileUtilities.indexAC);
		FileUtilities.writeIndexFile(termIndexDG,FileUtilities.indexDG);
		FileUtilities.writeIndexFile(termIndexHK,FileUtilities.indexHK);
		FileUtilities.writeIndexFile(termIndexLP,FileUtilities.indexLP);
		FileUtilities.writeIndexFile(termIndexQS,FileUtilities.indexQS);
		FileUtilities.writeIndexFile(termIndexTZ,FileUtilities.indexTZ);
		FileUtilities.writeIndexFile(termIndexMisc,FileUtilities.indexMisc);
		FileUtilities.closeDict();
	}
	
	public void read() {
		FileUtilities.readIndexFile(FileUtilities.indexAC);
		FileUtilities.readIndexFile(FileUtilities.indexDG);
		FileUtilities.readIndexFile(FileUtilities.indexHK);
		FileUtilities.readIndexFile(FileUtilities.indexLP);
		FileUtilities.readIndexFile(FileUtilities.indexQS);
		FileUtilities.readIndexFile(FileUtilities.indexTZ);
		FileUtilities.readIndexFile(FileUtilities.indexMisc);
	}
}
