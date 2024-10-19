package pile;

import card.Card;

public interface IPile {
    Card getPointCard();
    Card buyPointCard();
    Card getVeggieCard(int index);
    Card buyVeggieCard(int index);
    boolean isEmpty();
    boolean areAnyVeggieCardsEmpty();
    void addCard(Card c);
    Card removeCard();
    int size();
}
