
package pile;

import card.card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class pile implements IPile {
    public ArrayList<card> cards = new ArrayList<>();
    public card[] veggieCards = new card[2];
    private Logger logger = LogManager.getLogger();

    /**
     * Default constructor for the pile class.
     */
    public pile() {}

    /**
     * Constructs a pile with the specified list of cards.
     * Initializes the veggie cards by removing the first two cards from the list.
     *
     * @param cards the list of cards to initialize the pile with
     */
    public pile(ArrayList<card> cards) {
        this.cards = cards;
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        this.veggieCards[0].criteriaSideUp = false;
        this.veggieCards[1].criteriaSideUp = false;
    }

    /**
     * Gets the point card from the pile.
     *
     * @return the point card, or null if the pile is empty
     */
    public card getPointCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.get(0);
    }

    /**
     * Buys the point card from the pile, removing it from the pile.
     *
     * @return the bought point card, or null if the pile is empty
     */
    public card buyPointCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    /**
     * Checks if the pile is empty.
     *
     * @return true if the pile is empty, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Checks if any of the veggie cards are empty.
     *
     * @return true if any of the veggie cards are empty, false otherwise
     */
    public boolean areAnyVeggieCardsEmpty() {
        return veggieCards[0] == null || veggieCards[1] == null;
    }

    /**
     * Gets the veggie card at the specified index.
     *
     * @param index the index of the veggie card to get
     * @return the veggie card at the specified index
     */
    public card getVeggieCard(int index) {
        return veggieCards[index];
    }

    /**
     * Buys the veggie card at the specified index, replacing it with a new card from the pile.
     *
     * @param index the index of the veggie card to buy
     * @return the bought veggie card
     */
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

    /**
     * Adds a new card to the pile.
     *
     * @param newCard the card to be added
     */
    public void addCard(card newCard) {
        cards.add(newCard);
    }

    /**
     * Removes a card from the pile.
     *
     * @return the removed card, or null if the pile is empty
     */
    public card removeCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    /**
     * Gets the size of the pile.
     *
     * @return the number of cards in the pile
     */
    public int size() {
        return cards.size();
    }
}