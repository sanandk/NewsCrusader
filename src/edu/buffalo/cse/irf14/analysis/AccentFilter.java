package edu.buffalo.cse.irf14.analysis;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class AccentFilter  extends TokenFilter {
	// Update on 17th Sep by anand
	
	TokenStream t_stream;
	public AccentFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
		t_stream=stream;
		f_type=TokenFilterType.ACCENT;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
		String[] accents={
					"\u00C0","\u00C1","\u00C2","\u00C3","\u00C4","\u00C5","\u00C6","\u00C7","\u00C8","\u00C9",
					"\u00CA","\u00CB","\u00CC","\u00CD","\u00CE","\u00CF","\u0132","\u00D0","\u00D1","\u00D2",
					"\u00D3","\u00D4","\u00D5","\u00D6","\u00D8","\u0152","\u00DE","\u00D9","\u00DA","\u00DB",
					"\u00DC","\u00DD","\u0178","\u00E0","\u00E1","\u00E2","\u00E3","\u00E4","\u00E5","\u00E6","\u00C7","\u00C8","\u00C9",
					"\u00EA","\u00EB","\u00EC","\u00ED","\u00EE","\u00EF","\u0133","\u00F0","\u00F1","\u00F2",
					"\u00F3","\u00F4","\u00F5","\u00F6","\u00F8","\u0153","\u00DF","\u00FE","\u00F9","\u00FA",
					"\u00FB","\u00FC","\u00FD","\u00FF","\uFB00","\uFB01","\uFB02","\uFB03","\uFB04","\uFB05","\uFB06"
		};
		String[] accents_val={
				"A","A","A","A","A","A","AE","C","E","E","E","E","I","I","I","I","IJ","D","N","O","O","O","O","O","O",
				"OE","TH","U","U","U","U","Y","Y","a","a","a","a","a","a","ae","c","e","e","e","e","i","i","i","i","ij",
				"d","n","o","o","o","o","o","o","oe","ss","th","u","u","u","u","y","y","ff","fi","fl","ffi","ffl","ft","st"
		};
			Token current_token=t_stream.next();
			if(current_token==null)
				return false;
			String str=current_token.toString();
			String old=str;
			int i=0;
			for(String a:accents)
			{
				str=str.replaceAll(a, accents_val[i++]);
			}
		   
		    str=Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			if(!(str.equals(old)))
			{
			current_token.setTermText(str);
		    t_stream.replace(current_token);
		    ChainFilters.change=true;
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
