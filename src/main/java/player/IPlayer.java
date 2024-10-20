package player;


import java.util.ArrayList;

import card.ICard;
import card.VeggieCard;


public interface IPlayer {
    int getPlayerID();
    void sendMessage(String message);
    String readMessage();
    ArrayList<ICard> getHand();
    void addCardToHand(ICard veggieCard);
    int getScore();
    void setScore(int score);
    boolean isBot();
}
