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
//import edu.buffalo.cse.irf14.index.FileUtilities.DictType;

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

	public static TreeMap<String,Integer> docList= new TreeMap<String,Integer>();
//	public static TreeMap<Integer,LinkedList<String>> docCatList= new TreeMap<Integer,LinkedList<String>>();
	public static TreeMap<String, ArrayList<Integer>> CatIndex = new TreeMap<String, ArrayList<Integer>>();
	public static TreeMap<String, ArrayList<Integer>> PlaceIndex = new TreeMap<String, ArrayList<Integer>>();
	public static TreeMap<String, ArrayList<Integer>> AuthorIndex = new TreeMap<String, ArrayList<Integer>>();
	public static TreeMap<Integer,String> docCatList= new TreeMap<Integer,String>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexAC = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexDG = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexHK = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexLP = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexQS = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexTZ = new TreeMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<String, HashMap<Integer, Integer>> termIndexMisc = new TreeMap<String, HashMap<Integer, Integer>>();
	
	Analyzer analyzer;
	Token tp;
	TokenStream t_stream = null;
	Tokenizer t=new Tokenizer();
	final AnalyzerFactory fact = AnalyzerFactory.getInstance();
	String[] tit,cont,cat,place,author,authororg;
	public void addDocument(Document d) throws IndexerException {
		// TODO : YOU MUST IMPLEMENT THIS
		// Updated by anand on Sep 14
		
        tit = d.getField(FieldNames.TITLE);
		cont=d.getField(FieldNames.CONTENT);
		cat=d.getField(FieldNames.CATEGORY);
		place=d.getField(FieldNames.PLACE);
		author=d.getField(FieldNames.AUTHOR);
		authororg=d.getField(FieldNames.AUTHORORG);
		try {
//			catList=new LinkedList<String>();
//			catList.add(d.getField(FieldNames.FILEID)[0]);
//			catList.add(dirList[dirLen - 2]+File.separator+dirList[dirLen - 1]);
//			catList.add(dirList[dirLen - 1]);
//			catList.add(dirList[dirLen - 2]);
			IndexWriter.docList.put(d.getField(FieldNames.FILEID)[0],++FileUtilities.docId);
			IndexWriter.docCatList.put(FileUtilities.docId,d.getField(FieldNames.FILEID)[0]);

			
			if(cont!=null || tit!=null){	
				if(tit!=null){
						t_stream=t.consume(tit[0]);
						t_stream.append(t.consume(cont[0]));
				}
				else
					t_stream=t.consume(cont[0]);
				analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
			
	//		t_stream=t.consume(d.getField(FieldNames.CONTENT)[0]);
	//		Analyzer analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
			
			while (analyzer.increment()) {
				
			}
//			tokenSize+=t_stream.my_stream.size();
//			System.out.println("FileID:"+d.getField(FieldNames.FILEID)[0]+" Tokens:"+t_stream.my_stream.size()+" total:"+tokenSize);
			t_stream.reset();
//			System.out.println("Content is "+d.getField(FieldNames.CONTENT)[0]);
//			System.out.print("Content is ");
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
            if(place!=null && place[0].length()>1){
				t_stream=t.consume(place[0]);

				analyzer = fact.getAnalyzerForField(FieldNames.PLACE, t_stream);
				while(analyzer.increment())
				{
					
				}
				
				StringBuilder sb=new StringBuilder();
				while(t_stream.hasNext())
					sb.append(t_stream.next()).toString();
				String s=sb.toString();
				ArrayList<Integer> ll=PlaceIndex.get(s);
				if(ll==null)
					ll=new ArrayList<Integer>();
				ll.add(FileUtilities.docId);
				PlaceIndex.put(s, ll);
			}
			if(cat!=null){
				ArrayList<Integer> ll=CatIndex.get(cat[0]);
				if(ll==null)
					ll=new ArrayList<Integer>();
				ll.add(FileUtilities.docId);
				CatIndex.put(cat[0], ll);
			
				}
			
			if(author!=null && author[0].length()>1){
				String a[] = author[0].split(" and ");
				StringBuilder sb=new StringBuilder();
				for(String auth:a)
				{
					t_stream=t.consume(auth);
					analyzer = fact.getAnalyzerForField(FieldNames.AUTHOR, t_stream);
					while(analyzer.increment())
					{
					}
					
					while(t_stream.hasNext())
						sb.append(t_stream.next()).toString();
					
					if(authororg!=null &&  authororg[0].length()>1)
					{
						t_stream=t.consume(authororg[0]);
						analyzer = fact.getAnalyzerForField(FieldNames.AUTHORORG, t_stream);
						while(analyzer.increment())
						{
						}
						int i=0;
						while(t_stream.hasNext())
						{
							if(i==0)
								sb.append('(');
							sb.append(t_stream.next()).toString();
						}
						sb.append(')');
					}
					
					String s=sb.toString();
					ArrayList<Integer> ll=AuthorIndex.get(s);
					if(ll==null)
						ll=new ArrayList<Integer>();
					ll.add(FileUtilities.docId);
					AuthorIndex.put(s, ll);
				}
			}
		}
		catch (TokenizerException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception!");
			e.printStackTrace();
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
		FileUtilities.writeDocDic();
		FileUtilities.writeIndexFile(termIndexAC,FileUtilities.indexAC);
		FileUtilities.writeIndexFile(termIndexDG,FileUtilities.indexDG);
		FileUtilities.writeIndexFile(termIndexHK,FileUtilities.indexHK);
		FileUtilities.writeIndexFile(termIndexLP,FileUtilities.indexLP);
		FileUtilities.writeIndexFile(termIndexQS,FileUtilities.indexQS);
		FileUtilities.writeIndexFile(termIndexTZ,FileUtilities.indexTZ);
		FileUtilities.writeIndexFile(termIndexMisc,FileUtilities.indexMisc);
		FileUtilities.writeIndexFile(CatIndex,FileUtilities.indexCat);
		FileUtilities.writeIndexFile(PlaceIndex,FileUtilities.indexPlace);
		FileUtilities.writeIndexFile(AuthorIndex,FileUtilities.indexAuth);
		Runtime a = Runtime.getRuntime();
		long total = a.totalMemory();
		long free = a.freeMemory();
		long used = total - free;
		System.out.println("Total="+total+"\t free="+free+"Used="+used);
		docList= null;
		docCatList= null;
		termIndexAC = null;
		termIndexDG = null;
		termIndexHK = null;
		termIndexLP = null;
		termIndexQS = null;
		termIndexTZ = null;
		termIndexMisc = null;
	}
	
}
