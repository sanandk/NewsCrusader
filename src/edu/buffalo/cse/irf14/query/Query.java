package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.index.IndexType;

/**
 * Class that represents a parsed query
 * @author nikhillo
 *
 */
public class Query {
	
	private String queryText;
	private String queryString;
	private String defOp="OR";
	
	final static Set<String> keywordList = new HashSet<String>(Arrays.asList("AND","OR","NOT"));
	final static Set<String> stopwordList = new HashSet<String>(Arrays.asList("Term","Category","Place","Author"));
	
	Query(String queryText){
		this.queryText= queryText;
		
		String currentIndexType="Term"; 
		String tempIndex;
		String[] queryTextArray;
		ArrayList<String> queryList=new ArrayList<String>();
		boolean quoteFlag= false;
		boolean braceFlag= false;
		boolean notFlag= false;
		boolean termFlag= false;
		
		
//		if(queryText.contains(":")){
			queryTextArray=queryText.split(" ");

			for(int i=0,len=queryTextArray.length;i<len;i++){
				tempIndex=queryTextArray[i];
				if(tempIndex.contains(":")){
					currentIndexType=tempIndex.split(":")[0];
					tempIndex=tempIndex.split(":")[1];
					if(tempIndex.startsWith("(")){
						queryList.add("[");
						tempIndex=tempIndex.substring(1);//to remove the brace from the string
						braceFlag=true;
					}
					
					if(tempIndex.startsWith("\"") && !tempIndex.endsWith("\"")){
						String tempAhead;
						do{
							i++;
							if(i>=len){
								break;
							}
							tempAhead=queryTextArray[i];
							tempIndex+=" "+tempAhead;
						}while(!tempAhead.endsWith("\""));
					}
					
					if(tempIndex.equals("AND")||tempIndex.equals("OR")){
						queryList.add(tempIndex);
					}else if(tempIndex.equals("NOT")){
						queryList.add("AND");
						queryList.add("<");
					}else{
						tempIndex=currentIndexType+":"+tempIndex;
						if(braceFlag && tempIndex.endsWith(")")){
							tempIndex=tempIndex.substring(0,tempIndex.length()-1);
							queryList.add(tempIndex);
							queryList.add("]");
							braceFlag=false;
						}
						else
							queryList.add(tempIndex);
						i++;
						if(i<len){
							tempIndex=queryTextArray[i];
							if(!tempIndex.equals("AND")&&!tempIndex.equals("OR")&&!tempIndex.equals("NOT")){
								queryList.add(defOp);
							}	i--;
						}
					}
				
				}else{
					if(tempIndex.startsWith("(")){
						queryList.add("[");
						tempIndex=tempIndex.substring(1);//to remove the brace from the string
						braceFlag=true;
					}else if(queryList.contains("AND") && i+1<len && !termFlag && !keywordList.contains(queryTextArray[i]) && !keywordList.contains(queryTextArray[i+1])){
							queryList.add("[");
							termFlag=true;
					}
					
					if(tempIndex.startsWith("\"") && !tempIndex.endsWith("\"")){
						String tempAhead;
						do{
							i++;
							if(i>=len){
								break;
							}
							tempAhead=queryTextArray[i];
							tempIndex+=" "+tempAhead;
						}while(!tempAhead.endsWith("\""));
					}
					
					if(tempIndex.equals("AND")||tempIndex.equals("OR")){
						queryList.add(tempIndex);
					}else
						if(tempIndex.equals("NOT")){
						queryList.add("AND");
						queryList.add("<");
					}else{
						
						if(braceFlag && tempIndex.endsWith(")")){
							tempIndex=currentIndexType+":"+tempIndex;
							tempIndex=tempIndex.substring(0,tempIndex.length()-1);
							queryList.add(tempIndex);
							queryList.add("]");
							braceFlag=false;
						}
						else{
							if(!braceFlag){
								currentIndexType="Term";
							}
							tempIndex=currentIndexType+":"+tempIndex;
							queryList.add(tempIndex);
							if(termFlag && (i+1==len || (i+1<len && keywordList.contains(i+1)))){
								queryList.add("]");
								termFlag=false;
							}
						}
//						else{
//							
//							tempIndex=currentIndexType+":"+tempIndex;
//							queryList.add(tempIndex);
//							if(termFlag && (i+1==len || (i+1<len && keywordList.contains(i+1)))){
//								queryList.add("]");
//								termFlag=false;
//							}
//						}
						i++;
						if(i<len){
							tempIndex=queryTextArray[i];
							if(!tempIndex.equals("AND")&&!tempIndex.equals("OR")&&!tempIndex.equals("NOT")){
								queryList.add(defOp);
							}
							i--;
						}
					}
				
				}
			}
			
//		}else{
//			queryTextArray=queryText.split(" ");
//
//			for(int i=0,len=queryTextArray.length;i<len;i++){
//				tempIndex=queryTextArray[i];
//				if(tempIndex.startsWith("(")){
//					queryList.add("[");
//					tempIndex=tempIndex.substring(1);//to remove the brace from the string
//					braceFlag=true;
//				}
//				
//				if(tempIndex.startsWith("\"") && !tempIndex.endsWith("\"")){
//					String tempAhead;
//					do{
//						i++;
//						if(i>=len){
//							break;
//						}
//						tempAhead=queryTextArray[i];
//						tempIndex+=" "+tempAhead;
//					}while(!tempAhead.endsWith("\""));
//				}
//				
//				if(tempIndex.equals("AND")||tempIndex.equals("OR")){
//					queryList.add(tempIndex);
//				}else if(tempIndex.equals("NOT")){
//					queryList.add("AND");
//					queryList.add("<");
//				}else{
//					tempIndex="Term:"+tempIndex;
//					if(braceFlag && tempIndex.endsWith(")")){
//						tempIndex=tempIndex.substring(0,tempIndex.length()-1);
//						queryList.add(tempIndex);
//						queryList.add("]");
//						braceFlag=false;
//					}
//					else
//						queryList.add(tempIndex);
//					i++;
//					if(i<len){
//						tempIndex=queryTextArray[i];
//						if(!tempIndex.equals("AND")&&!tempIndex.equals("OR")&&!tempIndex.equals("NOT")){
//							queryList.add(defOp);
//						}	i--;
//					}
//				}
//			}
//		}
		queryString="{ ";
		String query;
		for(int i=0,len=queryList.size();i<len;i++){
//		for(String query:queryList){
			query=queryList.get(i);
			if(query.equals("<")){
				queryString+=query;
				i++;
				query=queryList.get(i);
				queryString+=query+"> ";
			}
			else
				queryString+=query+ " ";
		}
		queryString+="}";

	}
	
	
	final Pattern quotesPattern= Pattern.compile("\"[^\"]*\"");
	Matcher quotesMatcher=null;
	
	/**
	 * Method to convert given parsed query into string
	 */
	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS
//		User query		String representation 
//		hello	{ Term:hello }
//		hello world	{ Term:hello OR Term:world }
//		“hello world”	{ Term:”hello world” }
//		orange AND yellow 	{ Term:orange AND Term:yellow }
//		(black OR blue) AND bruises	{ [ Term:black OR Term:blue ] AND Term:bruises }
//		Author:rushdie NOT jihad	{ Author:rushdie AND <Term:jihad> }
//		Category:War AND Author:Dutt AND Place:Baghdad AND prisoners detainees rebels	{ Category:War AND Author:Dutt AND Place:Baghdad AND [ Term:prisoners OR Term:detainees OR Term:rebels ] }
//		(Love NOT War) AND Category:(movies NOT crime)	{ [ Term:Love AND <Term:War> ] AND [ Category:movies AND <Category:crime> ] }
		//doubts: will query text contain combination of quotes and no qoute text
				return queryString;
	}
}
