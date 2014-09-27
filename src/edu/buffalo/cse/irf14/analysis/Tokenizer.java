/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author nikhillo
 * Class that converts a given string into a {@link TokenStream} instance
 */
public class Tokenizer {
	// Updated by anand on Sep 14th
	String delimiter;
	/**
	 * Default constructor. Assumes tokens are whitespace delimited
	 */
	public Tokenizer() {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		delimiter=" ";
	}
	
	/**
	 * Overloaded constructor. Creates the tokenizer with the given delimiter
	 * @param delim : The delimiter to be used
	 */
	public Tokenizer(String delim) {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		delimiter=delim;
	}
	
	/**
	 * Method to convert the given string into a TokenStream instance.
	 * This must only break it into tokens and initialize the stream.
	 * No other processing must be performed. Also the number of tokens
	 * would be determined by the string and the delimiter.
	 * So if the string were "hello world" with a whitespace delimited
	 * tokenizer, you would get two tokens in the stream. But for the same
	 * text used with lets say "~" as a delimiter would return just one
	 * token in the stream.
	 * @param str : The string to be consumed
	 * @return : The converted TokenStream as defined above
	 * @throws TokenizerException : In case any exception occurs during
	 * tokenization
	 */
	public TokenStream consume(String str) throws TokenizerException {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		if(str==null || str.length()<1)
			throw new TokenizerException();
		String[] t_strings = str.split(delimiter);
		LinkedList<Token> t_list=new LinkedList<Token>();
		char endChar;
		int len;
		
		for(String text:t_strings){
			text=text.trim();
			Token tk=new Token(text);
			len=text.length();
			if(len>1)
			{
				endChar=text.charAt(text.length()-1);
				tk.lineEnd=(".?!".indexOf(endChar)!=-1);
			}
			if(len>0)
				t_list.add(tk);
			
		}
		return new TokenStream(t_list);
		
	}
}
