package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;
import game.GameEngine;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
class GameEngineTest {

    private GameEngine gameEngine;
    private List<Player> players;


    @BeforeEach
    void setUp() throws IOException {
        ArrayList players = new ArrayList<>();
        players.add(new Player(1, false, null, null, null));
        players.add(new Player(2, false, null, null, null));
        gameEngine = new GameEngine(players);
    }

*/

/*
    @Test
    void startGame() {
        gameEngine.startGame();
        assertFalse(gameEngine.isGameOver());
    }

    @Test
    void gameLoop() {
        gameEngine.startGame();
        gameEngine.gameLoop();
        assertTrue(gameEngine.isGameOver());
    }

    @Test
    void calculateAndAnnounceScores() {
        players.get(0).setScore(10);
        players.get(1).setScore(20);
        gameEngine.calculateAndAnnounceScores();
        assertEquals(20, Player.getScore());
    }

    @Test
    void sendToAllPlayers() {
        String message = "Test message";
        gameEngine.sendToAllPlayers(message);
        for (Player player : players) {
            assertEquals(message, player.readMessage());
        }
    }

}
*/
