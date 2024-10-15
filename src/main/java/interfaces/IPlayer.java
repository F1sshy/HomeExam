package interfaces;


import java.util.ArrayList;
import card.card;


public interface IPlayer {
    int getPlayerID();
    void sendMessage(String message);
    String readMessage();
    ArrayList<card> getHand();
    void addCardToHand(card card);
    int getScore();
    void setScore(int score);
    boolean isBot();
}
