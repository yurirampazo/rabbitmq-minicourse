# Microservices Minicourse: RabbitMQ with Spring Boot

## Overview

This minicourse demonstrates how to build a simple microservices architecture using Java, Spring Boot, and RabbitMQ. The project consists of a producer service that sends stock price updates, two consumer services (one in Java and one to be defined in Node.js or Go), and a shared domain library. RabbitMQ is used as the message broker, orchestrated via Docker Compose.

## Architecture

```
+----------------+         +----------------+         +---------------------+
|                |         |                |         |                     |
|  Producer      |  --->   |   RabbitMQ     |  --->   |  Stock Consumer     |
|  (stockprice)  |  AMQP   | (Dockerized)   |  AMQP   |  (Java)             |
|                |         |                |         +---------------------+
|                |         |                |         |                     |
|                |         |                |         |  Price Consumer     |
|                |         |                |         |  (Node.js or Go,    |
|                |         |                |         |   in definition)    |
+----------------+         +----------------+         +---------------------+
```

- **Producer (stockprice):** Publishes stock price messages to RabbitMQ.
- **Stock Consumer:** Listens for messages from RabbitMQ and processes them (Java).
- **Price Consumer:** Listens for messages from RabbitMQ and processes them (Node.js or Go, in definition).
- **domainlib:** Shared library for domain models and utilities.
- **RabbitMQ:** Message broker, managed via Docker Compose.

## Learning Objectives
- Understand microservices communication using RabbitMQ and AMQP.
- Use Spring Boot for building producer and consumer services.
- Share code between services using a common library.
- Orchestrate services and infrastructure with Docker Compose.

## Prerequisites
- Java 21 (for producer and stock consumer)
- Maven (for producer and stock consumer)
- Docker & Docker Compose
- Node.js or Go (for price consumer, to be defined)

## Setup Instructions

### 1. Start RabbitMQ

From the project root, run:

```bash
docker-compose up -d
```

RabbitMQ Management UI: [http://localhost:15672](http://localhost:15672)  
Default user: `admin` / `123456`

### 2. Build the Shared Library

```bash
cd domainlib
mvn clean install
```

### 3. Build and Run the Producer

```bash
cd ../producer/stockprice
mvn spring-boot:run
```

### 4. Build and Run the Stock Consumer

```bash
cd ../../consumers/stock
mvn spring-boot:run
```

### 5. Build and Run the Price Consumer

Implementation in Node.js or Go is in definition. Instructions will be added once the technology is chosen.

## Useful Links
- [Spring Boot AMQP Guide](https://spring.io/guides/gs/messaging-rabbitmq/)
- [RabbitMQ Management UI](http://localhost:15672)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Apache Maven](https://maven.apache.org/)

## Project Structure

- `producer/stockprice`: Producer microservice
- `consumers/stock`: Stock consumer microservice (Java)
- `consumers/price`: Price consumer microservice (Node.js or Go, in definition)
- `domainlib`: Shared domain library
- `docker-compose.yml`: RabbitMQ service definition
- `data/`: RabbitMQ data persistence

---

Feel free to explore each module's `HELP.md` for more detailed documentation and links.
