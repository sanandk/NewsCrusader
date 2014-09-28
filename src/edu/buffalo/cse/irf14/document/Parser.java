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
import java.util.List;
import java.util.regex.Matcher;
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

	final static Pattern byp=Pattern.compile("By|by|BY");
	static Matcher m;
    public static Document parse(String filename) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS
		// Code edited by Karthik-j on Sept 9, 2014- Starts
		Document doc = new Document();
//		LinkedList<String> catList; 
//		int tempFileId;
		FileReader inputFileReader;
		BufferedReader inputBR;
		String place=null;
		try {
				
			String[] dirList = filename.split(Pattern.quote(File.separator));
			int dirLen = dirList.length;
			String fileid,category;
			if (dirLen > 2) {// if less then zero try throwing an error saying invalid file name
				fileid=dirList[dirLen - 1];
				category=dirList[dirLen - 2];
				doc.setField(FieldNames.FILEID, fileid);
				doc.setField(FieldNames.CATEGORY, category);
			if(!IndexWriter.docList.containsKey(fileid)){
			 	
			 	
				inputFileReader = new FileReader(filename);
				inputBR = new BufferedReader(inputFileReader);
				
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
								// author names are separated by and
								if (inputLine.startsWith("<AUTHOR>")) {
									inputLine = inputLine.replace("<AUTHOR>", "");
									inputLine = inputLine.replace("</AUTHOR>", "");
									m=byp.matcher(inputLine);
									inputLine=m.replaceFirst("");
									String[] authorData = inputLine.split(",");
									if (inputLine.contains(",")) {
										doc.setField(FieldNames.AUTHOR,authorData[0].trim());
										doc.setField(FieldNames.AUTHORORG,authorData[1].trim());
									} else {
										doc.setField(FieldNames.AUTHOR,authorData[0].trim());
									}
								} else {
									// PLACE, NEWSDATE, CONTENT
									if (inputLine.contains("-")&& placeFlag) {
										placeFlag=false;
										int h=inputLine.indexOf("-");
											String placeInfo = inputLine.substring(0, h);
											content=inputLine.substring(h+1);
											if (placeInfo.contains(",")) {
												h=placeInfo.lastIndexOf(",");
												place=placeInfo.substring(0, h).trim();
												doc.setField(FieldNames.PLACE,place);
												doc.setField(FieldNames.NEWSDATE,placeInfo.substring(h+1).trim());
											} else {
												place=placeInfo.trim();
												doc.setField(FieldNames.PLACE,place);
											}
									} else {
										content+=" "+inputLine;
										
									}
								}
							}
						}
						doc.setField(FieldNames.CONTENT, content);
						
					}
					if(null!=inputBR){
						inputBR.close();
					}
				} catch (IOException ioe) {
						throw new ParserException();
				}
				
				}else{
                  /*docCatList=IndexWriter.docList.get(fileid);
					docCatList.add(category);
	                IndexWriter.docList.put(fileid,docCatList);
					catList=IndexWriter.CatIndex.get(category);
					if(catList==null)
						catList=new ArrayList<Integer>();
					catList.add(FileUtilities.docId);
					IndexWriter.CatIndex.put(dirList[dirLen - 2], catList);
					*/
					skippedCount++;
				}
			}
			else
				throw new ParserException();
		} catch (FileNotFoundException fe) {
			throw new ParserException();
//		}catch (IOException ioe) {
//			throw new ParserException();
		} catch (NullPointerException npe) {
			throw new ParserException();
		}
		
		return doc;
		// Code edited by Karthik-j on Sept 9, 2014- Ends
	}

}

