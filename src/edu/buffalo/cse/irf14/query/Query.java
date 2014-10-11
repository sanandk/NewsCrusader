package edu.buffalo.cse.irf14.query;

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
	
	Query(String queryText){
		this.queryText= queryText;
	}
	
	
	final Pattern quotesPattern= Pattern.compile("\"[^\"]*\"");
	Matcher quotesMatcher=null;
	
	/**
	 * Method to convert given parsed query into string
	 */
	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS
//		hello	{ Term:hello }
//		hello world	{ Term:hello OR Term:world }
//		“hello world”	{ Term:”hello world” }
//		orange AND yellow 	{ Term:orange AND Term:yellow }
//		(black OR blue) AND bruises	{ [ Term:black OR Term:blue ] AND Term:bruises }
//		Author:rushdie NOT jihad	{ Author:rushdie AND <Term:jihad> }
//		Category:War AND Author:Dutt AND Place:Baghdad AND prisoners detainees rebels	{ Category:War AND Author:Dutt AND Place:Baghdad AND [ Term:prisoners OR Term:detainees OR Term:rebels ] }
//		(Love NOT War) AND Category:(movies NOT crime)	{ [ Term:Love AND <Term:War> ] AND [ Category:movies AND <Category:crime> ] }
		
		
		//doubts: will query text contain combination of quotes and no qoute text
		String queryString;
		if(quotesMatcher==null)
			quotesMatcher= quotesPattern.matcher(queryText);
		else
			quotesMatcher.reset(queryText);
		
		if(quotesMatcher.matches()){
			queryString="{"+IndexType.TERM+":"+queryText+"}";
		}
		else
		{
			String[] queryList=queryText.split(" ");
			queryString="{";
			for(String text: queryList){
				if(text.contains(":")){
//					for(String temp:){
						String indexType=text.split(":")[0];
						switch(IndexType.valueOf(indexType)){
							case PLACE:
								
								break;
							case AUTHOR:
								
								break;
							case CATEGORY:
								
								break;
							case TERM:
//								break;
							default:
								
						}
//					}
				}
				else{
					queryString+=IndexType.TERM+":"+text;
				}
			}
			queryString+="}";
		}
		return queryString;
	}
}
