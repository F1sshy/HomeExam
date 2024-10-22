package player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


class PlayerFactoryTest {

    private PlayerFactory playerFactory;
    private Socket mockSocket;
    private ObjectInputStream mockInputStream;
    private ObjectOutputStream mockOutputStream;

    @BeforeEach
    void setUp() {
        playerFactory = new PlayerFactory();

    }

    @Test
    void createPlayer() throws IOException {
        int playerID = 1;
        boolean isBot = false;

        IPlayer player = playerFactory.createPlayer(playerID, isBot, mockSocket, mockInputStream, mockOutputStream);

        assertAll(
                () -> assertNotNull(player),
                () -> assertEquals(playerID, player.getPlayerID()),
                () -> assertEquals(isBot, player.isBot())
        );
    }

    @Test
    void createBotPlayer() throws IOException {
        int playerID = 2;
        boolean isBot = true;

        IPlayer player = playerFactory.createPlayer(playerID, isBot, mockSocket, mockInputStream, mockOutputStream);

        assertAll(
                () -> assertNotNull(player),
                () -> assertEquals(playerID, player.getPlayerID()),
                () -> assertEquals(isBot, player.isBot())
        );
    }
}