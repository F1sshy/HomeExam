package player;


import java.util.ArrayList;
import card.VeggieCard;


public interface IPlayer {
    int getPlayerID();
    void sendMessage(String message);
    String readMessage();
    ArrayList<VeggieCard> getHand();
    void addCardToHand(VeggieCard veggieCard);
    int getScore();
    void setScore(int score);
    boolean isBot();
}
