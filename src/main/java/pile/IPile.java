package pile;

import card.VeggieCard;

public interface IPile {
    VeggieCard getPointCard();
    VeggieCard buyPointCard();
    VeggieCard getVeggieCard(int index);
    VeggieCard buyVeggieCard(int index);
    boolean isEmpty();
    boolean areAnyVeggieCardsEmpty();
    void addCard(VeggieCard c);
    VeggieCard removeCard();
    int size();
}
