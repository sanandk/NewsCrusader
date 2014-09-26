/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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
		  FileUtilities.openDict();
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
	public static int tokenSize =0;
	public static ConcurrentHashMap<String, StringBuilder> termIndex = new ConcurrentHashMap<String, StringBuilder>();
	
	
	public void addDocument(Document d) throws IndexerException {
		// TODO : YOU MUST IMPLEMENT THIS
		// Updated by anand on Sep 14
		
		FileUtilities.writeToDic(d.getField(FieldNames.FILEID)[0], DictType.DOC);
		
		TokenStream t_stream = null;
		Tokenizer t=new Tokenizer();
		AnalyzerFactory fact = AnalyzerFactory.getInstance();
		try {
			t_stream=t.consume(d.getField(FieldNames.CONTENT)[0]);
			Analyzer analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
			
	//		t_stream=t.consume(d.getField(FieldNames.CONTENT)[0]);
	//		Analyzer analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, t_stream);
			
			while (analyzer.increment()) {
				
			}
			tokenSize+=t_stream.my_stream.size();
			System.out.println("FileID:"+d.getField(FieldNames.FILEID)[0]+" Tokens:"+t_stream.my_stream.size()+" total:"+tokenSize);
			t_stream.reset();
//			System.out.println("Content is "+d.getField(FieldNames.CONTENT)[0]);
//			System.out.print("Content is ");
			Token tp;
			StringBuilder termPosting ;
			while(t_stream.hasNext())
			{
				tp=t_stream.next();
				termPosting= new StringBuilder();
				if(tp!=null){
					String termValue=tp.toString();
					if(termIndex.containsKey(termValue)){
						termPosting=termIndex.get(termValue);
						
					}
//					else{
						termPosting.append(","+FileUtilities.docId);
						
						termIndex.put(termValue,termPosting );
//					}
				}
//				System.out.print(tp.toString()+"|");
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
		FileUtilities.openDict();
	}
}
