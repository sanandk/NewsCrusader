/**
 * 
 */
package edu.buffalo.cse.irf14.query;

/**
 * @author nikhillo
 * Static parser that converts raw text to Query objects
 */
public class QueryParser {
	
	//created for testing purpose
	public static void main(String args[]){
		System.out.println(parse("\"Hello World\"","").toString());
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
