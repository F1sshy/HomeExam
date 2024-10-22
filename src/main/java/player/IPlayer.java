package player;


import java.util.ArrayList;

import card.ICard;
import card.VeggieCard;


public interface IPlayer {
    int getPlayerID();
    ArrayList<ICard> getHand();
    void addCardToHand(ICard veggieCard);
    int getScore();
    void setScore(int score);
    boolean isBot();
    PlayerCommunication getCommunication();
}
