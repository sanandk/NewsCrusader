/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo Class responsible for writing indexes to disk
 */
public class IndexWriter {
	/**
	 * Default constructor
	 * 
	 * @param indexDir
	 *            : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {
		// TODO : YOU MUST IMPLEMENT THIS
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

	public void addDocument(Document d) throws IndexerException {
		// TODO : YOU MUST IMPLEMENT THIS
		// Updated by anand on Sep 14
		TokenStream t_stream = null;
		Tokenizer t=new Tokenizer();
		try {
			System.out.println("Content is "+d.getField(FieldNames.CONTENT)[0]);
			t_stream=t.consume(d.getField(FieldNames.CONTENT)[0]);
		} catch (TokenizerException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception!");
			e.printStackTrace();
		}
		
		while (t_stream.hasNext()) {
            System.out.println("Item is: " + t_stream.next());
        }

		System.exit(0);
		
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
	}
}
