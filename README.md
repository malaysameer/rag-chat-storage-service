#### RAG Chat Storage Microservice

Backend microservice to store and manage chat sessions and messages for a RAG (Retrieval-Augmented Generation) chatbot.





##### **Project Name**

* rag-chat-storage-service





##### **Prerequisites**

* Docker \& Docker Compose
* Java 17 (if running locally without Docker)
* Maven (for building locally)



##### **Setup and Running Instructions**



* ###### Clone the repository

&nbsp;	git clone <your-repo-url>

&nbsp;	cd rag-chat-storage-service



* Update .env file values if needed
  	Make sure it contains:
  ---


			SPRING\_DATASOURCE\_URL=jdbc:postgresql://db:5432/ragdb

&nbsp;			SPRING\_DATASOURCE\_USERNAME=postgres

&nbsp;			SPRING\_DATASOURCE\_PASSWORD=postgres

&nbsp;			APP\_API\_KEY=2d578628-edba-49a0-919d-554e82bb36b0

&nbsp;			APP\_RATE\_LIMIT=100

&nbsp;			SERVER\_PORT=8080

&nbsp;			PGADMIN\_DEFAULT\_EMAIL=admin@local.com

&nbsp;			PGADMIN\_DEFAULT\_PASSWORD=admin



* ###### Build the Spring Boot application JAR using maven : (by running below command)

&nbsp;			mvn clean package

&nbsp;	Note : 	This step is required because the Dockerfile expects the JAR to exist in target/.

&nbsp;		After building, the JAR will be available at:

&nbsp;			target/rag-chat-storage-service-0.0.1-SNAPSHOT.jar



* ###### Run the services with Docker Compose : (by running below command)

&nbsp;			sudo docker compose -p rag-chat-storage-service up --build -d

&nbsp;	Note : 	This will start three containers:

&nbsp;			rag-chat-storage-service-db-1 → PostgreSQL

&nbsp;			rag-chat-storage-service-app-1 → Spring Boot app

&nbsp;			rag-chat-storage-service-pgadmin-1 → PGAdmin



* ###### Verify containers

&nbsp;	sudo docker compose ps



* ###### Access services

&nbsp;	App: http://localhost:8080

&nbsp;	Swagger UI: http://localhost:8080/swagger-ui/index.html

&nbsp;	PGAdmin: http://localhost:5050



##### &nbsp;	

##### API Endpoints



All APIs require the X-API-KEY header with the value defined in .env (default: 2d578628-edba-49a0-919d-554e82bb36b0).





* ###### Chat Sessions



**Method**		**Endpoint**				**Description**					

POST		/api/chats/session			Create a new chat session			

GET		/api/chats/session/{sessionId}		Get chat session details (without messages)	

PUT		/api/chats/{sessionId}/rename		Rename a chat session				

PUT		/api/chats/{sessionId}/favorite		Mark or unmark a session as favorite		

DELETE		/api/chats/{sessionId}			Delete a chat session and all its messages	



* ###### Chat Messages



**Method		Endpoint				Description**	

POST		/api/chats/{sessionId}/message		Add a message to a session	

GET		/api/chats/{sessionId}/messages		Get paginated messages of a session	



* ###### Health Check



**Method		Endpoint				Description**	

GET		/health					Check if service is running

GET		/actuator/health			Spring Boot actuator health endpoint





##### Swagger API Documentation



* Swagger UI is available at: http://localhost:8080/swagger-ui/index.html
* All endpoints are protected by API Key. Enter the key in the X-API-KEY field in the Authorize button.
* Provides interactive API testing and request/response previews.



##### Notes



* **Database**: PostgreSQL container persists data in Docker volume pgdata.
* **PGAdmin**: Access the database via http://localhost:5050 using credentials in .env file
* **API Key**: Change the key in .env and restart the service to update it.
* **Rate Limiting**: Requests per API key are limited (default 100 requests per unit time).
