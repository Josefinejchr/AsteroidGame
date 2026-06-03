


# AsteroidsFX

A component-based implementation of the classic Asteroids arcade game developed for the Component-Based Software Engineering (CBSE) course at the University of Southern Denmark (SDU).

The project demonstrates how a game can be decomposed into independently deployable components using the Java Platform Module System (JPMS), ServiceLoader, Spring Dependency Injection, and runtime plugin loading with ModuleLayer. Score persistence is implemented as an external Spring Boot microservice communicating over REST.


## Build

```bash
mvn clean install
```

## Run the game

```bash
mvn exec:exec -N
```

`-N` is required — the exec plugin lives in the parent POM.

## Run the scoring microservice

In a separate terminal:

```bash
mvn -pl ScoringService spring-boot:run
```
