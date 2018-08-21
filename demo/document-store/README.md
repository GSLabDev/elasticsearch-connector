# Elasticsearch as a Document Store

### Introduction
This usecase uses bulk operation of Elasticsearch connector to upload the Shakespeare dataset to Elasticsearch. It also uses get document operation to retrieve one of the document from the uploaded dataset. This usecase use readily available dataset that can be found at [ complete works of William Shakespeare ](https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html) 

### Pre-requisites
1. Elasticsearch running on HTTP
2. Mule Runtime 4.1.1 
3. Anypoint studio 7
4. Dataset : [ complete works of William Shakespeare ](https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html) 

### Preparation
1. To import this demo project in Anypoint Studio, go to ***File → Import…​ → Anypoint Studio Project from File System***, select the demo project root and choose as server runtime ***Mule Server 4.1.1 EE*** 
2. Once imported, in ***src/main/resources/*** you will find ***mule-application.properties*** file, this contains all required properties to make the demo work. It will look like:

```
#ElasticSearch Host configuration
elastic.host=
elastic.port=

#Document store
bulk.index=shakespeare
bulk.type=doc
bulk.dataset= 

#Get Document
get.index=shakespeare
get.type=doc
get.id=111209
```

3. Fill empty property with the required value:
	
Field Name        | Value
-------------     | -------------
elastic.host 	  | Host name or IP of Elasticsearch
elastic.port      | Port on which Elasticsearch is running
bulk.dataset      | Path to shakespeare dataset (Sample dataset has been included in resources folder.)

4. In ***Anypoint Studio***, Right click on the ***project folder → Run As → Mule Application***.

5. Once the Mule App is deployed, hit <http://localhost:8081/documentstore>.
  
### How it works:
- ***Bulk Operation***: Uploads the dataset to ElasticSearch
- ***Get Document***: Retrieves the document with provided document id from Elasticsearch

