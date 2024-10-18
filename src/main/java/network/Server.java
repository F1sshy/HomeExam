package network;

import player.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * The Server class implements the IServer interface and represents the server in the game.
 */
public class Server implements IServer {

    private ServerSocket aSocket;
    private ArrayList<Player> players;
    private int port;

    /**
     * Constructs a Server with the specified list of players and port number.
     *
     * @param players the list of players
     * @param port the port number for the server
     */
    public Server(ArrayList<Player> players, int port){
        this.players = players;
        this.port = port;
    }

    /**
     * Starts the server and waits for players to connect.
     * Adds the specified number of bots and waits for the remaining players to connect.
     *
     * @param numberPlayers the number of players to connect
     * @param numberOfBots the number of bots to add
     * @throws Exception if an I/O error occurs
     */
    public void server(int numberPlayers, int numberOfBots) throws Exception {

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
}