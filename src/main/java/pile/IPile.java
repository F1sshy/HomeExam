package pile;

import card.ICard;
import card.VeggieCard;

public interface IPile {
    ICard getPointCard();
    ICard buyPointCard();
    ICard getVeggieCard(int index);
    ICard buyVeggieCard(int index);
    boolean isEmpty();
    boolean areAnyVeggieCardsEmpty();
    void addCard(ICard c);
    ICard removeCard(int index);
    int size();
}
