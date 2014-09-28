/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
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
public class IndexWriter  implements Runnable{  
	
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
	static int tokens=0;
	public static TreeMap<String,LinkedList<String>> docList= new TreeMap<String,LinkedList<String>>();
	public static TreeMap<String, LinkedList<Integer>> CatIndex = new TreeMap<String, LinkedList<Integer>>();
	public static TreeMap<String, LinkedList<Integer>> PlaceIndex = new TreeMap<String, LinkedList<Integer>>();
	public static TreeMap<String, LinkedList<Integer>> AuthorIndex = new TreeMap<String, LinkedList<Integer>>();
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
     //   docBW.write(files + delim + fileName.get(files) + "\n");
		Analyzer analyzer;
		Token tp;
		TokenStream t_stream = null;
		Tokenizer t=new Tokenizer();
	//	if(FileUtilities.docId==7372)
		//	System.out.println("FOUND!!!~"+d.getField(FieldNames.FILEID)[0]);
		String[] tit = d.getField(FieldNames.TITLE);
		String[] cont=d.getField(FieldNames.CONTENT);
		String[] cat=d.getField(FieldNames.CATEGORY);
		String[] place=d.getField(FieldNames.PLACE);
		String[] author=d.getField(FieldNames.AUTHOR);
		String[] authororg=d.getField(FieldNames.AUTHORORG);
		AnalyzerFactory fact = AnalyzerFactory.getInstance();
		
		try {
				
			if(cont!=null || tit!=null){	
				if(tit!=null){
						t_stream=t.consume(tit[0]);
						t_stream.append(t.consume(cont[0]));
				}
				else
					t_stream=t.consume(cont[0]);
				analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
					while (analyzer.increment()) {
				
					}
			t_stream.reset();
			
			HashMap<Integer,Integer> termPosting;
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
							if(IndexWriter.termIndexAC.containsKey(termValue)){
								termPosting=IndexWriter.termIndexAC.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexAC.put(termValue,termPosting );
							break;
						case 'd': case 'e': case 'f': case 'g':
							if(IndexWriter.termIndexDG.containsKey(termValue)){
								termPosting=IndexWriter.termIndexDG.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexDG.put(termValue,termPosting );
							break;
						case 'h': case 'i': case 'j': case 'k':
							if(IndexWriter.termIndexHK.containsKey(termValue)){
								termPosting=IndexWriter.termIndexHK.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexHK.put(termValue,termPosting );
							break;
						case 'l': case 'm': case 'n': case 'o': case 'p':
							if(IndexWriter.termIndexLP.containsKey(termValue)){
								termPosting=IndexWriter.termIndexLP.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexLP.put(termValue,termPosting );
							break;
						case 'q': case 'r': case 's':
							if(IndexWriter.termIndexQS.containsKey(termValue)){
								termPosting=IndexWriter.termIndexQS.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexQS.put(termValue,termPosting );
							break;
						case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
							if(IndexWriter.termIndexTZ.containsKey(termValue)){
								termPosting=IndexWriter.termIndexTZ.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexTZ.put(termValue,termPosting );
							break;
						default :
							if(IndexWriter.termIndexMisc.containsKey(termValue)){

								termPosting=IndexWriter.termIndexMisc.get(termValue);
								frequency=termPosting.get(FileUtilities.docId);
								if(frequency==null){
									termPosting.put(FileUtilities.docId, 1);
								}else{
									termPosting.put(FileUtilities.docId, ++frequency);
								}
							}else
								termPosting.put(FileUtilities.docId, 1);
							IndexWriter.termIndexMisc.put(termValue,termPosting );
					}
			
				}

			}
			}

			
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
				LinkedList<Integer> ll=PlaceIndex.get(s);
				if(ll==null)
					ll=new LinkedList<Integer>();
				ll.add(FileUtilities.docId);
				PlaceIndex.put(s, ll);
			}
			if(cat!=null){
				LinkedList<Integer> ll=CatIndex.get(cat[0]);
				if(ll==null)
					ll=new LinkedList<Integer>();
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
								sb.append("(");
							sb.append(t_stream.next()).toString();
						}
						sb.append(")");
					}
					
					String s=sb.toString();
					LinkedList<Integer> ll=AuthorIndex.get(s);
					if(ll==null)
						ll=new LinkedList<Integer>();
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
		FileUtilities.writeToDic(docList, DictType.DOC);
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
		FileUtilities.closeDict();
		read();
	}
	
	public void read() {
//		FileUtilities.readIndexFile(FileUtilities.indexAC);
//		FileUtilities.readIndexFile(FileUtilities.indexDG);
//		FileUtilities.readIndexFile(FileUtilities.indexHK);
//		FileUtilities.readIndexFile(FileUtilities.indexLP);
//		FileUtilities.readIndexFile(FileUtilities.indexQS);
//		FileUtilities.readIndexFile(FileUtilities.indexTZ);
//		FileUtilities.readIndexFile(FileUtilities.indexMisc);
//		FileUtilities.readIndexFile(FileUtilities.indexCat);
//		FileUtilities.readIndexFile(FileUtilities.indexPlace);
//		FileUtilities.readIndexFile(FileUtilities.indexAuth);
		System.out.println("Tokens"+tokens);
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
