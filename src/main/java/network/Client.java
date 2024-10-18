package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * The Client class implements the IClient interface and represents a client in the game.
 */
public class Client implements IClient {

    /**
     * Constructs a Client and attempts to connect to the server.
     *
     * @param ipAddress the IP address of the server
     * @param port the port number of the server
     */
    public Client(String ipAddress, String port){
        try {
            run(ipAddress, Integer.parseInt(port));
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(404);
        }

    }

    /**
     * Connects to the server and handles communication.
     *
     * @param ipAddress the IP address of the server
     * @param port the port number of the server
     * @throws Exception if an I/O error occurs when creating the socket or streams
     */
    public void run(String ipAddress, int port) throws Exception {
        //Connect to network.server
        Socket aSocket = new Socket(ipAddress, port);
        ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());
        String nextMessage = "";
        while(!nextMessage.contains("winner")){
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            if(nextMessage.contains("Take") || nextMessage.contains("into")) {
                Scanner in = new Scanner(System.in);
                outToServer.writeObject(in.nextLine());
            }
        }
    }

}
