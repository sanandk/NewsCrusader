package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

public class ChainFilters implements Analyzer{

	ArrayList<Analyzer> chain;
	private TokenStream stream;
	
	public ChainFilters(ArrayList<Analyzer> list,TokenStream s) {
		// TODO Auto-generated constructor stub
		chain=list;
		stream=s;
	}
	
		
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
		
		boolean next=false;
		int i=0;
		while(i<chain.size())
		{
			final TokenFilter a=(TokenFilter) chain.get(i);
			while(a.increment())
			{
			}
			stream.reset();
			i++;
		}

		return next;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return stream;
	}

}
