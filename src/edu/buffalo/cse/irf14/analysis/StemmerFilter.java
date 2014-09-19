package edu.buffalo.cse.irf14.analysis;

public class StemmerFilter extends TokenFilter {
	
	TokenStream t_stream;
	
	public StemmerFilter(TokenStream stream) {
		// TODO Auto-generated constructor stub
		super(stream);
		t_stream=stream;
	}
	
	@Override
	public boolean increment() throws TokenizerException {
	//Code added by Karthik-j on Sept 18,2014
//		Porter Alogorithm Reference site: http://snowball.tartarus.org/algorithms/english/stemmer.html
		Token current_token;
		
		if(t_stream.hasNext())
		{
			t_stream.next();
			current_token= t_stream.getCurrent();
			if(null==current_token)
				return false;
//			if(){
//				
//			}
			return true;
		}else{
			return false;
		}
		
	}
	
	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return t_stream;
	}
	
}
