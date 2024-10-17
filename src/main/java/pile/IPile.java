package pile;

import card.card;

public interface IPile {
    card getPointCard();
    card buyPointCard();
    card getVeggieCard(int index);
    card buyVeggieCard(int index);
    boolean isEmpty();
    boolean areVeggieCardsEmpty();
    void addCard(card c);
    card removeCard();
    int size();
}
