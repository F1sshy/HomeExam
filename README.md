Thank you for the clarification! Based on your input, here's the revised **README** to reflect that the game is always online and correct the instructions for starting the server and clients:

---

# PointSalad Game

Welcome to the PointSalad game! This project is a multiplayer online card-based game where players collect and manage vegetables to score points. The game can be played with both human players and bots over a network.

## Table of Contents

1. [Requirements](#requirements)
2. [Project Structure](#project-structure)
3. [Setup Instructions](#setup-instructions)
4. [Running the Game](#running-the-game)
5. [How to Play](#how-to-play)
6. [Contribution](#contribution)
7. [License](#license)

## Requirements

To run the game, you will need:

- **Java Development Kit (JDK)** 11 or above
- A Java IDE such as IntelliJ IDEA or Eclipse, or you can run the game using the command line.
- A network connection for multiplayer play.

## Project Structure

```
/src
    ├── main.java               # Entry point for the game, handles server setup and client connections.
    ├── PointSalad.java          # Main game logic.
    ├── CardFactory.java         # Factory for creating card instances.
    ├── ICard.java               # Interface for cards.
    ├── VeggieCard.java          # Implementation of a vegetable card.
    ├── Market.java              # Manages the vegetable market.
    ├── GameEngine.java          # Controls the game flow and rules.
    ├── Player.java              # Handles player actions and states.
    ├── Server.java              # Manages server-side networking and game state.
    ├── Client.java              # Manages client-side networking and player interactions.
    └── Display.java             # Handles game display and output.
```

## Setup Instructions

### 1. Cloning the Repository

To get started, clone this repository to your local machine:

```bash
git clone https://github.com/yourusername/PointSalad.git
cd PointSalad
```

### 2. Building the Project

If you are using an IDE like IntelliJ IDEA or Eclipse, you can import the project by selecting the folder and opening it as a Java project. Ensure that your IDE is set to use **Java 11 or higher**.

Alternatively, if you're using the command line, you can compile the code with the following:

```bash
javac -d bin src/*.java
```

This will compile all the `.java` files and place the class files in a `bin` directory.

## Running the Game

The game is always played in an online mode, with a server and one or more clients (which can be either human players or bots). Here’s how to set it up:

### 1. Starting the Server

The server is responsible for managing the game state and player connections. To start the server, you need to provide two arguments: the number of human players and the number of bot players. For example, to start a game with 2 human players and 1 bot, run:

```bash
java -cp bin main 2 1
```

This command will start the server and prepare it to accept connections for the specified number of players and bots.

### 2. Connecting Clients

Each human player connects to the server as a client. To connect to the server, clients need to specify the server’s IP address and port number. During testing, `127.0.0.1` (localhost) and port `2048` were used for local connections. To connect a client, run the following command:

```bash
java -cp bin main 127.0.0.1 2048
```

Repeat this step for each human player or bot that needs to join the game. Once all players are connected, the game will begin automatically.

### 3. Running the Game Locally for Testing

For local testing, you can run both the server and clients on the same machine. Start the server with the number of players and bots as shown above, and then run multiple clients using the localhost IP (`127.0.0.1`) and port `2048`.

```bash
# Start the server with 2 human players and 1 bot
java -cp bin main 2 1

# Connect first client
java -cp bin main 127.0.0.1 2048

# Connect second client
java -cp bin main 127.0.0.1 2048
```

Make sure to connect the correct number of clients as specified in the server arguments (2 players and 1 bot in this case).

## How to Play

- The game is a card-based, turn-based game where players draft vegetable cards from the shared market.
- Players aim to collect the best combination of vegetables to maximize their score, based on certain card combinations.
- The game continues until all cards in the market are drafted, at which point the player with the highest score wins.
- Players take turns drafting cards from the market, and the market is refreshed periodically.


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

-