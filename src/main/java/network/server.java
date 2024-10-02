package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import main.PointSalad;



public class server {

    public void server(int numberPlayers, int numberOfBots) throws Exception {
        players.add(new PointSalad.Player(0, false, null, null, null)); //add this instance as a player.player
        //Open for connections if there are online players
        for(int i=0; i<numberOfBots; i++) {
            players.add(new PointSalad.Player(i+1, true, null, null, null)); //add a bot
        }
        if(numberPlayers>1)
            aSocket = new ServerSocket(2048);
        for(int i=numberOfBots+1; i<numberPlayers+numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new PointSalad.Player(i, false, connectionSocket, inFromClient, outToClient)); //add an online network.client
            System.out.println("Connected to player.player " + i);
            outToClient.writeObject("You connected to the network.server as player.player " + i + "\n");
        }
    }

}
