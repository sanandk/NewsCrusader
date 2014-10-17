package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialCharFilter  extends TokenFilter {
	// Update on 17th Sep by anand
	
	
	public SpecialCharFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
		t_stream=stream;
		f_type=TokenFilterType.SPECIALCHARS;
	}
	
	final Pattern intpattern = Pattern.compile("(.)*(\\d)(.)*");
    Matcher matcher = null;
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
		 StringBuffer res = new StringBuffer();

			Token current_token=t_stream.next();
			if(current_token==null)
				return false;
			String str=current_token.getTermText();
			char [] buf=current_token.getTermBuffer();
			
			for(char a:buf)
			{
				if(Character.isLetterOrDigit(a) || a=='.')
					res.append(a);
				else if(a=='-')
				{
					if(matcher==null)
						matcher = intpattern.matcher(str);
					else
						matcher.reset(str);
			         boolean isMatched = matcher.matches();
			        
			        if(isMatched)
			        	 res=res.append(a);
				}
				else if(a==' ')
					res=res.append(a);
			}
			
			if(!(str.equals(res.toString())))
			{
				current_token.setTermText(res.toString());
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
