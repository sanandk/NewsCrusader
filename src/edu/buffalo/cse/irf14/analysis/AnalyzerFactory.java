/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * This factory class is responsible for instantiating "chained" {@link Analyzer} instances
 */
public class AnalyzerFactory {
	static AnalyzerFactory ins=new AnalyzerFactory();
	/**
	 * Static method to return an instance of the factory class.
	 * Usually factory classes are defined as singletons, i.e. 
	 * only one instance of the class exists at any instance.
	 * This is usually achieved by defining a private static instance
	 * that is initialized by the "private" constructor.
	 * On the method being called, you return the static instance.
	 * This allows you to reuse expensive objects that you may create
	 * during instantiation
	 * @return An instance of the factory
	 */
	public static AnalyzerFactory getInstance() {
		//TODO: YOU NEED TO IMPLEMENT THIS METHOD
		return ins;
	}
	
	/**
	 * Returns a fully constructed and chained {@link Analyzer} instance
	 * for a given {@link FieldNames} field
	 * Note again that the singleton factory instance allows you to reuse
	 * {@link TokenFilter} instances if need be
	 * @param name: The {@link FieldNames} for which the {@link Analyzer}
	 * is requested
	 * @param TokenStream : Stream for which the Analyzer is requested
	 * @return The built {@link Analyzer} instance for an indexable {@link FieldNames}
	 * null otherwise
	 */
	public Analyzer getAnalyzerForField(FieldNames name, TokenStream stream) {
		//TODO : YOU NEED TO IMPLEMENT THIS METHOD
		ArrayList<Analyzer> list=new ArrayList<Analyzer>();
		TokenFilterFactory factory = TokenFilterFactory.getInstance();
		TokenFilter AccentFilterObject = factory.getFilterByType(TokenFilterType.ACCENT, stream);
		TokenFilter SymbolFilterObject = factory.getFilterByType(TokenFilterType.SYMBOL, stream);
		TokenFilter CapitalizationFilterObject = factory.getFilterByType(TokenFilterType.CAPITALIZATION, stream);
		TokenFilter StopwordFilterObject = factory.getFilterByType(TokenFilterType.STOPWORD, stream);
		TokenFilter StemmerFilterObject = factory.getFilterByType(TokenFilterType.STEMMER, stream);
		TokenFilter DateFilterObject = factory.getFilterByType(TokenFilterType.DATE, stream);
		TokenFilter SpecialCharFilterObject = factory.getFilterByType(TokenFilterType.SPECIALCHARS, stream);
		TokenFilter NumberFilterObject = factory.getFilterByType(TokenFilterType.NUMERIC, stream);
		
		if(name==FieldNames.CONTENT){
			
			list.add(AccentFilterObject);
			list.add(DateFilterObject);
			list.add(SymbolFilterObject);
			list.add(SpecialCharFilterObject);
			list.add(NumberFilterObject);
		
			list.add(CapitalizationFilterObject);
			list.add(StopwordFilterObject);
			list.add(StemmerFilterObject);
			
		}
		else if(name==FieldNames.AUTHOR || name==FieldNames.AUTHORORG)
		{
			list.add(CapitalizationFilterObject);
			list.add(StopwordFilterObject);
			list.add(AccentFilterObject);
			list.add(SymbolFilterObject);
			list.add(SpecialCharFilterObject);
			
		}
		else if(name==FieldNames.CATEGORY)
		{
			list.add(CapitalizationFilterObject);
			list.add(StopwordFilterObject);
			list.add(AccentFilterObject);
			list.add(SymbolFilterObject);
			list.add(SpecialCharFilterObject);
		}
		else if(name==FieldNames.NEWSDATE)
		{
			list.add(SymbolFilterObject);
			list.add(DateFilterObject);
			list.add(SpecialCharFilterObject);
		}
		else if(name==FieldNames.PLACE)
		{
			list.add(CapitalizationFilterObject);
			list.add(StopwordFilterObject);
			list.add(StemmerFilterObject);
			list.add(AccentFilterObject);
			list.add(SymbolFilterObject);
			list.add(SpecialCharFilterObject);
		}
		else if(name==FieldNames.TITLE)
		{
			list.add(CapitalizationFilterObject);
			list.add(AccentFilterObject);
			list.add(SymbolFilterObject);
			list.add(SpecialCharFilterObject);
		}
		ChainFilters cf=new ChainFilters(list, stream);
		return cf;
	}
}
