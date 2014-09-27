/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.index.FileUtilities;
import edu.buffalo.cse.irf14.index.IndexWriter;

/**
 * @author nikhillo Class that parses a given file into a Document
 */
public class Parser {
	/**
	 * Static method to parse the given file into the Document object
	 * 
	 * @param filename
	 *            : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException
	 *             In case any error occurs during parsing
	 */
	public static int skippedCount=0;
	public static Document parse(String filename) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS
		// Code edited by Karthik-j on Sept 9, 2014- Starts
		Document doc = new Document();
		LinkedList<String> docCatList; 
		try {
				
			String[] dirList = filename
					.split(Pattern.quote(File.separator));
			int dirLen = dirList.length;
			if (dirLen > 0) {// if less then zero try throwing an error
				// saying invalid file name
				doc.setField(FieldNames.FILEID, dirList[dirLen - 1]);
				doc.setField(FieldNames.CATEGORY, dirList[dirLen - 2]);
			if(!IndexWriter.docList.containsKey(dirList[dirLen - 1])){
				docCatList=new LinkedList<String>();
				docCatList.add(String.valueOf(++FileUtilities.docId));
				docCatList.add(dirList[dirLen - 2]);
				IndexWriter.docList.put(dirList[dirLen - 1],docCatList);
//				IndexWriter.docList.put(dirList[dirLen - 1],Fi);
				 	File inputFile = new File(filename);
					
					FileReader inputFileReader = new FileReader(inputFile);
					BufferedReader inputBR = new BufferedReader(inputFileReader);
					
					String inputLine;
					String content="";
					boolean titleFlag = true;
					boolean placeFlag = true; 
					try {
						while (null != (inputLine = inputBR.readLine())) {
							inputLine = inputLine.trim();
							if (!inputLine.isEmpty()) {
								if (titleFlag) {
									doc.setField(FieldNames.TITLE, inputLine);
									titleFlag = false;
								} else {
									// if(){
									// author names are separated by and
									if (inputLine.startsWith("<AUTHOR>")) {
										inputLine = inputLine.replace(
												"<AUTHOR>", "");
										inputLine = inputLine.replace(
												"</AUTHOR>", "");
										String byRegex = "By|by|BY";
										inputLine = inputLine.replaceFirst(
												byRegex, "");
										String[] authorData = inputLine
												.split(",");
										if (inputLine.contains(",")) {
											doc.setField(FieldNames.AUTHOR,
													authorData[0].trim());
											doc.setField(FieldNames.AUTHORORG,
													authorData[1].trim());
										} else {
											doc.setField(FieldNames.AUTHOR,
													authorData[0].trim());
										}
									} else {
										// PLACE, NEWSDATE, CONTENT
										if (inputLine.contains("-")&& placeFlag) {
											placeFlag=false;
											String placeInfo = inputLine.substring(0, inputLine.indexOf("-"));
											content=inputLine.substring(inputLine.indexOf("-")+1);
											if (placeInfo.contains(",")) {
//												String[] placeInfoList = placeInfo.split(",");
//												doc.setField(FieldNames.PLACE,placeInfoList[0]);
//												doc.setField(FieldNames.NEWSDATE,placeInfoList[1]);
												doc.setField(FieldNames.PLACE,placeInfo.substring(0, placeInfo.lastIndexOf(",")).trim());
												doc.setField(FieldNames.NEWSDATE,placeInfo.substring(placeInfo.lastIndexOf(",")+1).trim());
											} else {
												doc.setField(FieldNames.PLACE,placeInfo.trim());
											}
										} else {
											content+=" "+inputLine;
											
										}
									}

									// }
								}
							}
							doc.setField(FieldNames.CONTENT, content);
						}
						
					} catch (IOException ioe) {
							throw new ParserException();
					}
				}else{
					docCatList=IndexWriter.docList.get(dirList[dirLen - 1]);
					docCatList.add(dirList[dirLen - 2]);
					IndexWriter.docList.put(dirList[dirLen - 1],docCatList);
					skippedCount++;
				}
			
			}
			else
				throw new ParserException();
		} catch (FileNotFoundException fe) {
			throw new ParserException();
		} catch (NullPointerException npe) {
			throw new ParserException();
		}
		
		return doc;
		// Code edited by Karthik-j on Sept 9, 2014- Ends
	}

}

