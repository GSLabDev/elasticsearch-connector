# Search for the text from particular dataset

### Introduction
Elasticsearch is preferred when there is a need of doing a lot of text search, where traditional RDBMS databases are not performing well (poor configuration, acts as a black-box, poor performance). Elasticsearch is highly customizable, extendable through plugins. 

If data is not available on Elasticsearch, please refer the use case: ***Elasticsearch as a Document Store*** to insert the data in Elasticsearch using bulk operation.

This usecase demonstrates different search queries like match-query, multi-match-query, match-phrase-query and match-all-query of search operation available in Elasticsearch connector for searching.
The querytype is sent through query parameter in URL which is captured in set variable component in the flow. Later based on the value of this variable/query parameter, search operation is performed with that query types.

### Pre-requisites
1. Elasticsearch running on HTTP
2. Mule Runtime 4.2.1 
3. Anypoint studio 7

### Preparation
1. To import this demo project in Anypoint Studio, go to ***File → Import…​ → Anypoint Studio Project from File System***, select the demo project root and choose as server runtime ***Mule Server 4.2.1 EE*** 
2. Once imported, in ***src/main/resources/*** you will find ***mule-application.properties*** file, this contains all required properties to make the demo work. It will look like:

```
#ElasticSearch Host configuration
elastic.host=<PROVIDE ELASTIC SEARCH SERVER HOST>
elastic.port=9200

search.index=shakespeare
searchSource.from=0
searchSource.size=100
searchSource.timeout=50

#MATCH ALL QUERY 
searchMatchAll.fieldA=speaker
searchMatchAll.fieldB=text_entry

#MATCH PHRASE
searchMatchPhrase.field=play_name
searchMatchPhrase.queryString=Alls well that ends well
searchMatchPhrase.sortBy=line_id
searchMatchPhrase.fieldA=speaker

#MATCH QUERY
searchMatchQuery.field=speaker
searchMatchQuery.searchString=WESTMORELAND
searchMatchQuery.fieldA=text_entry
searchMatchQuery.excludeFieldA=type
searchMatchQuery.excludeFieldB=line_id
searchMatchQuery.excludeFieldC=speech_number
searchMatchQuery.excludeFieldD=line_number

#MULTI MATCH QUERY
searchMultiMatch.fieldA=line_number
searchMultiMatch.fieldB=speaker
searchMultiMatch.fieldC=text_entry
searchMultiMatch.searchString=limits of the charge set down
searchMultiMatch.includeField=text_entrys
```

3. Fill empty property with the required value:
	
Field Name        | Value
-------------     | -------------
elastic.host 	  | Host name or IP of Elasticsearch
elastic.port      | Port on which Elasticsearch is running

4. In ***Anypoint Studio***, Right click on the ***project folder → Run As → Mule Application***

5. Once the Mule App is deployed, hit <http://localhost:8081/searchOperation?querytype=matchphrase>.
	The query type can be changed to multimatch, matchquery otherwise 'Match All Query' will be executed by default
  
### How it works:
- ***Search***: Search for phrase/text depending on the configuration of query type.

