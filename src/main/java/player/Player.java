package player;
import interfaces.IPlayer;
import java.io.*;

import card.card;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements IPlayer {
    private int playerID;
    private boolean online;
    private boolean isBot;
    private Socket connection;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private ArrayList<card> hand = new ArrayList<card>();
    Scanner in = new Scanner(System.in);
    private int score = 0;

    public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        this.playerID = playerID; this.connection = connection; this.inFromClient = inFromClient; this.outToClient = outToClient; this.isBot = isBot;
        if (connection == null) {
            this.online = false;
        } else {
            this.online = true;
        }
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void sendMessage(String message) {
        if (online && outToClient != null) {
            try {
                outToClient.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
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

    @Override
    public ArrayList<card> getHand() {
        return hand;
    }

    @Override
    public void addCardToHand(card card) {
        hand.add(card);
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
    @Override
    public boolean isBot() {
        return isBot;
    }
}