package edu.buffalo.cse.irf14.analysis;

import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolFilter extends TokenFilter {
	// Updated by anand on Sep 15
	// Fixed after changes made on 17th Sep
	
	TokenStream t_stream;
	public SymbolFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
		
		t_stream=stream;
	}
	String intregex = "(.)*(\\d)(.)*";      
    Pattern intpattern = Pattern.compile(intregex);
    
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		
		 char[] punctuations={'.',',',':',';','?','!','-','\''};
		 Token current_token;
	
		 current_token=t_stream.next();
		
		
		if(current_token==null)
			return false;
		String str=current_token.toString();
		if(str.length()>0)
		{
			
		char f=str.charAt(0);
		char l=str.charAt(str.length()-1);
		String ps=new String(punctuations);
		
		if(str.length()>=1)
		{
			while(f==l && ps.indexOf(l)>=0)
			{
				if(str.length()<=2)
				{	
					str="";
					l=0;
					break;
				}
					str=str.substring(1,str.length()-1);
					f=str.charAt(0);
					l=str.charAt(str.length()-1);
			}
			while(ps.indexOf(l)>=0)
			{
				//if(l=='\'' && str.contains("s\'"))
					//str=str.replaceAll("s\'", "s");	
				//else
				str=str.substring(0,str.length()-1);
				l=str.charAt(str.length()-1);
			}
			if(str.contains("\'")) // Handling apostrophe
			{
				if(str.contains("let\'s"))
					str=str.replaceAll("let\'s", "let us");
				if(str.contains("\'s"))
					str=str.replaceAll("\'s", "");
				if(str.contains("\'ve"))
					str=str.replaceAll("\'ve", " have");
				if(str.contains("\'d"))
					str=str.replaceAll("\'d", " would");
				if(str.contains("\'ll"))
					str=str.replaceAll("\'ll", " will");
				if(str.contains("won\'t"))
					str=str.replaceAll("won\'t", "will not");
				if(str.contains("ain\'t"))
					str=str.replaceAll("ai\'t", "am not");
				if(str.contains("shan\'t"))
					str=str.replaceAll("shan\'t", "shall not");
				if(str.contains("\'re"))
					str=str.replaceAll("\'re", " are");
				if(str.contains("\'m"))
					str=str.replaceAll("\'m", " am");
				if(str.contains("\'em"))
					str=str.replaceAll("\'em", "them");
				
				if(str.contains("n\'t"))
					str=str.replaceAll("n\'t", " not");
				if(str.contains("o\' clock"))
					str=str.replaceAll("\'ve", " of the clock");
			}
	
			int in=str.indexOf('\'');
			while(in>=0)
			{
				if(str.length()<=1)
				{
					str="";
					break;
				}
				if(in==0)
					str=str.substring(1,str.length());
				else
					str=str.substring(0, in)+str.substring(in+1,str.length());
				in=str.indexOf('\'');
			}
			if(str.contains("-"))
			{
			 	 Matcher matcher = intpattern.matcher(str);
		         boolean isMatched = matcher.matches();
		        
		         if (!isMatched)
		         {
		        	String [] sp=str.split(String.valueOf("-"));
		        	String alphapattern= "[a-zA-Z]+";
		        	int char_flag=0,h_flag=0;
		        	for(int i=0;i<sp.length;i++)
		        	{
		        		if(sp[i].matches(alphapattern))
		        			char_flag++;
		        		else
		        			h_flag++;
		        	}
		            if((char_flag-h_flag)>1)
		            {	
		            	str=str.replaceAll("-", " ");
		            	t_stream.replace(new Token(str));
		            }
		            else
		            {
		            	while(str.contains("-"))
		            		str=str.replaceAll("-", "");
		     
		            }
		         }
			}
			t_stream.replace(new Token(str));
		} 
		else
			t_stream.remove();
		
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
