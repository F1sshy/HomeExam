package network;

import player.player;
import game.engine;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server {

    public ServerSocket aSocket;
    private ArrayList<player> players = new ArrayList<>();

    public void server(int numberPlayers, int numberOfBots) throws Exception {
        players.add(new player(0, false, null, null, null)); // add this instance as a player
        // Open for connections if there are online players
        for (int i = 0; i < numberOfBots; i++) {
            players.add(new player(i + 1, true, null, null, null)); // add a bot
        }
        if (numberPlayers > 1)
            aSocket = new ServerSocket(2048);
        for (int i = numberOfBots + 1; i < numberPlayers + numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new player(i, false, connectionSocket, inFromClient, outToClient)); // add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }
    }

    private void sendToAllPlayers(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }
}