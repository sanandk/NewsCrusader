package edu.buffalo.cse.irf14.analysis;


import java.text.Normalizer;


public class AccentFilter  extends TokenFilter {
	// Update on 17th Sep by anand
	
	
	public AccentFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
		t_stream=stream;
		f_type=TokenFilterType.ACCENT;
	}
	char c;
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
			Token current_token=t_stream.next();
			if(current_token==null)
				return false;
			String str=current_token.toString();
			final String old=str;	   
	
			char[] out = new char[str.length()];
		    str = Normalizer.normalize(str, Normalizer.Form.NFD);
		    int j = 0;
		    for (int i = 0, n = str.length(); i < n; ++i) {
		         c= str.charAt(i);
		        if (c <= '\u007F') out[j++] = c;
		    }
		    str= new String(out);
		    
			if(!(str.equals(old)))
			{
			current_token.setTermText(str);
		    t_stream.replace(current_token);
			}
			if(t_stream.hasNext())
				return true;
			else
				return false;

	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return t_stream;
	}

}
