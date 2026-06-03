AsteroidsFX – Component-Based Asteroids Game

A component-based implementation of the classic Asteroids arcade game developed for the Component-Based Software Engineering (CBSE) course at the University of Southern Denmark (SDU).

The project demonstrates how a game can be decomposed into independently deployable components using the Java Platform Module System (JPMS), ServiceLoader, Spring Dependency Injection, and runtime plugin loading with ModuleLayer. Score persistence is implemented as an external Spring Boot microservice communicating over REST.

Build all modules: mvn clean install
Run Game: mvn exec:exec -N
Run scoring microservice:mvn -pl ScoringService spring-boot:run
