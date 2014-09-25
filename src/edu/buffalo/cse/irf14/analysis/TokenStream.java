/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author nikhillo
 * Class that represents a stream of Tokens. All {@link Analyzer} and
 * {@link TokenFilter} instances operate on this to implement their
 * behavior
 */
public class TokenStream implements Iterator<Token>{
	// Updated by anand on 14th Sep
	
	public List<Token> my_stream;
	int currentPointer;
	TokenStream(ArrayList<Token> t_stream)
	{
		my_stream = t_stream;
		currentPointer=-1;
	}
	
	/**
	 * Method that checks if there is any Token left in the stream
	 * with regards to the current pointer.
	 * DOES NOT ADVANCE THE POINTER
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {
		// TODO YOU MUST IMPLEMENT THIS
		if((currentPointer+1)<my_stream.size() && my_stream.get(currentPointer+1)==null)
		{
			my_stream.remove(currentPointer+1);
			
		}
		return ((currentPointer+1)<my_stream.size());
	}
	
	public boolean hasPrevious() {
		// TODO YOU MUST IMPLEMENT THIS
		return ((currentPointer-1)>-1);
	}

	/**
	 * Method to return the next Token in the stream. If a previous
	 * hasNext() call returned true, this method must return a non-null
	 * Token.
	 * If for any reason, it is called at the end of the stream, when all
	 * tokens have already been iterated, return null
	 */
	@Override
	public Token next() {
//		System.out.println("Entered next now!"+currentPointer);
		// TODO YOU MUST IMPLEMENT THIS
		
		if(currentPointer>-1 && currentPointer<my_stream.size() && my_stream.get(currentPointer)==null)
			my_stream.remove(currentPointer);
		else
			++currentPointer;
		if(currentPointer>=my_stream.size())
			return null;
		else
			return my_stream.get(currentPointer);
	}
	

	 public Token previous() {

		// TODO YOU MUST IMPLEMENT THIS
		
		if(--currentPointer>-1)
			return my_stream.get(currentPointer);
		else
			return null;
	}
	
	/**
	 * Method to remove the current Token from the stream.
	 * Note that "current" token refers to the Token just returned
	 * by the next method. 
	 * Must thus be NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		// TODO YOU MUST IMPLEMENT THIS
		if(currentPointer>=0 && currentPointer<my_stream.size() )
			//my_stream.remove(currentPointer--);
			my_stream.set(currentPointer,null);
	}
	
	/**
	 * Method to reset the stream to bring the iterator back to the beginning
	 * of the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		//TODO : YOU MUST IMPLEMENT THIS
	//	currentPointer=(my_stream.size()>0)?0:-1;
		currentPointer=-1;
	}
	
	/* Method to add token next to the current pointer */
	public void add_next(Token t) {
		my_stream.add(++currentPointer, t);
	}
	
	public void replace(Token t){
		my_stream.set(currentPointer, t);
	}
	
	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the iterator
	 * currently stands. After appending, the iterator position must be unchanged
	 * Of course this means if the iterator was at the end of the stream and a 
	 * new stream was appended, the iterator hasn't moved but that is no longer
	 * the end of the stream.
	 * @param stream : The stream to be appended
	 */
	public void append(TokenStream stream) {
		//TODO : YOU MUST IMPLEMENT THIS
		
		if(stream==null)
			return;
		Token element;

		stream.reset();
		while(stream.hasNext())
		{
			element = stream.next();
			my_stream.add(element);
		}

	}
	
	/**
	 * Method to get the current Token from the stream without iteration.
	 * The only difference between this method and {@link TokenStream#next()} is that
	 * the latter moves the stream forward, this one does not.
	 * Calling this method multiple times would not alter the return value of {@link TokenStream#hasNext()}
	 * @return The current {@link Token} if one exists, null if end of stream
	 * has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		//TODO: YOU MUST IMPLEMENT THIS
		if(currentPointer>=0 && currentPointer<my_stream.size())
			return my_stream.get(currentPointer);
		else 
			return null;
	}
	
}
