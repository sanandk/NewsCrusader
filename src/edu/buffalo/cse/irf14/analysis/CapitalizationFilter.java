package edu.buffalo.cse.irf14.analysis;

public class CapitalizationFilter extends TokenFilter {

	TokenStream t_stream;
	// int lineStart;
	String camelCaseRegex = "([a-z]*[A-Z]+[a-z]*)+";

	public CapitalizationFilter(TokenStream stream) {
		// TODO Auto-generated constructor stub
		super(stream);
		t_stream = stream;
		// lineStart=0;
	}

	@Override
	public boolean increment() throws TokenizerException {
		Token current_token;
		Token previous_token;

		// if(t_stream.hasNext())
		// {
		previous_token = t_stream.getCurrent();
		t_stream.next();
		current_token = t_stream.getCurrent();
		if (null == current_token)
			return false;
		int k=0;
		if(current_token.getTermText().matches(camelCaseRegex) && !current_token.lineEnd && t_stream.hasNext()){
			
			do{
				current_token = t_stream.next();
				if (current_token.getTermText().matches(camelCaseRegex) ) {
					k++;
					t_stream.remove();
					previous_token = t_stream.previous();
	                current_token.setTermText(previous_token.getTermText()+" "+current_token.getTermText());
					t_stream.replace(current_token);
				}else{
					current_token=t_stream.previous();
					break;
				}
			}while(!current_token.lineEnd);
			
		}
		if(k==0 && (previous_token == null || previous_token.lineEnd) && current_token.getTermText().matches(camelCaseRegex)){
			// loop to check if not begining of line and captilization conditions
            current_token.setTermText(current_token.getTermText().toLowerCase());
			t_stream.replace(current_token);
		}
		
		
		
		
//        if (current_token.getTermText().matches(camelCaseRegex)) {
//
//			if (previous_token == null || previous_token.lineEnd) {
//				// loop to check if not begining of line and captilization
//				// conditions
//                current_token.setTermText(current_token.getTermText().toLowerCase());
//				t_stream.replace(current_token);
//			} else if (t_stream.hasNext()) {
//				current_token = t_stream.next();
//                if (current_token.getTermText().matches(camelCaseRegex)) {
//					t_stream.remove();
//					previous_token = t_stream.previous();
//                    current_token.setTermText(previous_token.getTermText()+" "+current_token.getTermText());
//
//					t_stream.replace(current_token);
//				} else {
//					t_stream.previous();
//				}
//			}
//		}

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
