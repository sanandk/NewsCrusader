package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StemmerFilter extends TokenFilter {

	
	Stemmer stemmer=new Stemmer();

	public StemmerFilter(TokenStream stream) {
		// TODO Auto-generated constructor stub
		super(stream);
		f_type=TokenFilterType.STEMMER;
		t_stream = stream;
	}
	final Pattern alphap= Pattern.compile("[a-zA-Z]+");
	Matcher m;
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
			m=alphap.matcher(str);
			if (m.matches()) {
			char[] w= str.toCharArray();
		
			int j= w.length;
			for (int c = 0; c < j; c++) stemmer.add(w[c]);

			stemmer.stem();
			
			String res=stemmer.toString();
			if(!str.equals(res))
			{
			current_token.setTermText(res);
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
