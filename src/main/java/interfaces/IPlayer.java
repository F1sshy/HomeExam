package interfaces;

public interface IPlayer {
    int getPlayerID();
    void sendMessage(String message);
    String readMessage();
    ArrayList<Card> getHand();
    void addCardToHand(Card card);
    int getScore();
    void setScore(int score);
}
