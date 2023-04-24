# CISC191
Intermediate Java Programming Final Project Template
Mahyar Saadati CISC 191_22487
## Prerequisites
1. Oracle openJDK 1.8
2. Git
3. Maven
## Building
mvn clean install
## Running
java -jar Server/target/Server-1.0.0.jar  
java -jar Client/target/Client-1.0.0.jar
## Common Module
Shared classes between client and server modules.
## Server Module
The server application that handles multiple clients.
## Client Module
The client application used to connect to the server.
## Maven Dependencies
<dependency>
  <groupId>net.jthink</groupId>
  <artifactId>jaudiotagger</artifactId>
  <version>3.0.1</version>
</dependency>