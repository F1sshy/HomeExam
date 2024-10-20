package network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private ServerSocket serverSocket;
    private Thread serverThread;

    @BeforeEach
    void setUp() throws Exception {
        serverSocket = new ServerSocket(2048);
        serverThread = new Thread(() -> {
            try (Socket socket = serverSocket.accept();
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out.writeObject("winner");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        serverSocket.close();
        serverThread.join();
    }

    @Test
    void testRun() throws Exception {
        Client client = new Client("127.0.0.1", "2048");
        client.run("127.0.0.1", 2048);
    }
}