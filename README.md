# NewsCrusader

###Project Description

This project aims to build a fully functional News search engine with News Indexer with the following goals:
* Parse simple news articles with minimal markup
* Index a decent sized subset of the given news corpus.
* Create multiple indexes on the news articles as well as metadata.
* Provide an index introspection mechanism that can later be built upon to support queries.
*	Implement a query parser that can parse free text into structured query terms
*	Implement a querying mechanism that can ingest query terms and find matching documents
*	Implement two popular scoring mechanisms to rank retrieved documents
*	Implement one or more methods to improve search engine relevancy scores.

### Parser

This component is responsible for converting a given text file into a Document representation. A Document is nothing but a collection of fields. Each field can have its own indexing strategy that would be applied by the IndexWriter. More details are provided in the following section. 

###IndexWriter
Once a given file has been converted into a Document, the IndexWriter is responsible for writing the fields to the corresponding indexes and dictionaries. You are expected to implement the following indexes. Representative snapshots of the indexes and dictionaries follow.
* Term index: An index that maps different terms to documents. This is the standard index on which you would perform retrieval.
*	Author index: An author to document index, stores the different documents written by a given author.
*	Category index: A category to document index, stores the different documents classified by a given category.
*	Place index: A place to document index, stores the different documents as referenced by a given place.

##### Document dictionary
This contains a Document to document id mapping. This dictionary is optional as you may use the FileId as the document id. However, you may be able to achieve some compression benefits by assigning document ids manually.

#####	Term dictionary
Like above it contains a term to term id mapping

##### Term index
It is a simple inverted index storing the document postings list for each term.

##### Author dictionary & author index
Analogous to term dictionary and index; create a dictionary and index for authors. Note that if a document has multiple authors, the mapping must be stored against each author. The author organization if available, must be stored in the author dictionary.
##### Category dictionary & category index
Similar to term and author indexes, create a category dictionary and index.

#####Place dictionary & place index
Similar to term, author and category indexes, create a place dictionary and index.

###Runner
* It simply takes two inputs: the input directory and the index directory.
* 	It iterates over each file within the input directory, passes it to the Parser to convert it into a Document and then calls the addDocument method on IndexWriter.
*	After all files have been processed, it calls the close method

###	Query Parser
This component is responsible for converting a given raw string query into a Query representation.  A Query is nothing but an ordered collection of Clauses that are connected by Boolean operators. Refer to the grammar below:
```
Query ::= ( Clause )*
Clause ::= [<Index> “:”] ( <Term> | “(“ Query “)” )
Index ::= Term | Category | Author | Place
Term :: = Query term | “ Query phrase “
Query phrase ::= ( Query term ) *
Operators ::= AND | OR | NOT
```
Thus, all that the Query Parser does is parse the given user string and pass on the Query object to downstream classes for them to process.

###	 Index Searcher
As the name suggests, this component is responsible for consuming the given Query object and use it to search through the given Index to retrieve documents. This component would be responsible thus, to perform the following operations:
*	Determine the order of execution of different Clauses for a given Query
*	Evaluate each Clause by retrieving postings through the corresponding indices and intersecting them as necessary
*	Generate a final list of retrieved documents for the corresponding Query by evaluating all Clauses. 

###	Scorer
The set of documents retrieved by the Index Searcher need not be in any order. The Scorer is responsible for ranking the results based on their relevance to the query. Two different relevance models: tf-idf based vector similarity model and BM-25 or the Okapi probabilistic model. Thus, the Scorer works on a set of unranked documents and generates a set of ranked documents. 

##### SearchRunner
This is the main class used to interact with the searcher. Before we describe the methods, let us enumerate the different modes under which this class runs:
*	Query mode: The program works as query engine that runs in an infinite loop until terminated. It accepts user queries, validates them and displays search results (see details below). This mode is interactive and more user friendly. There is no strict standardization on how the output must be formatted in this case.
*	Evaluation mode: The program works as a barebones query engine that reads queries from a given file and logs the ranked results to a given file (see details below). This is a non-interactive mode and expects results to be formatted as specified for correct evaluations.

Irrespective of the mode, the searcher is expected to return at most 10 results.

##### Query mode
This mode has the least formatting requirements, but the following information is required to be printed:

* a.	Query: The user query as input
* b.	Query time: Time taken to execute the query, prepare results and print them in ms.
* c.	Result rank: Rank of a returned result (document) starting from 1.
* d.	Result title: The title of the news article
* e.	Result snippet: A short 2-3 line snippet from the news article indicating its relevance to the query.
* f.	Result relevancy: The relevancy score for the result.
* g.	Term highlighting (optional): If needed you can highlight query terms as found in the results using the HTML markup <b>…</b>

