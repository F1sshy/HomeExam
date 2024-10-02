package player;

import main.PointSalad;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class player {

    class Player {
        public int playerID;
        public boolean online;
        public boolean isBot;
        public Socket connection;
        public ObjectInputStream inFromClient;
        public ObjectOutputStream outToClient;
        public ArrayList<String> region = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        public ArrayList<PointSalad.Card> hand = new ArrayList<PointSalad.Card>();
        public int score = 0;



        public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
            this.playerID = playerID; this.connection = connection; this.inFromClient = inFromClient; this.outToClient = outToClient; this.isBot = isBot;
            if(connection == null)
                this.online = false;
            else
                this.online = true;
        }
        public void sendMessage(Object message) {
            if(online) {
                try {outToClient.writeObject(message);} catch (Exception e) {}
            } else if(!isBot){
                System.out.println(message);
            }
        }
        public String readMessage() {
            String word = "";
            if(online)
                try{word = (String) inFromClient.readObject();} catch (Exception e){}
            else
                try {word=in.nextLine();} catch(Exception e){}
            return word;
        }
    }
}
