package player;
import java.io.*;

import card.card;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Player class implements the IPlayer interface and represents a player in the game.
 */
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

    /**
     * Constructs a Player with the specified parameters.
     *
     * @param playerID the ID of the player
     * @param isBot whether the player is a bot
     * @param connection the socket connection for the player
     * @param inFromClient the input stream from the client
     * @param outToClient the output stream to the client
     * @throws IOException if an I/O error occurs
     */
    public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        this.playerID = playerID; this.connection = connection; this.inFromClient = inFromClient; this.outToClient = outToClient; this.isBot = isBot;
        if (connection == null) {
            this.online = false;
        } else {
            this.online = true;
        }
    }

    /**
     * Gets the player ID.
     *
     * @return the player ID
     */
    @Override
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Sends a message to the player.
     *
     * @param message the message to be sent
     */
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

    /**
     * Gets the player's hand of cards.
     *
     * @return the player's hand of cards
     */
    @Override
    public ArrayList<card> getHand() {
        return hand;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to be added
     */
    @Override
    public void addCardToHand(card card) {
        hand.add(card);
    }

    /**
     * Gets the player's score.
     *
     * @return the player's score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     *
     * @param score the score to be set
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Checks if the player is a bot.
     *
     * @return true if the player is a bot, false otherwise
     */
    @Override
    public boolean isBot() {
        return isBot;
    }
}