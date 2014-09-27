package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

public class FileUtilities {

	public enum DictType {
		DOC, TERM, TITLE, AUTHOR, DATE;
	};

	public enum Type {
		DICT, INDEX;
	};

	public static String outputDir;
	
	//Dictionary Files
	public static String docDict = "DocDictionary";
	public static String termDict = "TermDictionary";
	public static String authorDict = "AutDictionary";
	
	
	//Index Files
	public static String indexAC="ACIndex";
	public static String indexDG="DGIndex";
	public static String indexHK="HKIndex";
	public static String indexLP="LPIndex";
	public static String indexQS="QSIndex";
	public static String indexTZ="TZIndex";
	public static String indexMisc="MiscIndex";
	
	
	
	public static String delim = "~";
	// public static String fileName;
	public static BufferedWriter docBW;
	public static BufferedWriter termBW;
	public static BufferedWriter autBW;
	static int docId = 0;

	public static String getOutputDir() {
		return outputDir;
	}

	public static void setOutputDir(String outputDir) {
		FileUtilities.outputDir = outputDir;
	}

	// public static String getFileName() {
	// return fileName;
	// }
	//
	// public static void setFileName(String fileName) {
	// FileUtilities.fileName = fileName;
	// }

	public static void openDict() {
		try {
			File docDicFile = new File(outputDir + File.separator + docDict);
			if (docDicFile.exists()) {
				docDicFile.delete();

			}
			FileWriter docFW = new FileWriter(docDicFile, true);
			docBW = new BufferedWriter(docFW);
		} catch (IOException e) {

		}
	}

	public static void writeToDic(Object data, DictType type) {
		String fileName;
		try {
			switch (type) {
			case DOC:
				if (null != docBW) {
					fileName = data.toString();
					docBW.write(fileName + delim + ++docId + "\n");
				}
			}
		} catch (IOException e) {

		}
	}

	public static void closeDict() {
		try {
			if (null != docBW) {
				docBW.close();
			}
		} catch (IOException e) {

		}
	}

	public static void writeIndexFile(TreeMap<String, StringBuilder> indexMap,String fileName) {
		FileOutputStream f_out = null;
		ObjectOutputStream obj_out = null;
		//File indexFile= new File(outputDir+File.separator+fileName);
		 try {
		 FileOutputStream fos = new FileOutputStream(outputDir+File.separator+fileName);
		  GZIPOutputStream gz = new GZIPOutputStream(fos);
		  
		   ObjectOutputStream oos = new ObjectOutputStream(gz);
		 
			oos.writeObject(indexMap);
			  oos.close();
			  fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  

		/*BufferedWriter indexBW= null;
		FileWriter indexFW= null;
		
		try {
			if(indexFile.exists()){
				indexFile.delete();
			}
//			f_out = new FileOutputStream(indexFile);
//			obj_out = new ObjectOutputStream(f_out);
//			obj_out.writeObject(indexMap);
			
			indexFW= new FileWriter(indexFile);
			indexBW= new BufferedWriter(indexFW);
			
			for (String key : indexMap.keySet()) {
				indexBW.write("\n"+key + delim + indexMap.get(key));
		    }
			
		} catch (Exception e) {

		if (null != indexBW) {
			try {
				indexBW.close();
			} catch (IOException e) {

			}
		}*/
//	 	if (null != obj_out) {
//			try {
//				obj_out.close();
//			} catch (IOException e) {
//
//			}
//		}
	}

	public static void printToFile() {

	}

}
