package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CapitalizationFilter extends TokenFilter {

	
	// int lineStart;
	final String camelCaseRegex = "([a-z]*[A-Z]+[a-z]*)+";
	final String camelCaseRegex2 = "([a-z]*[A-Z]+[a-z]*[ ]*)+";
	final Pattern p = Pattern.compile(camelCaseRegex);
	final Pattern p2 = Pattern.compile(camelCaseRegex2);
	Matcher m1=null,m2=null;
	public CapitalizationFilter(TokenStream stream) {
		// TODO Auto-generated constructor stub
		super(stream);
		t_stream = stream;
        f_type=TokenFilterType.CAPITALIZATION;
	}

	@Override
	public boolean increment() throws TokenizerException {
		Token current_token;
		Token previous_token;

		previous_token = t_stream.getCurrent();
		t_stream.next();
		current_token = t_stream.getCurrent();
		if (null == current_token)
			return false;
		if(m1==null)
			m1 = p.matcher(current_token.getTermText());
		else
			m1.reset(current_token.getTermText());
		if (m1.matches()) {

			if (t_stream.currentPointer == 0 || (previous_token!=null && previous_token.lineEnd)) {
				// loop to check if not begining of line and captilization conditions
                current_token.setTermText(current_token.getTermText().toLowerCase());
				t_stream.replace(current_token);
			} else if (t_stream.hasNext()) {
				
				do{
					current_token = t_stream.next();
					if(null!=current_token)
						if(m2==null)
							m2=p2.matcher(current_token.getTermText());
						else
							m2.reset(current_token.getTermText());
					else
						m2=null;

                if (null!=m2 && m2.matches()) {
					t_stream.remove();
					previous_token = t_stream.previous();
                    current_token.setTermText(previous_token.getTermText()+" "+current_token.getTermText());
					t_stream.replace(current_token);
				} 
					else {
					t_stream.previous();
					break;
				}
				}while(true);
			}
		}

		if (t_stream.hasNext()) {
			return true;
		} else
			return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return t_stream;
	}

}
