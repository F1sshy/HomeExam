package player;

import card.card;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements IPlayer {
    private int playerID;
    private boolean isBot;
    private PlayerCommunication communication;
    private ArrayList<card> hand = new ArrayList<>();
    private int score = 0;

    public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        this.playerID = playerID;
        this.isBot = isBot;
        this.communication = new PlayerCommunication(connection, inFromClient, outToClient);
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void sendMessage(String message) {
        communication.sendMessage(message);
    }

    @Override
    public String readMessage() {
        return communication.readMessage();
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