## A Simple Library API

This repo contains the code for a simple Library API written with Java Spring Boot,
featuring Authentication with JSON Web Tokens, Authorization using Spring Security and 
input validation using data transfer objects and constraints.

### How to run

* Clone the repository
* Run `mvn install`
* Change the database credentials in the `application.properties` file.
* Run the application
* To populate the database with dummy values, you can call the `/api/v1/populate` route. 
This command creates the following three special users, along with others, you can use to test out the authentication features;
  * Test Admin: 
    * **email**: `test@admin.com`
    * **password**: `123qwe`  
  * Test Customer: 
    * **email**: `test@customer.com`
    * **password**: `123qwe`  
  * Test Librarian: 
    * **email**: `test@librarian.com`
    * **password**: `123qwe`
    
    
In addition to the Swagger documentation at route `/swagger-ui/index.html`, a Postman collection and environment
can be found within the `postman` directory at the project root.

To get a JWT, import the Postman collection and execute the `/login` route. The automatic script will set the JWT
for you on the environment so you will be able to access the resources you are allowed without doing any other configuration.