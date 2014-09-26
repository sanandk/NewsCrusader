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
	
	public static boolean change=false;
	int j=-1;
	
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
		
		boolean next=false;
		int i=0;
		while(i<chain.size())
		{
			if(i==0)
				change=false;
			TokenFilter a=(TokenFilter) chain.get(i);
			while(a.increment())
			{
				
			}
			if(i<chain.size()-1)
			stream.reset();
			i++;
			/*
					next=a.increment();
					stream.previous();
					
			
			
			if(change)
			{
				j=i;
				i=0;
			}
			if(i==j)
				j=-1;*/
		}
	//	if(next!=false)
	//		stream.next();
		return next;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return stream;
	}

}
