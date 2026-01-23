# Rummikub Rules Engine

Rummikub is a tile-based game combining and mahjong. Players arrange tiles numbered 1-13 in four colors (red, blue, black, yellow), plus jokers that can substitute for any tile.

This project implements a Rummikub rules engine in Java, focused on validating melds correctly and rigorously, including full joker logic, and exposes that logic via a Spring Boot REST API, containerized with Docker.

---

## Game Rules (Rummikub Basics)

Players form valid **melds** by arranging tiles into one of the following structures:

### 1️⃣ Runs
Sequences of **three or more consecutive numbers** in the **same color**.

**Examples:**
- `[Red 5, Red 6, Red 7]`
- `[Blue 10, Joker, Blue 12]` → Joker represents **Blue 11**

**Rules:**
- All non-joker tiles must share the same color  
- Numbers must be consecutive  
- Jokers may fill gaps or extend a run  

### 2️⃣ Sets
Groups of **three or four tiles** with the **same number** in **different colors**.

**Examples:**
- `[Red 7, Blue 7, Black 7]`
- `[Red 9, Joker, Black 9]` → Joker represents **Blue or Yellow 9**

**Rules:**
- All non-joker tiles must have the same value  
- Colors must be unique  
- Jokers may substitute for missing colors  

---

## Project Overview
This project focuses on building a clean, testable rules engine rather than a UI or full game implementation.

### Key Features
    - ✅ Validation of runs and sets
    - 🃏 Full joker substitution logic
    - ❌ Rejection of invalid melds (duplicates, gaps, illegal joker use)
    - 🌐 REST API for validating melds
    - 🐳 Dockerized for easy setup and testing

### Tech Stack
    - Java 17
    - Spring Boot (REST API)
    - JUnit
    - Docker (containerization)
    - Maven (build tool)

### Project Structure
``` 
rummikub-rules-engine/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/rummikub/
│ │ │ ├── model/ # Tile, Color, Meld, Joker
│ │ │ ├── rules/ # RunValidator, SetValidator
│ │ │ ├── service/ # RulesEngine
│ │ │ └── controller/ # REST endpoints
│ │ └── resources/
│ └── test/
│ └── java/ # Unit tests
├── Dockerfile
├── pom.xml
└── README.md
```

### API Usage
#### Validate a Meld
POST /api/validate
Example request body:
```
{
"tiles": [
{ "color": "RED", "value": 5 },
{ "color": "RED", "value": 6 },
{ "color": "JOKER" }
]
}
```
Example response:
```
{
"valid": true,
"type": "RUN"
}
```

---

## Getting Started
#### Run Locally
```
mvn spring-boot:run
```
The API will be available at:
```
http://localhost:8080
```
#### Run with Docker
```
docker build -t rummikub-engine .
docker run -p 8080:8080 rummikub-engine
```

---

## Testing
Run unit tests with:
```
mvn test
```
Tests cover:
    - Valid and invalid runs
    - Valid and invalid sets
    - Joker edge cases
    - Duplicate tiles and illegal configurations

---

## Future Improvements
- Support for full table validation (multiple melds in one turn)
- Game state tracking
- Frontend UI or CLI client
