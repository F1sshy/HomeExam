package player;

import java.net.Socket;
import java.io.*;

public class PlayerCommunication {
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private boolean online;

    public PlayerCommunication(Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
        this.online = connection != null;
    }

    public void sendMessage(String message) {
        if (online && outToClient != null) {
            try {
                outToClient.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readMessage() {
        if (online && inFromClient != null) {
            try {
                return (String) inFromClient.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}