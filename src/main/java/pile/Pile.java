
package pile;

import card.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class Pile implements IPile {
    public ArrayList<Card> Cards = new ArrayList<>();
    public Card[] veggieCards = new Card[2];
    private Logger logger = LogManager.getLogger();

    /**
     * Default constructor for the pile class.
     */
    public Pile() {}

    /**
     * Constructs a pile with the specified list of cards.
     * Initializes the veggie cards by removing the first two cards from the list.
     *
     * @param Cards the list of cards to initialize the pile with
     */
    public Pile(ArrayList<Card> Cards) {
        this.Cards = Cards;
        this.veggieCards[0] = Cards.remove(0);
        this.veggieCards[1] = Cards.remove(0);
        this.veggieCards[0].criteriaSideUp = false;
        this.veggieCards[1].criteriaSideUp = false;
    }

    /**
     * Gets the point card from the pile.
     *
     * @return the point card, or null if the pile is empty
     */
    public Card getPointCard() {
        if (Cards.isEmpty()) {
            return null;
        }
        return Cards.get(0);
    }

    /**
     * Buys the point card from the pile, removing it from the pile.
     *
     * @return the bought point card, or null if the pile is empty
     */
    public Card buyPointCard() {
        if (Cards.isEmpty()) {
            return null;
        }
        return Cards.remove(0);
    }

    /**
     * Checks if the pile is empty.
     *
     * @return true if the pile is empty, false otherwise
     */
    public boolean isEmpty() {
        return Cards.isEmpty();
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
    public Card getVeggieCard(int index) {
        return veggieCards[index];
    }

    /**
     * Buys the veggie card at the specified index, replacing it with a new card from the pile.
     *
     * @param index the index of the veggie card to buy
     * @return the bought veggie card
     */
    public Card buyVeggieCard(int index) {
        Card aCard = veggieCards[index];
        if (Cards.size() > 0) {
            veggieCards[index] = Cards.remove(0);
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
    public void addCard(Card newCard) {
        Cards.add(newCard);
    }

    /**
     * Removes a card from the pile.
     *
     * @return the removed card, or null if the pile is empty
     */
    public Card removeCard() {
        if (!Cards.isEmpty()) {
            return Cards.remove(0);
        }
        return null;
    }

    /**
     * Gets the size of the pile.
     *
     * @return the number of cards in the pile
     */
    public int size() {
        return Cards.size();
    }
}