package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
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
	public static int docId = 0;

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
		
			switch (type) {
			case DOC:
				try {
					File docDicFile = new File(outputDir + File.separator + docDict);
					if (docDicFile.exists()) {
						docDicFile.delete();

					}
					FileWriter docFW = new FileWriter(docDicFile, true);
					docBW = new BufferedWriter(docFW);
					TreeMap<String,LinkedList<String>> fileName = (TreeMap<String,LinkedList<String>>)data;
					for(String files: fileName.keySet()){
						docBW.write(files + delim + fileName.get(files) + "\n");
					}
				} catch (IOException e) {

				}
				try {
					if (null != docBW) {
						docBW.close();
					}
				} catch (IOException e) {

				}
				
			}

	}

	public static void closeDict() {
		
	}

	public static void writeIndexFile(TreeMap<String, HashMap<Integer,Integer>> indexMap,String fileName) {
		FileOutputStream f_out = null;
		ObjectOutputStream obj_out = null;
		GZIPOutputStream zip_out=null;
		File indexFile= new File(outputDir+File.separator+fileName);
//		BufferedWriter indexBW= null;
//		FileWriter indexFW= null;
//		HashMap<Integer,Integer> fileFrequencyMap;
		try {
			if(indexFile.exists()){
				indexFile.delete();
			}
			f_out = new FileOutputStream(indexFile);
			zip_out = new GZIPOutputStream(f_out);
			obj_out = new ObjectOutputStream(zip_out);
			obj_out.writeObject(indexMap);
			
//			indexFW= new FileWriter(indexFile);
//			indexBW= new BufferedWriter(indexFW);
//			
//			for (String key : indexMap.keySet()) {
//				
//				indexBW.write("\n"+key + delim + indexMap.get(key));
//		    }
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		if (null != obj_out) {
			try {
				obj_out.close();
			} catch (IOException e) {

			}
		}
		if (null != zip_out) {
			try {
				zip_out.close();
			} catch (IOException e) {

			}
		}
		if (null != f_out) {
			try {
				f_out.close();
			} catch (IOException e) {

			}
		}

	}
	
	public static void readIndexFile(String fileName) {
		TreeMap<String, HashMap<Integer,Integer>> indexMap;
		FileInputStream f_in = null;
		ObjectInputStream obj_in = null;
		GZIPInputStream zip_in=null;
		File indexFile= new File(outputDir+File.separator+fileName);
//		BufferedWriter indexBW= null;
//		FileWriter indexFW= null;
//		HashMap<Integer,Integer> fileFrequencyMap;
		try {
			f_in = new FileInputStream(indexFile);
			zip_in = new GZIPInputStream(f_in);
			obj_in = new ObjectInputStream(zip_in);
			indexMap=(TreeMap<String, HashMap<Integer,Integer>>)obj_in.readObject();
			
			for (String key : indexMap.keySet()) {
				System.out.println("\n"+key + delim + indexMap.get(key));
				break;
		    }
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != obj_in) {
			try {
				obj_in.close();
			} catch (IOException e) {

			}
		}
		if (null != zip_in) {
			try {
				zip_in.close();
			} catch (IOException e) {

			}
		}
	}

	public static void printToFile() {

	}

}
