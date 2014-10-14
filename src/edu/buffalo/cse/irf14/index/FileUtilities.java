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

//	public enum DictType {
//		DOC, TERM, TITLE, AUTHOR, DATE;
//	};

	public enum Type {
		DICT, INDEX;
	};

	public static String outputDir;
	
	//Dictionary Files
	public static String docDict = "DocDictionary";
	public static String docCatDict = "DocCatDictionary";
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
	public static String indexCat="CatIndex";
	public static String indexPlace="PlaceIndex";
	public static String indexAuth="AuthIndex";
	
	
	
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

	public static void writeDocDic() {
		FileOutputStream f_out = null;
		ObjectOutputStream obj_out = null;
		GZIPOutputStream zip_out=null;
		File docDicFile= new File(outputDir+File.separator+docDict);
		File docCatDicFile= new File(outputDir+File.separator+docCatDict);
		try {
			
			if (docDicFile.exists()) {
				docDicFile.delete();

			}
			f_out = new FileOutputStream(docDicFile);
			zip_out = new GZIPOutputStream(f_out);
			obj_out = new ObjectOutputStream(zip_out);
			obj_out.writeObject(IndexWriter.docList);
			obj_out.close();
			zip_out.close();
			f_out.close();
			f_out = new FileOutputStream(docCatDicFile);
			zip_out = new GZIPOutputStream(f_out);
			obj_out = new ObjectOutputStream(zip_out);
			obj_out.writeObject(IndexWriter.docCatList);
			obj_out.close();
			zip_out.close();
			f_out.close();
		} catch (IOException e) {

		
		if (null != obj_out) {
			try {
				obj_out.close();
			} catch (IOException ex) {

			}
		}
		if (null != zip_out) {
			try {
				zip_out.close();
			} catch (IOException ex) {

			}
		}
		if (null != f_out) {
			try {
				f_out.close();
			} catch (IOException ex) {

			}
		}
		}
	}

	public static void readDocDic() {
		FileInputStream f_in = null;
		ObjectInputStream obj_in = null;
		GZIPInputStream zip_in=null;
		IndexWriter.docList=null;
		IndexWriter.docCatList=null;
		File docDicFile= new File(outputDir+File.separator+docDict);
		File docCatDicFile= new File(outputDir+File.separator+docCatDict);
		try {
			f_in = new FileInputStream(docDicFile);
			zip_in = new GZIPInputStream(f_in);
			obj_in = new ObjectInputStream(zip_in);
			IndexWriter.docList= (TreeMap<String,Integer>)obj_in.readObject();
			obj_in.close();
			zip_in.close();
			f_in.close();
			f_in = new FileInputStream(docCatDicFile);
			zip_in = new GZIPInputStream(f_in);
			obj_in = new ObjectInputStream(zip_in);
			IndexWriter.docCatList=(TreeMap<Integer,String[]>)obj_in.readObject();
			obj_in.close();
			zip_in.close();
			f_in.close();
		} catch (Exception e) {
			if (null != obj_in) {
				try {
					obj_in.close();
				} catch (IOException ex) {
	
				}
			}
			if (null != zip_in) {
				try {
					zip_in.close();
				} catch (IOException ex) {
	
				}
			}
			if (null != f_in) {
				try {
					f_in.close();
				} catch (IOException ex) {
	
				}
			}
		}
	}

	public static void writeIndexFile(Object indexMap,String fileName) {
		FileOutputStream f_out = null;
		ObjectOutputStream obj_out = null;
		GZIPOutputStream zip_out=null;
		File indexFile= new File(outputDir+File.separator+fileName);
		try {
			if(indexFile.exists()){
				indexFile.delete();
			}
			f_out = new FileOutputStream(indexFile);
			zip_out = new GZIPOutputStream(f_out);
			obj_out = new ObjectOutputStream(zip_out);
			obj_out.writeObject(indexMap);
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
	
	public static Object readIndexFile(String fileName) {
		Object indexMap=null;
		FileInputStream f_in = null;
		ObjectInputStream obj_in = null;
		GZIPInputStream zip_in=null;
		File indexFile= new File(outputDir+File.separator+fileName);
		try {
			f_in = new FileInputStream(indexFile);
			zip_in = new GZIPInputStream(f_in);
			obj_in = new ObjectInputStream(zip_in);
			indexMap=obj_in.readObject();
		} catch (Exception e) {
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
		return indexMap;
	}

	public static void printToFile() {

	}

}
