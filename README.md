# Rock Paper Scissors API

This project is a RESTful API that allows users to play the classic game of Rock, Paper, Scissors. The API supports multiple users and offers both human and AI opponents, including a predictive AI that adapts its strategy based on the user's previous moves.

## Features
- **Play against AI**: Users can play against a random AI opponent.
- **Predictive AI**: An advanced AI that adapts its strategy based on the player's previous choices.
- **Opponent configuration**: Users can configure their opponents and view game results.

## Technologies
- **Java 17**
- **Spring Boot**
- **Maven**
- **JUnit** for testing

## How to Run the Project

1. **Clone the repository**:
   ```bash
   git clone https://github.com/andersonap/rock-paper-scissors.git
   ```
2. **Navigate to the project directory**:
   ```bash
   cd rock-paper-scissors
   ```
3. **Build the project using Maven**:
   ```bash
   mvn clean install
   ```
4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```
   The API will start at `http://localhost:8080`.

## API Endpoints

### 1. Set Opponent
Set the opponent for a specific user.

**Endpoint**:  
`POST /v1/games/{userId}/opponent`

**Request Parameters**:  
- `opponent` (String): The opponent type (`"RANDOM"` or `"PREDICTIVE"`).

**Example**:
```bash
curl -X POST "http://localhost:8080/v1/games/1/opponent?opponent=PREDICTIVE"
```

### 2. Play Game
Play a game of Rock, Paper, Scissors.

**Endpoint**:  
`POST /v1/games/{userId}/play`

**Request Parameters**:  
- `move` (String): The player's move (`"ROCK"`, `"PAPER"`, or `"SCISSORS"`).

**Example**:
```bash
curl -X POST "http://localhost:8080/v1/games/1/play?move=ROCK"
```

**Response**:  
A `GameResult` object containing date, player move, opponent, opponent move and result.

### 3. Get Game Results
Retrieve the results of past games for a user.

**Endpoint**:  
`GET /v1/games/{userId}/results`

**Example**:
```bash
curl -X GET "http://localhost:8080/v1/games/1/results"
```

**Response**:  
A list of games with details such as date, player move, opponent, opponent move and result.

## How the Predictive Player Works

The **Predictive Player** analyzes the user's past moves to adjust its strategy and increase its chances of winning. It works as follows:

- If there are fewer than 10 games played, the predictive player behaves like a random AI.
- After 10 or more games, it calculates the probabilities of the player's moves (Rock, Paper, Scissors) based on past choices.
- If the player won the last game, the AI avoids repeating the player's last winning move.
- Based on the calculated probabilities, the AI selects the move that maximizes its chance of winning against the player's predicted choice.

This adaptive approach makes the game more challenging by learning from the user's behavior over time.

## Running Tests

To run the unit tests, use the following Maven command:
```bash
mvn test
```
