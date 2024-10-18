
package pile;

import card.card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class pile implements IPile {
    public ArrayList<card> cards = new ArrayList<>();
    public card[] veggieCards = new card[2];
    private Logger logger = LogManager.getLogger();

    public pile() {}

    public pile(ArrayList<card> cards) {
        this.cards = cards;
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        this.veggieCards[0].criteriaSideUp = false;
        this.veggieCards[1].criteriaSideUp = false;
    }

    public card getPointCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.get(0);
    }

    public card buyPointCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean areAnyVeggieCardsEmpty() {
        return veggieCards[0] == null || veggieCards[1] == null;
    }

    public card getVeggieCard(int index) {
        return veggieCards[index];
    }

    public card buyVeggieCard(int index) {
        card aCard = veggieCards[index];
        if (cards.size() > 0) {
            veggieCards[index] = cards.remove(0);
            veggieCards[index].criteriaSideUp = false;
        } else {
            veggieCards[index] = null;
        }
        return aCard;
    }

    public void addCard(card newCard) {
        cards.add(newCard);
    }

    public card removeCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public int size() {
        return cards.size();
    }
}