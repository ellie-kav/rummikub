# Rummikub Rules Engine

Rummikub is a tile-based game combining elements of rummy and mahjong. Players arrange tiles numbered 1-13 in four colors (red, blue, black, yellow), plus jokers that can substitute for any tile.

Below is a small visual example of common gameplay patterns.

**Run:**  
🟥5 🟥6 🃏 🟥8

**Set:**  
🟥7 🟦7 ⬛7

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
rummikub/
├── src/main/java/com/elliekavanagh/rummikub/
│   ├── api/                          # Spring Boot REST layer
│   │   ├── RummikubApiApplication.java
│   │   ├── ValidationController.java
│   │   ├── HealthController.java
│   │   └── dto/                      # Request/response objects
│   │       ├── MeldValidationRequest.java
│   │       ├── MeldValidationResponse.java
│   │       └── TileDto.java
│   │
│   ├── model/                        # Core domain objects (framework-agnostic)
│   │   ├── Tile.java
│   │   ├── Color.java
│   │   ├── Meld.java
│   │   └── MeldType.java
│   │
│   └── rules/                        # Validation logic (framework-agnostic)
│       ├── MeldRulesEngine.java      # Routes to appropriate validator
│       ├── MeldValidator.java        # Validator interface
│       ├── RunValidator.java         # Consecutive sequence validation
│       └── SetValidator.java         # Same-value group validation
│
├── src/test/java/com/elliekavanagh/rummikub/
│   ├── api/
│   │   └── ValidationControllerTest.java  # Integration tests
│   └── rules/
│       ├── MeldRulesEngineTest.java
│       ├── RunValidatorTest.java
│       └── SetValidatorTest.java
│
├── Dockerfile
├── pom.xml
└── README.md
```

### API Usage
#### Validate a Meld
POST /api/v1/melds/validate

#### Example API Calls (cURL)
Once the service is running locally, you can validate a meld using the following command:

✅ RUN Example
```bash
curl -X POST http://localhost:8080/api/v1/melds/validate \
  -H "Content-Type: application/json" \
  -d '{
    "type": "RUN",
    "tiles": [
      { "color": "RED", "value": 5, "joker": false },
      { "color": "RED", "value": 6, "joker": false },
      { "joker": true }
    ]
  }'
```
Expected response:
```json
{"valid": true}
```
✅ SET Example
```bash
curl -X POST http://localhost:8080/api/v1/melds/validate \
  -H "Content-Type: application/json" \
  -d '{
    "type": "SET",
    "tiles": [
      { "color": "RED", "value": 7, "joker": false },
      { "color": "BLUE", "value": 7, "joker": false },
      { "color": "BLACK", "value": 7, "joker": false }
    ]
  }'
```
Expected response:
```json
{"valid": true}
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
#### Run with Docker
```bash
docker build -t rummikub-engine .
docker run -p 8080:8080 rummikub-engine
```
The API will be available at:
```bash
http://localhost:8080
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

- ✅ **`RunValidatorTest`**
  - Valid runs:
    - 3-tile consecutive run with same color
    - Run with a joker filling an internal gap
    - Run extended by jokers within valid bounds
  - Invalid runs:
    - Mixed colors
    - Duplicate values
    - Too-short runs (<3 tiles)
    - All-joker runs

- ✅ **`SetValidatorTest`**
  - Valid sets:
    - 3-tile set with distinct colors and same value
    - 4-tile set with distinct colors and same value
    - Joker substitution for a missing color
  - Invalid sets:
    - Duplicate colors
    - Mixed values
    - Too-short sets (<3 tiles)
    - Too-long sets (>4 tiles)
    - All-joker sets

- ✅ **`MeldRulesEngineTest`**
  - Validates RUN and SET melds through a single engine entry point
  - Confirms correct dispatch to run vs set validation logic for valid inputs

Run unit tests with:
```bash
mvn test
```

### Integration Tests (Application / API Layer)

- ✅ **`ValidationControllerTest`**
  - Loads the full Spring application context (`@SpringBootTest`)
  - Exercises the meld validation API via HTTP using `MockMvc` and JSON request/response bodies
  - Verifies correct request routing from controller → rules engine → validators
  - Asserts `400 Bad Request` responses for invalid payloads, including:
    - Missing required fields
    - Invalid enum values
    - Malformed or illegal tile definitions
  - Asserts error response structure

Run integration tests:

```bash
mvn test
```


---

## Future Improvements

- **Expanded test coverage:** continue refining the test suite with additional edge cases (e.g., joker placement at run boundaries, large gaps relative to joker count, and implied out-of-range values), as well as higher-level tests covering invalid combinations across meld types.

- **Full table validation:** validate an entire board state (multiple melds per turn), including legality checks when tiles are rearranged across the table.

- **Game state and turn flow:** model a complete game lifecycle (rack, draw pile, turns, plays) to support gameplay-level validation and stateful interactions.

- **Client interface:** add a lightweight **CLI** or simple web UI to submit melds and visualize validation results.

