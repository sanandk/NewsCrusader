package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

public class ChainFilters implements Analyzer{

	ArrayList<Analyzer> chain;
	TokenStream stream;
	public ChainFilters(ArrayList<Analyzer> list,TokenStream s) {
		// TODO Auto-generated constructor stub
		chain=list;
		stream=s;
	}
	
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		boolean next=false;
		for(Analyzer a:chain)
		{
			next=a.increment();
		}
		return next;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return stream;
	}

}
