/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import edu.buffalo.cse.irf14.index.IndexType;

/**
 * @author nikhillo
 * Static parser that converts raw text to Query objects
 */
public class QueryParser {
	
	//created for testing purpose
	public static void main(String args[]){
		
		System.out.println(parse("hello","").toString());
		System.out.println(parse("hello world","").toString());
		System.out.println(parse("\"hello world\"","").toString());
		System.out.println(parse("\"hello\"","").toString());
		System.out.println(parse("\"hello world","").toString());
		System.out.println(parse("orange AND yellow","").toString());
		System.out.println(parse("(black OR blue OR yellow) AND bruises","").toString());
		System.out.println(parse("Category:(black OR blue OR yellow) AND bruises","").toString());
		System.out.println(parse("(black blue) AND bruises","").toString());
		System.out.println(parse("Author:rushdie AND jihad","").toString());
		System.out.println(parse("Author:rushdie NOT jihad","").toString());
		System.out.println(parse("Category:War AND Author:Dutt AND Place:Baghdad AND prisoners","").toString());
		System.out.println(parse("Category:War AND Author:Dutt AND Place:Baghdad AND prisoners detainees rebels","").toString());
		System.out.println(parse("(Love NOT War) AND Category:(movies NOT crime)","").toString());
		
	}
	
	/**
	 * MEthod to parse the given user query into a Query object
	 * @param userQuery : The query to parse
	 * @param defaultOperator : The default operator to use, one amongst (AND|OR)
	 * @return Query object if successfully parsed, null otherwise
	 */
	public static Query parse(String userQuery, String defaultOperator) {
		//TODO: YOU MUST IMPLEMENT THIS METHOD
		
		if(defaultOperator.equals("AND")){
			defaultOperator= "AND";
		}else{
			defaultOperator= "OR";
		}
		
		Query q= new Query(userQuery);
		
		return q;
	}
}
