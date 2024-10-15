package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public Client(String ipAddress, String port){
        try {
            run(ipAddress, Integer.parseInt(port));
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(404);
        }

    }

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
