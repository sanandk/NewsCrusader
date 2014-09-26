/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

/**
 * @author nikhillo
 *
 */
public class Runner {

	/**
	 * 
	 */
	public Runner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ipDir = args[0];
		String indexDir = args[1];
		//more? idk!
		
		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();
		
		String[] files;
		File dir;
		
		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);
		System.out.println(indexDir);
		long startTime=System.currentTimeMillis();
		try {
			for (String cat : catDirectories) {
				
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				if (files == null)
					continue;
				
				for (String f : files) {
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						System.out.println("Parsed:"+d.getField(FieldNames.FILEID)[0]);
						writer.addDocument(d);
						
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
			}
			
			try{
			String indexTest="IndexTestFile";
			File indexTestFile= new File(indexDir+File.separator+indexTest);
			if(indexTestFile.exists()){
				indexTestFile.delete();
			}
			FileWriter indexTestFw;
			BufferedWriter indexTestBw;
			indexTestFw= new FileWriter(indexTestFile);
			indexTestBw= new BufferedWriter(indexTestFw);
			System.out.println(IndexWriter.termIndex.size());
			for (String key : IndexWriter.termIndex.keySet()) {
				indexTestBw.write("\n"+key + " " + IndexWriter.termIndex.get(key));
		    }
			indexTestBw.close();
			}catch(IOException e){
				
			}
			
//			    
			
			
//			System.out.println("==============*******=============\n"+termValue+"===>"+termPosting+"\n==============*******=============");
//			System.out.println("No Place");
//			writer.printA();
//			System.out.println("No Date");
//			writer.printB();
			writer.close();
			System.out.println(startTime+"\n"+System.currentTimeMillis()+"\nTotal Number of tokenss"+IndexWriter.tokenSize);
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
