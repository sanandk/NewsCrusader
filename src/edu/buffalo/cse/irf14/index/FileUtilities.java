package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileUtilities {

	public enum DictType {
		DOC, TERM, TITLE, AUTHOR, DATE;
	};

	public enum Type {
		DICT, INDEX;
	};

	public static String outputDir;
	public static String docDict = "DocDictionary";
	public static String termDict = "TermDictionary";
	public static String authorDict = "AutDictionary";
	public static String delim="~";
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
			if(docDicFile.exists()){
				docDicFile.delete();
			}
			FileWriter docFW = new FileWriter(docDicFile, true);
			docBW = new BufferedWriter(docFW);
		} catch (IOException e) {

		}
	}

	public static void writeToDic(Object data, DictType type) {
		try {
			switch (type) {
			case DOC:
				if (null != docBW) {
					String fileName = data.toString();
					docBW.write(fileName + delim + ++docId +"\n");
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

	public static void printToFile() {

	}

}
