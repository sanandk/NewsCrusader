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
		f_type=TokenFilterType.SYMBOL;
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
		String old=str;
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
				if(str.length()>0)
					str=str.substring(0,str.length()-1);
				if(str.length()>0)
					l=str.charAt(str.length()-1);
				else
					break;
			}
			if(str.contains("\'")) // Handling apostrophe
			{
					str=str.replaceAll("let\'s", "let us");
					str=str.replaceAll("\'s", "");
					str=str.replaceAll("\'ve", " have");
					str=str.replaceAll("\'d", " would");
					str=str.replaceAll("\'ll", " will");
					str=str.replaceAll("won\'t", "will not");
					str=str.replaceAll("ai\'t", "am not");
					str=str.replaceAll("shan\'t", "shall not");
					str=str.replaceAll("\'re", " are");
					str=str.replaceAll("\'m", " am");
					str=str.replaceAll("\'em", "them");
					str=str.replaceAll("n\'t", " not");
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
		            	str=str.replaceAll("-", " ");
		            else
		            {
		            	while(str.contains("-"))
		            		str=str.replaceAll("-", "");
		     
		            }
		         }
			}
			
		} 
		
		}
		if(str.length()<1){
			t_stream.remove();
			
		}
		if(!str.equals(old))
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
