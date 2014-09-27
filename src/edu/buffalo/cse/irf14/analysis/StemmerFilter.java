package edu.buffalo.cse.irf14.analysis;


public class StemmerFilter extends TokenFilter {

	TokenStream t_stream;

	Stemmer stemmer=new Stemmer();

	public StemmerFilter(TokenStream stream) {
		// TODO Auto-generated constructor stub
		super(stream);
		f_type=TokenFilterType.STEMMER;
		t_stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// Code added by Karthik-j on Sept 18,2014
		// Porter Alogorithm Reference
		// site:http://snowball.tartarus.org/algorithms/porter/stemmer.html
		Token current_token;


			current_token = t_stream.next();
			if (null == current_token)
				return false;
			String str = current_token.toString();
			if (str.matches("[a-zA-Z]+")) {
			char[] w= str.toCharArray();
		
			int j= w.length;
			for (int c = 0; c < j; c++) stemmer.add(w[c]);

			stemmer.stem();
			
			
			if(!str.equals(stemmer.toString()))
			{
			current_token.setTermText(stemmer.toString());
			t_stream.replace(current_token);
			
			}
			
			}
		
			if (t_stream.hasNext()) {	
			return true;
		} else {
			return false;
		}

	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return t_stream;
	}


}
