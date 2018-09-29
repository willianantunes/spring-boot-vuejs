# Honest CRUD project with Spring Boot and Vue.js

The idea is to test Vue.js to see if it's powerful or not.

Here you'll find a complete stack powered by Spring Boot in the back-end providing first-class CRUD operations in MongoDB.

## Stack

- Java 11
- Spring Boot 2.0.X
- JUnit 5
- Vue.js 2.X

## Development details

First make MongoDB works:

    docker run -itd --name mongodb -p 27017:27017 mongo:3.6.3

Then run MainApplication in your favorite IDE or issue the following command:

    mvn spring-boot:run

Test the REST web service with Swagger if you want it:

- http://localhost:8081/swagger-ui.html

Now open another terminal and access `src/main/webapp` folder. There you can type:    
 
    npm run dev

The front-end application written in Vue.js can be accessed from: 

- http://localhost:8080

## Links and refs

- https://github.com/willianantunes/stuff-books/tree/master/Vue.js%20in%20Action
