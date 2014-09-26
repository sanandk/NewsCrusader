package edu.buffalo.cse.irf14.analysis;

import java.sql.Time;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFilter  extends TokenFilter {
	// Update on 17th Sep by anand
	
	TokenStream t_stream;
	public DateFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
		t_stream=stream;
		f_type=TokenFilterType.DATE;
	}
	String intregex = "[012]?[0-9]:[0-5][0-9](:[0-5][0-9])?([a-zA-z.]{3})?";      
    Pattern intpattern = Pattern.compile(intregex);
	public static String Number(String string,boolean suffix) {
		int i; String ch="";
	    try {
	    	if(!Character.isDigit(string.charAt(string.length()-1)))
	    	{
	    		ch=string.charAt(string.length()-1)+"";
	    		string=string.substring(0,string.length()-1);
	    	}
	    	i=Integer.parseInt(string);
	    	String delim=(ch=="")?"":"^";
	    	if(!suffix && i>1800 && i<2100)
	    		return "~"+String.format("%04d", i)+delim+ch;
	    	if(i<32)
	    		return String.format("%02d", i);
	    	if(suffix)
	    		return "~"+String.format("%04d", i)+delim+ch;
	    	
	    	 return "";
	    } catch (Exception e) {
	       return "";
	        
	    }
	}
	
	public String month(String str)
	{
		int i=1;
		if(str!=null && !str.equals(""))
		for(String s:months)
		{
			if(s.contains(str) && s.charAt(0)==str.charAt(0))
				return (i<10)?"0"+i:i+"";
			i++;
		}
		return "";
	}
	
	public String year_or_date(String str)
	{
		if(str.contains("BC"))
			return "-"+Number(str.replaceAll("BC", ""),true);
		else if(str.contains("AD"))
			return Number(str.replaceAll("AD", ""),true);
		else
			return Number(str,false);
	}
	
	
	static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		 Token current_token,next;
		 ArrayList<String> time = null;
		 String ch_time = "";
		 int cnt=0;
		 String date="";
			current_token=t_stream.next();
			if(current_token==null)
				return false;
			String str=current_token.getTermText();
			Matcher matcher = intpattern.matcher(str);
	        boolean onlyNo = matcher.matches();
	        
	         
			
			if(t_stream.hasNext())
			{
				String y=t_stream.next().getTermText();
				if(y.contains("BC")){
					str=str+"BC";
					cnt++;
				}
				else if(str.contains("AD")){
					str=str+"BC";
					cnt++;
				}
				t_stream.previous();
			}
			String t1=year_or_date(str),t2 = "",t3="";
			
			if(!t1.equals("")) // DD MM YY
			{
			
				if(t_stream.hasNext())
				{
					next=t_stream.next();
					t2=month(next.toString());
					
					if(!t2.equals("") && t_stream.hasNext())
					{
						next=t_stream.next();
						t_stream.previous();
						cnt++;
						t3=year_or_date(next.toString());
					}
					t_stream.previous();
				}
				if(t2.equals("") && t3.equals("") && !t1.contains("~"))
					date="~0000";
				if(t2.equals(""))
					t2="01";				
				if(t3.contains("~")) //t3=year
				{
					t1=(t1.equals(""))?"01":t1;
					date=t3.replaceAll("~", "")+t2+t1;
					cnt++;
				}
				else if(t1.contains("~")) //t1=year
				{
					t3=(t3.equals(""))?"01":t3;
					if(t1.startsWith("~00"))
						date="";
					else
						date=t1.replaceAll("~", "")+t2+t3;
				}
				else
				{
					t3=(t3.equals(""))?"1900":t3;	
					date=t3+t2+t1;
				}
				
			}
			else if(str.contains("-")) // for 2011-12
			{
				String[] yr=str.split("-");
				String re;
				int range=0,i=0;
				for(String s:yr)
				{
					try{
						if(i>0 && s.length()<4){
							s=yr[i-1].substring(0,2)+s;
						}
					}catch(Exception e){
//						System.out.println("Datefilter error"+yr[i-1]);
					}
							
						t1=year_or_date(s);
						if(i!=0 && t1!=""){
							date+="-"+t1.replaceAll("~", "")+"01"+"01";
							cnt++;
						}
						else if(t1!="")
							date+=t1.replaceAll("~", "")+"01"+"01";
						
				
					i++;
				}
				
			}		
			else if(onlyNo && str.contains(":") && str.length()>1)
			{
				int ap=-1;
				if(str.contains("PM")){
					ap=2;
					str=str.replaceAll("PM", "");
				}
				else if(str.contains("pm")){
					ap=2;
					str=str.replaceAll("pm", "");
				}
				else if(str.contains("AM")){
					ap=1;
					str=str.replaceAll("AM", "");
				}
				else if(str.contains("am")){
					ap=1;
					str=str.replaceAll("am", "");
				}
				else
				{
					if(t_stream.hasNext())
					{
						String y=t_stream.next().getTermText();
						if(y.contains("PM")){
							ap=2;
							cnt++;
						}
						else if(y.contains("pm")){
							ap=2;
							cnt++;
						}
						else if(y.contains("AM"))
						{
							ap=1;
							cnt++;
						}
						else if(y.contains("am"))
						{
							ap=1;
							cnt++;
						}

						if(y.length()>1 && !Character.isDigit(y.charAt(y.length()-1)))
							ch_time=y.charAt(y.length()-1)+"";
				    	
				    	
						t_stream.previous();
					}
				}
				if(!Character.isDigit(str.charAt(str.length()-1)))
				{
					ch_time=str.charAt(str.length()-1)+"";
					str=str.substring(0,str.length()-1);
				}
		    	
				String [] tt=str.split(":");
				time=new ArrayList<String>();
				int i=0;
				for(String s:tt){
					if(i==0 && ap==2)
					{
						ap=Integer.parseInt(s)+12;
						time.add(ap+"");
					}
					else
						time.add(s);
				}
				
			}
			else // MM DD YY
			{
				t2=month(str);
				if(!t2.equals(""))
				{
					if(t_stream.hasNext()){
					next=t_stream.next();
					t1=year_or_date(next.toString());
					
					if(!t1.equals("") && t_stream.hasNext())
					{
						next=t_stream.next();
						t3=year_or_date(next.toString());
						t_stream.previous();
						cnt++;
					}
					t_stream.previous();
					
					}					
					if(t3.contains("~"))
					{
						t1=(t1.equals(""))?"01":t1;
						date=t3.replaceAll("~", "")+t2+t1;
						cnt++;
					}
					else if(t1.contains("~"))
					{
						if(t3.equals(""))
							t3="01";
						else
							cnt++;
						date=t1.replaceAll("~", "")+t2+t3;
					}
					else
					{
						t3=(t3.equals(""))?"1900":t3;	
						t1=(t1.equals(""))?"01":t1;
						date=t3+t2+t1;
					}
					
				}
				
			}
			String delim="^";
			int di=date.indexOf(delim);
			String ch="";
			while(di>0)
			{
				ch=date.charAt(di+1)+"";
				date=date.substring(0, di)+date.substring(di+2, date.length());
				
				di=date.indexOf(delim);
			}
			date+=ch;
			int i=0;
			if(time!=null)
			{
				for(String s:time)
				{
					date+=s+":";
					i++;
				}
				while(i>0 && i<3)
				{
					date+="00";
					if(++i<3)
						date+=":";
				}
				date+=ch_time;
			}
			
			if(!date.equals(""))
			{
			ChainFilters.change=true;
			t_stream.replace(new Token(date));
			}
			while(cnt>0)
			{
				t_stream.next();
				t_stream.remove();
				cnt--;
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
