package network;

import player.IPlayer;
import player.IPlayerFactory;
import player.PlayerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements IServer {

    private ServerSocket aSocket;
    private ArrayList<IPlayer> players;
    private int port;
    private IPlayerFactory playerFactory;

    public Server(ArrayList<IPlayer> players, int port) {
        this.players = players;
        this.port = port;
        this.playerFactory = new PlayerFactory(); // Initialize the player factory
    }

    public void server(int numberPlayers, int numberOfBots) throws Exception {
        for (int i = 0; i < numberOfBots; i++) {
            players.add(playerFactory.createPlayer(i + 1, true, null, null, null)); // add a bot
        }
        aSocket = new ServerSocket(port);

        System.out.println("Waiting for " + numberPlayers + " to connect...");
        for (int i = numberOfBots + 1; i <= numberPlayers + numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(playerFactory.createPlayer(i, false, connectionSocket, inFromClient, outToClient)); // add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }
    }
}