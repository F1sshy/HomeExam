package player;

import card.ICard;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Player implements IPlayer {
    private int playerID;
    private boolean isBot;
    private PlayerCommunication communication;
    private ArrayList<ICard> hand = new ArrayList<>();
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
    public ArrayList<ICard> getHand() {
        return hand;
    }

    @Override
    public void addCardToHand(ICard veggieCard) {
        hand.add(veggieCard);
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

    public PlayerCommunication getCommunication() {
        return communication;
    }
}