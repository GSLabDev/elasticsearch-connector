# Different Elasticsearch operations that can be performed on Documents and Indices

### Introduction
This usecase demonstrates the use of different document and index operations of Elasticsearch connector as mentioned below and if successful returns the ***true*** message.
1. Create Index
2. Index Document
3. Get Document
4. Update Document
5. Delete Document
6. Delete Index

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

#Index Parameter for use case 1
index=applicationd1

#Document Parameters
document.type=_doc
document.id=1
document.path= indexDocument.json
document.updatepath= UpdateIndexDocument.json
```

3. Fill empty property with the required value:
	
Field Name        | Value
-------------     | -------------
elastic.host 	  | Host name or IP of Elasticsearch
elastic.port      | Port on which Elasticsearch server is running

4. In ***Anypoint Studio***, Right click in the ***project folder → Run As → Mule Application***

5. Once the Mule App is deployed, hit <http://localhost:8081/documentIndicesOperations>.
  
### How it works:
- ***Create Index***: Create index operation instantiate an index
- ***Index Document***: Index document operation adds a typed JSON document in a specific index, making it searchable
- ***Get Document***: Get operation retrieves a typed JSON document from the index based on its id
- ***Update Document***: Update operation updates a typed JSON document in a specific index, making it searchable
- ***Delete Document***: Delete operation deletes a typed JSON document from a specific index based on its id
- ***Delete Index***: Delete index operation delete an existing index