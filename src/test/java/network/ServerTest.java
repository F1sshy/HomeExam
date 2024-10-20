package network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import player.IPlayer;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private Server server;
    private Thread clientThread;
    private ArrayList<IPlayer> players;

    @BeforeEach
    void setUp() throws Exception {
        players = new ArrayList<>();
        server = new Server(players, 2048);

        clientThread = new Thread(() -> {
            try (Socket socket = new Socket("127.0.0.1", 2048);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                assertEquals("You connected to the server as player 1\n", in.readObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientThread.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        clientThread.join();
    }

    @Test
    void testServer() throws Exception {
        server.server(1, 0);
        assertEquals(1, players.size());
    }
}