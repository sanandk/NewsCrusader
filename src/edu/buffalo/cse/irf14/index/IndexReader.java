/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author nikhillo Class that emulates reading data back from a written index
 */
public class IndexReader {

	String indexDir;
	IndexType type;

	/**
	 * Default constructor
	 * 
	 * @param indexDir
	 *            : The root directory from which the index is to be read. This
	 *            will be exactly the same directory as passed on IndexWriter.
	 *            In case you make subdirectories etc., you will have to handle
	 *            it accordingly.
	 * @param type
	 *            The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		// TODO
		this.indexDir = indexDir;
		this.type = type;
		FileUtilities.setOutputDir(indexDir);
		readIndex(type);
		

	}

	/**
	 * Get total number of terms from the "key" dictionary associated with this
	 * index. A postings list is always created against the "key" dictionary
	 * 
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		// TODO : YOU MUST IMPLEMENT THIS
		int totalKeyTerms=0;
		switch (type) {
			case TERM:
				if(null!=IndexWriter.termIndexAC)
					totalKeyTerms += IndexWriter.termIndexAC.size();
				if(null!=IndexWriter.termIndexDG)
					totalKeyTerms += IndexWriter.termIndexDG.size();
				if(null!=IndexWriter.termIndexHK)
					totalKeyTerms += IndexWriter.termIndexHK.size();
				if(null!=IndexWriter.termIndexLP)
					totalKeyTerms += IndexWriter.termIndexLP.size();
				if(null!=IndexWriter.termIndexQS)
					totalKeyTerms += IndexWriter.termIndexQS.size();
				if(null!=IndexWriter.termIndexTZ)
					totalKeyTerms += IndexWriter.termIndexTZ.size();
				if(null!=IndexWriter.termIndexMisc)
					totalKeyTerms += IndexWriter.termIndexMisc.size();
				return totalKeyTerms;
			case AUTHOR:
				if(IndexWriter.AuthorIndex!=null)
					totalKeyTerms=IndexWriter.AuthorIndex.size();
				return totalKeyTerms;
			case CATEGORY:
				if(IndexWriter.CatIndex!=null)
					totalKeyTerms=IndexWriter.CatIndex.size();
				return totalKeyTerms;
			case PLACE:
				if(IndexWriter.PlaceIndex!=null)
					totalKeyTerms=IndexWriter.PlaceIndex.size();
				return totalKeyTerms;
		}
		return -1;
	}

	/**
	 * Get total number of terms from the "value" dictionary associated with
	 * this index. A postings list is always created with the "value" dictionary
	 * 
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		// TODO: YOU MUST IMPLEMENT THIS
//		int totalValueTerms=0;
		HashSet<Integer> uniquePostingList= new HashSet<Integer>();
		switch (type) { 
			case TERM:
				return (null!=IndexWriter.docList)?IndexWriter.docList.size():0;  //uniquePostingList.size();
			case AUTHOR:
				if(IndexWriter.AuthorIndex!=null && !IndexWriter.AuthorIndex.isEmpty()){
					for (String key : IndexWriter.AuthorIndex.keySet()) {
						for(Integer fileId:IndexWriter.AuthorIndex.get(key)){
							uniquePostingList.add(fileId);
						}
				    }
				}
				return uniquePostingList.size();
				
			case CATEGORY:
				if(IndexWriter.AuthorIndex!=null && !IndexWriter.AuthorIndex.isEmpty()){
					for (String key : IndexWriter.AuthorIndex.keySet()) {
						for(Integer fileId:IndexWriter.AuthorIndex.get(key)){
							uniquePostingList.add(fileId);
						}
				    }
				}
				return uniquePostingList.size();
				
			case PLACE:
				if(IndexWriter.AuthorIndex!=null && !IndexWriter.AuthorIndex.isEmpty()){
					for (String key : IndexWriter.AuthorIndex.keySet()) {
						for(Integer fileId:IndexWriter.AuthorIndex.get(key)){
							uniquePostingList.add(fileId);
						}
				    }
				}
				return uniquePostingList.size();
				
		}
		return -1;
	}

	/**
	 * Method to get the postings for a given term. You can assume that the raw
	 * string that is used to query would be passed through the same Analyzer as
	 * the original field would have been.
	 * 
	 * @param term
	 *            : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the
	 *         number of occurrences as values if the given term was found, null
	 *         otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		// TODO:YOU MUST IMPLEMENT THIS
		Map<String, Integer> postingsMap=null;
		HashMap<Integer, Double> postingList;
		ArrayList<Integer> postingArray;
		LinkedList<String[]> docList;
		String fileId;
		switch(type){
			case TERM:
				if(null!=term && !term.isEmpty()){
					char termStart= term.toLowerCase().charAt(0);
					switch(termStart){
					case 'a': case 'b': case 'c':
						postingList=IndexWriter.termIndexAC.get(term);
						
						break;
					case 'd': case 'e': case 'f': case 'g':
						postingList=IndexWriter.termIndexDG.get(term);
						
						break;
					case 'h': case 'i': case 'j': case 'k':
						postingList=IndexWriter.termIndexHK.get(term);
						
						break;
					case 'l': case 'm': case 'n': case 'o': case 'p':
						postingList=IndexWriter.termIndexLP.get(term);
						
						break;
					case 'q': case 'r': case 's':
						postingList=IndexWriter.termIndexQS.get(term);
						
						break;
					case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
						postingList=IndexWriter.termIndexTZ.get(term);
						
						break;
					default :
						postingList=IndexWriter.termIndexMisc.get(term);
						
					}
					
					if(null!=postingList){
						postingsMap= new HashMap<String, Integer>();
						for(Integer docId: postingList.keySet()){
							postingsMap.put(IndexWriter.docCatList.get(docId)[0], postingList.get(docId).intValue());
						}
					}
				}
				
			case AUTHOR:
				postingArray=IndexWriter.AuthorIndex.get(term);
				if(postingArray!=null){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingArray){
						postingsMap.put(IndexWriter.docCatList.get(docId)[0], 1);
					}
				}
			case CATEGORY:
				postingArray=IndexWriter.CatIndex.get(term);
				if(postingArray!=null){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingArray){
						postingsMap.put(IndexWriter.docCatList.get(docId)[0], 1);
					}
				}
			case PLACE:
				postingArray=IndexWriter.PlaceIndex.get(term);
				if(postingArray!=null){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingArray){
						postingsMap.put(IndexWriter.docCatList.get(docId)[0], 1);
					}
				}
		}
		return postingsMap;
	}

	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * 
	 * @param k
	 *            : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values null
	 *         for invalid k values
	 */
	public List<String> getTopK(int k) {
		// TODO YOU MUST IMPLEMENT THIS
		int frequency=0;
		HashMap<Integer, Double> postingList;
		HashMap<String,Integer> frequencyListing= new HashMap<String, Integer>();
		List<Entry<String, Integer>> sortedFrequencyList;
		List<String> topKterms= null;
		if(k>0){
		switch (type) {
			case TERM:
				List<TreeMap<String, HashMap<Integer, Double>>> IndexList= new LinkedList<TreeMap<String,HashMap<Integer,Double>>>();
				IndexList.add(IndexWriter.termIndexAC);IndexList.add(IndexWriter.termIndexDG);IndexList.add(IndexWriter.termIndexHK);
				IndexList.add(IndexWriter.termIndexLP);IndexList.add(IndexWriter.termIndexQS);IndexList.add(IndexWriter.termIndexTZ);
				IndexList.add(IndexWriter.termIndexMisc);
				for(TreeMap<String, HashMap<Integer, Double>> index: IndexList){
					for(String term: index.keySet()){
						postingList=index.get(term);
						frequency=0;
						for(Integer i: postingList.keySet()){
							frequency+=postingList.get(i);
						}
						frequencyListing.put(term, frequency);
					}
				}
			case AUTHOR:
				for(String cat: IndexWriter.AuthorIndex.keySet()){
					frequency=0;
					ArrayList<Integer> postingArray = IndexWriter.AuthorIndex.get(cat);
					if(postingArray!=null){
						frequency=postingArray.size();
					}
					frequencyListing.put(cat, frequency);
				}
			case CATEGORY:
				for(String cat: IndexWriter.CatIndex.keySet()){
					frequency=0;
					ArrayList<Integer> postingArray = IndexWriter.CatIndex.get(cat);
					if(postingArray!=null){
						frequency=postingArray.size();
					}
					frequencyListing.put(cat, frequency);
				}
			case PLACE:
				for(String cat: IndexWriter.PlaceIndex.keySet()){
					frequency=0;
					ArrayList<Integer> postingArray = IndexWriter.PlaceIndex.get(cat);
					if(postingArray!=null){
						frequency=postingArray.size();
					}
					frequencyListing.put(cat, frequency);
				}
		}
		sortedFrequencyList= entriesComparator(frequencyListing);
		if(k>sortedFrequencyList.size()){
			k=sortedFrequencyList.size();
		}
		topKterms=new ArrayList<String>();
		for(int i=0;i<k;i++){
			topKterms.add(sortedFrequencyList.get(i).getKey());
		}
			return topKterms;
//			return null;
		}
		else{
			return null;
		}
	}

	

	 
	public static <K,V extends Comparable<? super V>> List<Entry<K, V>> entriesComparator(Map<K,V> map) {
		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());
		Collections.sort(sortedEntries,new Comparator<Entry<K,V>>() {
	        @Override
		    public int compare(Entry<K,V> key1, Entry<K,V> key2) {
		            return key2.getValue().compareTo(key1.getValue());
	        }
	    });
		return sortedEntries;
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * 
	 * @param terms
	 *            The ordered set of terms to AND, similar to getPostings() the
	 *            terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key and
	 *         number of occurrences as the value, the number of occurrences
	 *         would be the sum of occurrences for each participating term.
	 *         return null if the given term list returns no results BONUS ONLY
	 */
	public Map<String, Integer> query(String... terms) {
		// TODO : BONUS ONLY
		try{
		Map<String,Integer> queryMap= new HashMap<String,Integer>();
		Map<Integer,Double> queryWithDocId;
		ArrayList<HashMap<Integer, Double>> postingListArray=new ArrayList<HashMap<Integer,Double>>();
		String fileName;
		int i=0;
		for(String term: terms){
			i++;
			char termStart= term.toLowerCase().charAt(0);
			HashMap<Integer, Double> postingList;
			
			switch(termStart){
			case 'a': case 'b': case 'c':
				postingList=IndexWriter.termIndexAC.get(term);
				break;
			case 'd': case 'e': case 'f': case 'g':
				postingList=IndexWriter.termIndexDG.get(term);
				break;
			case 'h': case 'i': case 'j': case 'k':
				postingList=IndexWriter.termIndexHK.get(term);
				break;
			case 'l': case 'm': case 'n': case 'o': case 'p':
				postingList=IndexWriter.termIndexLP.get(term);
				break;
			case 'q': case 'r': case 's':
				postingList=IndexWriter.termIndexQS.get(term);
				break;
			case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
				postingList=IndexWriter.termIndexTZ.get(term);
				break;
			default :
				postingList=IndexWriter.termIndexMisc.get(term);
			}
			if(null!=postingList)
				postingListArray.add(postingList);
			
		}
		
		queryWithDocId=intersect(postingListArray.subList(0, i).toArray(new HashMap[i-1]));
		for( Integer docId: queryWithDocId.keySet()){
			queryMap.put(IndexWriter.docCatList.get(docId)[0],queryWithDocId.get(docId).intValue());
		}
		
		
		
		return (queryMap.size()>0)?queryMap:null;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	
	private Map<Integer, Double> intersect(HashMap<Integer, Double>...hashMaps) {
		HashMap<Integer, Double> main = new HashMap<Integer, Double>(hashMaps[0]);
		
		int len = hashMaps.length,key;
		double value;
		for (int i = 1; i < len; i++) {
			main.keySet().retainAll(hashMaps[i].keySet());
			
			for (Entry<Integer, Double> etr : hashMaps[i].entrySet()) {
				key = etr.getKey();
				value = etr.getValue();
				
				if (main.containsKey(key)) {
					main.put(key, main.get(key) + value);
				}
			}
		}
		
		return main;
	}

	@SuppressWarnings("unchecked")
	public void readIndex(IndexType type) {
		FileUtilities.readDocDic();
		switch (type) {
		case TERM:
			IndexWriter.termIndexAC = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexAC);
			IndexWriter.termIndexDG = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexDG);
			IndexWriter.termIndexHK = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexHK);
			IndexWriter.termIndexLP = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexLP);
			IndexWriter.termIndexQS = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexQS);
			IndexWriter.termIndexTZ = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexTZ);
			IndexWriter.termIndexMisc = (TreeMap<String, HashMap<Integer,Double>>) FileUtilities.readIndexFile(FileUtilities.indexMisc);
			break;
		case AUTHOR:
			IndexWriter.AuthorIndex=(TreeMap<String, ArrayList<Integer>>) FileUtilities.readIndexFile(FileUtilities.indexAuth);
			break;
		case CATEGORY:
			IndexWriter.CatIndex=(TreeMap<String, ArrayList<Integer>>) FileUtilities.readIndexFile(FileUtilities.indexCat);
			break;
		case PLACE:
			IndexWriter.PlaceIndex=(TreeMap<String, ArrayList<Integer>>) FileUtilities.readIndexFile(FileUtilities.indexPlace);
		}
	}
	
}
