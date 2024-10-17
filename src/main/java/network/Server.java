package network;

import player.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Server implements IServer {

    private ServerSocket aSocket;
    private ArrayList<Player> players;
    private int port;

    public Server(ArrayList<Player> players, int port){
        this.players = players;
        this.port = port;
    }

    public void server(int numberPlayers, int numberOfBots) throws Exception {
        //players.add(new Player(0, false, null, null, null)); // add this instance as a Player
        // Open for connections if there are online players
        for (int i = 0; i < numberOfBots; i++) {
            players.add(new Player(i + 1, true, null, null, null)); // add a bot
        }
        aSocket = new ServerSocket(port);

        System.out.println("Waiting for " + numberPlayers + " to connect...");
        for (int i = numberOfBots + 1; i <= numberPlayers + numberOfBots; i++) {
            System.out.println("xd4");
            Socket connectionSocket = aSocket.accept();
            System.out.println("xd5");
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new Player(i, false, connectionSocket, inFromClient, outToClient)); // add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }
    }

    public void sendToAllPlayers(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }
}