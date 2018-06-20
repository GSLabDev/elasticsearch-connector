# README.md

## Elasticsearch Anypoint™ Connector
The Elasticsearch connector allows you to access the Elasticsearch REST API through Anypoint Platform. Elasticsearch is a distributed, open source search and analytics engine, designed for horizontal scalability, reliability, and easy management. The connector exposes Elasticsearch operations by executing their API calls as per configuration. It supports HTTP, HTTP with basic authentication and HTTPS connections to Elasticsearch instance.

## Supported Mule runtime versions
Mule 4.1.1

## Supported Elasticsearch versions
Elasticsearch version 6.2.2 or above

## Installation 

Install connector in Anypoint Studio following the instructions at [Installing Connectors from Anypoint Exchange](https://docs.mulesoft.com/mule-user-guide/v/3.9/installing-connectors) 

To configure and use the Elasticsearch connector refer [user manual](doc/Elasticsearch-connector-user-manual.adoc) 

## Authentication
* Elasticsearch host can be accessed in three different ways from a client (Mule runtime / Anypoint studio ): 
    - Without authentication (HTTP)
	 - With basic authentication (HTTP)
    - With certificate based authentication (HTTPS)

### WITHOUT AUTHENTICATION (HTTP)
Provide Elasticsearch host and port in a global configuration of Elasticsearch connector. Without authentication use is generally recommended for internal applications or for testing purpose where Elasticsearch is running on HTTP port.

### HTTP WITH BASIC AUTHENTICATION (HTTP)
Along with host and port, basic authentication will require two additional fields:
1) Username
2) Password

### CERTIFICATE BASED AUTHENTICATION (HTTPS)
Implementing CERTIFICATE BASED AUTHENTICATION mechanisms involves a few extra steps, but ìs preferred if your Elasticsearch is exposed to external users, as it ensures better security.
To make the Elasticsearch run on a https, generate server and client certificates on Elasticsearch host using X-pack. Please refer https://www.elastic.co/guide/en/elasticsearch/reference/current/configuring-tls.html#node-certificates for detailed information about running Elasticsearch on HTTPS and generating required certificates.

## Reporting Issues

Use GitHub for tracking issues with this connector. To report new issues use this [link] (https://github.com/GSLabDev/elasticsearch-connector/issues).