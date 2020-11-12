# note_app

Java REST API
A RESTful CRUD API for a Note-taking application using Spring Boot, H2, JPA and Hibernate.

Requirements
Java 1.8.x
Maven 3.x.x
MySQL 5.x.x(Optional)

Setup
Steps to Setup
1. Clone the application
git clone https://github.com/okeyifee/note_app.git


2. Create Mysql database or change database settings for H2
create database notedb


3. Change mysql username and password as per your installation
open src/main/resources/application.properties
change spring.datasource.username and spring.datasource.password as per your mysql installation

4. Build and run the app using maven

mvn package:
mvn spring-boot:run
The app will start running at http://localhost:8080.

Explore the routes
The app defines following routes.

GET /api/notes

POST /api/notes

GET /api/notes/{noteId}

PUT /api/notes/{noteId}

DELETE /api/notes/{noteId}

