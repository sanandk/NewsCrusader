package edu.buffalo.cse.irf14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.index.FileUtilities;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;

/**
 * Main class to run the searcher.
 * As before implement all TODO methods unless marked for bonus
 * @author nikhillo
 *
 */
public class SearchRunner {
	public enum ScoringModel {TFIDF, OKAPI};
	
	public static void main(String args[])
	{
		SearchRunner r=new SearchRunner("D:\\output","D:\\Projects\\news_training\flattened",'Q',System.out);
		r.query("chase manhattan", ScoringModel.TFIDF);

	}
	
	PrintStream o_stream;
	char mode;
	String indexDir;
	/**
	 * Default (and only public) constuctor
	 * @param indexDir : The directory where the index resides
	 * @param corpusDir : Directory where the (flattened) corpus resides
	 * @param mode : Mode, one of Q or E
	 * @param stream: Stream to write output to
	 */
	public SearchRunner(String indexDir, String corpusDir, 
			char mode, PrintStream stream) {
		//TODO: IMPLEMENT THIS METHOD
		
		this.indexDir = indexDir;
		this.mode = mode;
		FileUtilities.setOutputDir(indexDir);
		readIndex();
		o_stream=stream;
		
	}
	
	public void readIndex() {
		FileUtilities.readDocDic();
		
			IndexWriter.termIndexAC = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexAC);
			IndexWriter.termIndexDG = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexDG);
			IndexWriter.termIndexHK = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexHK);
			IndexWriter.termIndexLP = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexLP);
			IndexWriter.termIndexQS = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexQS);
			IndexWriter.termIndexTZ = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexTZ);
			IndexWriter.termIndexMisc = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexMisc);
		
			IndexWriter.AuthorIndex=(TreeMap<String, ArrayList<Integer>>) FileUtilities.readIndexFile(FileUtilities.indexAuth);
		
			IndexWriter.CatIndex=(TreeMap<String, ArrayList<Integer>>) FileUtilities.readIndexFile(FileUtilities.indexCat);
		
			IndexWriter.PlaceIndex=(TreeMap<String, ArrayList<Integer>>) FileUtilities.readIndexFile(FileUtilities.indexPlace);
			//if(term.equals("leverage"))
				for(Entry<String, HashMap<Integer, Double>> id:IndexWriter.termIndexLP.entrySet())
				{
					if(id.getKey().startsWith("leverage"))
					{
					System.out.print("\n"+id.getKey()+":");
					for(Integer a:id.getValue().keySet())
						System.out.print(" "+a+",");
					}
				}
	}
	
	HashMap<String, Double> qterms=new HashMap<String, Double>();
	HashMap<Integer, Double> doclen=new HashMap<Integer, Double>();
	Tokenizer t=new Tokenizer();
	Analyzer analyzer;
	final AnalyzerFactory fact = AnalyzerFactory.getInstance();
	int flag=0;
	public TreeMap<Integer, Double> getPostings(String term, IndexType type, String op) 
	{
		String term_f;
		TreeMap<Integer, Double> postingsMap_f=new TreeMap<Integer, Double>();
		try
		{
		TokenStream t_stream=t.consume(term);
		analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
		
		while (analyzer.increment()) {
			
		}
		
		term_f=t_stream.next().toString();
			if(flag==0){
				flag=1;
				postingsMap_f=getPostings(term_f, type, "OR");
			}
		}
		catch(TokenizerException e)
		{
			
		}
		
		TreeMap<Integer, Double> postingsMap=new TreeMap<Integer, Double>();
		HashMap<Integer, Double> postingList;
		ArrayList<Integer> postingArray;
		double idf=0,tf=0;
		Double qw=0.0;
		Double w=0.0;
		// if(op.equals("OR") || op.equals("AND"))
		//	 postingsMap=postings;
		switch(type){
			case TERM:
				if(null!=term && !term.isEmpty()){
					term=term.toLowerCase();
				//	char termStart= term.toLowerCase().charAt(0);
					switch(term.charAt(0)){
					case 'a': case 'b': case 'c':
						postingList=IndexWriter.termIndexAC.get(term);
						
						break;
					case 'd': case 'e': case 'f': case 'g':
						postingList=IndexWriter.termIndexDG.get(term);
						
						break;
					case 'h': case 'i': case 'j': case 'k':
						postingList=IndexWriter.termIndexHK.get(term);
						
						break;
					case 'l': case 'm': case 'n': case 'o': case 'p':
						postingList=IndexWriter.termIndexLP.get(term);
						
						
						break;
					case 'q': case 'r': case 's':
						postingList=IndexWriter.termIndexQS.get(term);
						
						break;
					case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
						postingList=IndexWriter.termIndexTZ.get(term);
						
						break;
					default :
						postingList=IndexWriter.termIndexMisc.get(term);
						
					}
					if(postingList!=null)
					{
						idf=postingList.get(-1);
						for(Integer docId: postingList.keySet()){
							tf=postingList.get(docId);
							
							w=postingsMap.get(docId);
							qw=qterms.get(term);
							
							if(qw==null || qw!=0.0)
								qw=idf;
							if(w==null)
								w=0.0;
							w+=qw*tf*idf; // or use log(1+tf).idf
							
							doclen.put(docId, tf*idf);
					//		w+=qw*tf; 
							postingsMap.put(docId, w);
							qterms.put(term, qw);
						}
					}
					
				}
				break;
			case AUTHOR:
				postingArray=IndexWriter.AuthorIndex.get(term);
				if(postingArray!=null){
					
					for(Integer docId: postingArray){
						w=postingsMap.get(docId);
						if(w==null)
							w=0.0;
						w+=1;
						postingsMap.put(docId, w);
					}
				}
				break;
			case CATEGORY:
				postingArray=IndexWriter.CatIndex.get(term);
				if(postingArray!=null){
					
					for(Integer docId: postingArray){
						w=postingsMap.get(docId);
						if(w==null)
							w=0.0;
						w+=1;
						postingsMap.put(docId, w);
					}
				}
				break;
			case PLACE:
				postingArray=IndexWriter.PlaceIndex.get(term);
				if(postingArray!=null){
				
					for(Integer docId: postingArray){
						w=postingsMap.get(docId);
						if(w==null)
							w=0.0;
						w+=1;
						postingsMap.put(docId, w);
					}
				}
				break;
		}
	//	postings=;
	/*	if(op.equals("AND"))
		{
			postings=postingsMap;
			postings.keySet().retainAll(postingsMap.keySet());
		}
		else if(op.equals("OR"))
		{
			postings=postingsMap;
		}
		else
		{
			postings.keySet().removeAll(postingsMap.keySet());
		}*/
		if(postingsMap_f.size()>0)
			postingsMap=mergePostings(postingsMap, postingsMap_f, "OR");
		return postingsMap;
	}
	int iter=0;
	String defOp="AND";
	String op=defOp;
	public TreeMap<Integer, Double> processblock(TreeMap<Integer, Double> postings1,String val, String[] k) 
	{
		TreeMap<Integer, Double> postings2;
		int i=iter;
		IndexType itype=IndexType.TERM;
		if(val.equals("AND") || val.equals("OR"))
			op=val;
		else
		{
			if(val.contains("<")) // Handle NOT
			{
				op="NOT";
				val=val.replace("<", "");
				val=val.replace(">", "");
			}
			if(val.contains(":"))
			{
			String c_split[]=val.split(":");
			
			if(c_split[0].equals("Term"))
				itype=IndexType.TERM;
			else if(c_split[0].equals("Author"))
				itype=IndexType.AUTHOR;
			else if(c_split[0].equals("Category"))
				itype=IndexType.CATEGORY;
			else if(c_split[0].equals("Place"))
				itype=IndexType.PLACE;
			else if(c_split[0].equals("Term"))
				itype=IndexType.TERM;
			
			val=c_split[1];
			}
			if(val.contains("\""))
			{
				if(!val.endsWith("\""))
				{
					while(!k[++i].contains("\""))
					{
						val+=" "+k[i];	
					}
					val+=" "+k[i];
				}
				val=val.replace("\"", "");
			}
			if(op!="NOT")
				qterms.put(val, 1.0);
			else
				qterms.put(val, 0.0);
			flag=0;
			postings2=getPostings(val,itype,op);
			if(postings1.size()>0)
			postings1=mergePostings(postings1,postings2,op);
			else
				postings1=postings2;
		}
		return postings1;
	}
	
	public TreeMap<Integer,Double> mergePostings(TreeMap<Integer,Double> A, TreeMap<Integer,Double> B,String operator)
	{
		Double d;
		Integer[] akeys;
		if(operator.equals("AND"))
		{
			System.out.println(operator);
			akeys=A.keySet().toArray(new Integer[A.size()]);
			for(Integer a:akeys)
			{
				d=B.get(a);
				if(d!=null)
					A.put(a, A.get(a)+d);
				else
					A.remove(a);
			}

		}
		else if(operator.equals("OR"))
		{
			for(Integer b:B.keySet())
			{
				d=A.get(b);
				if(d!=null)
					A.put(b, B.get(b)+d);
				else
					A.put(b, B.get(b));
			}
		}
		else
		{
			for(Integer b:B.keySet())
				A.remove(b);
		}
		return A;
	}
	
	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {
		//TODO: IMPLEMENT THIS METHOD
		
		
		long startTime=System.currentTimeMillis();
		Query q=QueryParser.parse(userQuery, defOp);
		String fq=q.toString();
		String[] k=fq.split(" ");
		TreeMap<Integer, Double> postings=new TreeMap<Integer,Double>();
		String tempop=defOp;
		String val;
		for(iter=0;iter<k.length;iter++)
		{
			val=k[iter];
			if(val.equals("["))
			{
				TreeMap<Integer, Double> temp=new TreeMap<Integer,Double>();
				val=k[++iter];
				tempop=op;
				while(!val.equals("]"))
				{
					temp=processblock(temp, val,  k);
					val=k[++iter];
				}
				postings=mergePostings(postings,temp,tempop);
			}
			else if(!val.equals("{") && !val.equals("}"))
			{
				postings=processblock(postings,val,k);
			}
			else if(val.equals("AND") || val.equals("OR"))
				tempop=val;
		}
		 postings.remove(-1);
		double length=0,doc_len=0,score=0;
		for(double d:qterms.values())
		{
			length+=d*d;
		}
		length=Math.sqrt(length);
		doc_len=0;
		for(double d:doclen.values())
		{
			doc_len+=d*d;
		}
		doc_len=Math.sqrt(doc_len);
		for(Integer docID:postings.keySet())
		{
			score=postings.get(docID)/(length * doc_len);
			postings.put(docID, score);
		}
		
		 Comparator<Map.Entry<Integer, Double>> byMapValues = new Comparator<Map.Entry<Integer, Double>>() {
		        @Override
		        public int compare(Map.Entry<Integer, Double> left, Map.Entry<Integer, Double> right) {
		            return right.getValue().compareTo(left.getValue());
		        }
		    };
		    
		   
		    
		    List<Map.Entry<Integer, Double>> finalp = new ArrayList<Map.Entry<Integer, Double>>();
		    
		  
		    finalp.addAll(postings.entrySet());
		  
		    Collections.sort(finalp, byMapValues);
		    double min=Collections.min(postings.values());
		    double max=Collections.max(postings.values());
		long time=System.currentTimeMillis()-startTime;
		
		double sc=0.0;
		o_stream.println("Query: "+userQuery);
		o_stream.println("Query time: "+time + " seconds");
		o_stream.println("\n");
		Entry<Integer, Double> doc;
		for(int i=0;i<finalp.size();i++)
		{
			
			doc = finalp.get(i);
			sc=(doc.getValue()-min)/(max-min);
			o_stream.println("Result Rank: "+(i+1));
			o_stream.println("Result title: "+doc.getKey());
			o_stream.println("Result snippet: "+IndexWriter.docCatList.get(doc.getKey())[0]);
			o_stream.println("Result relevance: "+doc.getValue());
			o_stream.println("\n");
		}
	//	
	}
	
	
	 
	
	
	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 */
	public void query(File queryFile) {
		//TODO: IMPLEMENT THIS METHOD
//		numQueries=3
//				Q_1A63C:{hello world}
//				Q_6V87S:{Category:oil AND place:Dubai AND ( price OR cost )}
//				Q_4K66L:{long query with several words}
		
		FileReader f_in = null;
		BufferedReader br= null;
				
		try {
			f_in = new FileReader(queryFile);
			br= new BufferedReader(f_in);
			String line;
			int queryNum=0;
			String queryId;
			String userQuery;
			Query query;
			if((line=br.readLine())!=null){
				if(line.trim().startsWith("numQueries=")){
					queryNum=Integer.parseInt(line.substring(line.indexOf("numQueries=")+11));
				}else{
					System.out.println("Corrupted Query File.Invalid numQueries statement");
				}
			}
			int i=0;
			while(i++<queryNum){
				if((line=br.readLine())!=null){
					if(line.contains(":")){
						queryId=line.substring(0,line.indexOf(":"));
						userQuery=line.substring(line.indexOf(":")+2,line.length()-1);
						query=QueryParser.parse(userQuery, "OR");
					}else{
						System.out.println("Error at line "+i+" in QueryString");
						break;
					}
				}else{
					System.out.println("Query "+i+" missing in file");
					break;
				}
			}
			
		}catch(IOException ioe){
			
		}

	}
	
	/**
	 * General cleanup method
	 */
	public void close() {
		//TODO : IMPLEMENT THIS METHOD
		o_stream.close();
	}
	
	/**
	 * Method to indicate if wildcard queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
	}
	
	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * @return A Map containing the original query term as key and list of
	 * possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		//TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		return null;
		
	}
	
	/**
	 * Method to indicate if speel correct queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return false;
	}
	
	/**
	 * Method to get ordered "full query" substitutions for a given misspelt query
	 * @return : Ordered list of full corrections (null if none present) for the given query
	 */
	public List<String> getCorrections() {
		//TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}
}
