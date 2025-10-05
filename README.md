**RAG Chat Storage Microservice**
Backend microservice to store and manage chat sessions and messages for a RAG (Retrieval-Augmented Generation) chatbot.

**Project Name**
* rag-chat-storage-service

**Tech Stack**
1. Backend: Java 17, Spring Boot 3, Spring Data JPA
2. Database: PostgreSQL
3. API Documentation: Swagger / OpenAPI
4. Containerization: Docker, Docker Compose
5. Build Tool: Maven

**Prerequisites**
1. Docker & Docker Compose (required to run the application and PostgreSQL/pgAdmin containers)
2. Java 17 & Maven (required to build the Spring Boot JAR locally before Docker runs it)

**Setup and Running Instructions**
1. Clone the repository
		git clone <your-repo-url>
		cd rag-chat-storage-service
2. Update/change .env file values (if required)
  	Make sure it contains:
			SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/ragdb
   			SPRING_DATASOURCE_USERNAME=postgres
   			SPRING_DATASOURCE_PASSWORD=postgres
			APP_API_KEY=2d578628-edba-49a0-919d-554e82bb36b0
   			APP_RATE_LIMIT=100
			SERVER_PORT=8080
			PGADMIN_DEFAULT_EMAIL=admin@local.com
			PGADMIN_DEFAULT_PASSWORD=admin
3. Build the Spring Boot application JAR using maven : (by running below command)
		mvn clean package
	Note : 	This step is required because the Dockerfile expects the JAR to exist in target/.
			After building, the JAR will be available at:
				target/rag-chat-storage-service-0.0.1-SNAPSHOT.jar
4. Run the services with Docker Compose : (by running below command)
		sudo docker compose -p rag-chat-storage-service up --build -d
	Note : 	This will start three containers:
				1. rag-chat-storage-service-db-1 → PostgreSQL
				2. rag-chat-storage-service-app-1 → Spring Boot app
				3. rag-chat-storage-service-pgadmin-1 → PGAdmin
5. Verify containers
	sudo docker compose ps
6. Access services
	1. App: http://localhost:8080
    		This is the main API endpoint. You can use tools like curl or Postman to interact with the APIs.
    		API Key (X-API-KEY), required for all endpoints: 2d578628-edba-49a0-919d-554e82bb36b0
	2. Swagger UI: http://localhost:8080/swagger-ui/index.html
   			Use the API Key to authorize requests in Swagger: X-API-KEY: 2d578628-edba-49a0-919d-554e82bb36b0
	3. PGAdmin: http://localhost:5050
			PGAdmin Login Credentials:
				Email: admin@local.com
				Password: admin
			Database (PostgreSQL) Connection Details:
				Host: db (or localhost if connecting externally)
				Port: 5432
				Database Name: ragdb
				Username: postgres
				Password: postgres
			Note: These values are also configured in your .env file. Make sure to update them if you change the .env.

## API Endpoints
All APIs require the X-API-KEY header with the value defined in .env (default: 2d578628-edba-49a0-919d-554e82bb36b0).

Chat Sessions
**Method**		**Endpoint**						**Description**					
POST			/api/chats/session					Create a new chat session			
GET				/api/chats/session/{sessionId}		Get chat session details (without messages)	
PUT				/api/chats/{sessionId}/rename		Rename a chat session				
PUT				/api/chats/{sessionId}/favorite		Mark or unmark a session as favorite		
DELETE			/api/chats/{sessionId}				Delete a chat session and all its messages	

Chat Messages
**Method		Endpoint							Description**	
POST			/api/chats/{sessionId}/message		Add a message to a session	
GET				/api/chats/{sessionId}/messages		Get paginated messages of a session	

Health Check
**Method		Endpoint							Description**	
GET			/health									Check if service is running
GET			/actuator/health						Spring Boot actuator health endpoint


## Swagger API Documentation
* Swagger UI is available at: http://localhost:8080/swagger-ui/index.html
* All endpoints are protected by API Key. Enter the key in the X-API-KEY field in the Authorize button.
* Provides interactive API testing and request/response previews.

## Notes
1.**Database**		: PostgreSQL container persists data in Docker volume pgdata.
2.**PGAdmin**		: Access the database via http://localhost:5050 using credentials in .env file
3.**API Key**		: Change the key in .env and restart the service to update it.
4.**Rate Limiting**	: Requests per API key are limited (default 100 requests per unit time).
