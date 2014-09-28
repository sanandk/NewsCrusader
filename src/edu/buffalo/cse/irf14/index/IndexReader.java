/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.util.ArrayList;
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
			break;
		case CATEGORY:
			break;
		case PLACE:
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
//		HashSet<Integer> uniquePostingList= new HashSet<Integer>();
		switch (type) { 
		case TERM:
			return (null!=IndexWriter.docList)?IndexWriter.docList.size():0;  //uniquePostingList.size();
//				if(IndexWriter.termIndexAC!=null)
//					for (String key : IndexWriter.termIndexAC.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexAC.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
//				
//				if(IndexWriter.termIndexDG!=null)
//					for (String key : IndexWriter.termIndexDG.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexDG.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
//				if(null!=IndexWriter.termIndexHK)
//					for (String key : IndexWriter.termIndexHK.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexHK.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
//				if(null!=IndexWriter.termIndexLP)
//					for (String key : IndexWriter.termIndexLP.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexLP.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
//				if(null!=IndexWriter.termIndexQS)
//					for (String key : IndexWriter.termIndexQS.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexQS.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
//				if(null!=IndexWriter.termIndexTZ)
//					for (String key : IndexWriter.termIndexTZ.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexTZ.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
//				if(null!=IndexWriter.termIndexMisc)
//					for (String key : IndexWriter.termIndexMisc.keySet()) {
//						for(Integer fileId:IndexWriter.termIndexMisc.get(key).keySet()){
//							uniquePostingList.add(fileId);
//						}
//				    }
				
		case AUTHOR:
			break;
		case CATEGORY:
			break;
		case PLACE:
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
		HashMap<Integer, Integer> postingList;
		LinkedList<String[]> docList;
		String fileId;
		if(null!=term && !term.isEmpty()){
			char termStart= term.toLowerCase().charAt(0);
			switch(termStart){
			case 'a': case 'b': case 'c':
				postingList=IndexWriter.termIndexAC.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
				}
				break;
			case 'd': case 'e': case 'f': case 'g':
				postingList=IndexWriter.termIndexDG.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
				}
				break;
			case 'h': case 'i': case 'j': case 'k':
				postingList=IndexWriter.termIndexHK.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
				}
				break;
			case 'l': case 'm': case 'n': case 'o': case 'p':
				postingList=IndexWriter.termIndexLP.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
				}
				break;
			case 'q': case 'r': case 's':
				postingList=IndexWriter.termIndexQS.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
				}
				break;
			case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
				postingList=IndexWriter.termIndexTZ.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
				}
				break;
			default :
				postingList=IndexWriter.termIndexMisc.get(term);
				if(null!=postingList){
					postingsMap= new HashMap<String, Integer>();
					for(Integer docId: postingList.keySet()){
						postingsMap.put(IndexWriter.docCatList.get(docId), postingList.get(docId));
					}
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
		HashMap<Integer, Integer> postingList;
		HashMap<String,Integer> frequencyListing= new HashMap<String, Integer>();
		List<String> topKterms= new ArrayList<String>();
		if(k>0){
		switch (type) {
			case TERM:
				List<TreeMap<String, HashMap<Integer, Integer>>> IndexList= new LinkedList<TreeMap<String,HashMap<Integer,Integer>>>();
				IndexList.add(IndexWriter.termIndexAC);IndexList.add(IndexWriter.termIndexDG);IndexList.add(IndexWriter.termIndexHK);
				IndexList.add(IndexWriter.termIndexLP);IndexList.add(IndexWriter.termIndexQS);IndexList.add(IndexWriter.termIndexTZ);
				IndexList.add(IndexWriter.termIndexMisc);
				for(TreeMap<String, HashMap<Integer, Integer>> index: IndexList){
					for(String term: index.keySet()){
						postingList=index.get(term);
						frequency=0;
						for(Integer i: postingList.keySet()){
							frequency+=postingList.get(i);
						}
						frequencyListing.put(term, frequency);
					}
				}
				List<Entry<String, Integer>> sortedFrequencyList= entriesComparator(frequencyListing);
				for(int i=0;i<k;i++){
					topKterms.add(sortedFrequencyList.get(i).getKey());
//					System.out.println(sortedFrequencyList.get(i).getKey()+"==>"+sortedFrequencyList.get(i).getValue());
				}
				return topKterms;
				
			case AUTHOR:
				break;
			case CATEGORY:
				break;
			case PLACE:
				
//				return topKterms;
		}
			return null;
		}
		else{
			return null;
		}
	}

	

	 
	static <K,V extends Comparable<? super V>> List<Entry<K, V>> entriesComparator(Map<K,V> map) {
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
		return null;
	}

	public void readIndex(IndexType type) {
		FileUtilities.readDocDic();
		switch (type) {
		case TERM:
			IndexWriter.termIndexAC = FileUtilities.readIndexFile(FileUtilities.indexAC);
			IndexWriter.termIndexDG = FileUtilities.readIndexFile(FileUtilities.indexDG);
			IndexWriter.termIndexHK = FileUtilities.readIndexFile(FileUtilities.indexHK);
			IndexWriter.termIndexLP = FileUtilities.readIndexFile(FileUtilities.indexLP);
			IndexWriter.termIndexQS = FileUtilities.readIndexFile(FileUtilities.indexQS);
			IndexWriter.termIndexTZ = FileUtilities.readIndexFile(FileUtilities.indexTZ);
			IndexWriter.termIndexMisc = FileUtilities.readIndexFile(FileUtilities.indexMisc);
			break;
		case AUTHOR:
			break;
		case CATEGORY:
			break;
		case PLACE:

		}
	}
	
//	public void inverseMap(Map<Integer, String> map){
//	Map<String, Integer> inverted = new HashMap<String, Integer>();
//	for (Integer i : map.keySet())
//	    inverted.put(map.get(i), i);
//	}
}
