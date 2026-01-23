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

### Technical Highlight: Joker Resolution

Jokers are the most complex part of Rummikub validation, especially for **runs**.

To validate a run with jokers, the rules engine:
- Separates joker and non-joker tiles
- Sorts non-jokers by value
- Computes the gaps between consecutive tiles
- Uses available jokers to fill those gaps
- Ensures any remaining jokers can extend the run without exceeding bounds (1–13)

For example:
- `[Red 5, Joker, Joker, Red 8]` → **valid** (jokers fill 6 and 7)
- `[Red 5, Joker, Red 9]` → **invalid** (not enough jokers to fill gaps)

This approach avoids brute-force substitution and validates melds in **linear time** relative to meld size, while remaining deterministic and easy to test.

Set validation follows similar principles, treating jokers as wildcards for missing colors while enforcing size, value, and uniqueness constraints.

---

## Getting Started
#### Run Locally
```bash
mvn spring-boot:run
```
The API will be available at:
```bash
http://localhost:8080
```
#### Run with Docker
```bash
docker build -t rummikub-engine .
docker run -p 8080:8080 rummikub-engine
```

---

## Docker Support

The API is packaged as a Docker container to ensure consistent local development and deployment.

- Runs as a single executable JAR on **Java 17**
- Exposes port **8080**
- Includes a **health check endpoint** (`/actuator/health`) for container orchestration
- Suitable for production-style deployment and CI pipelines

---

## Test Coverage

The rules engine is covered with focused unit tests around both **happy paths** and **edge cases**, especially for **joker substitution**.

### Unit Tests
- ✅ `RunValidatorTest`
  - Valid runs: 3-tile, 4+ tile, joker in middle, joker at ends, multiple jokers
  - Invalid runs: color mismatch, gaps too large for available jokers, duplicates, out-of-range values
- ✅ `SetValidatorTest`
  - Valid sets: 3-tile, 4-tile, joker substitution for missing colors
  - Invalid sets: duplicate colors, wrong size (<3 or >4), inconsistent values, illegal duplicates
- ✅ `RulesEngineTest`
  - Routes requests to the correct validator (RUN vs SET)
  - Aggregates violations into a consistent response object

Run unit tests with:
```bash
mvn test
```

### Integration Tests (API)

- ✅ **`MeldValidationControllerTest`**
  - End-to-end validation via HTTP using JSON request/response bodies
  - Verifies `400 Bad Request` responses for invalid payloads:
    - Missing required fields
    - Invalid enum values
    - Malformed or illegal tile definitions
  - Asserts error response structure, including:
    - `violations` list
    - Echoed `type` field

Run integration tests (if separated via naming or profile):

```bash
mvn test
```


---

## Future Improvements
- Support for full table validation (multiple melds in one turn)
- Game state tracking
- Frontend UI or CLI client
