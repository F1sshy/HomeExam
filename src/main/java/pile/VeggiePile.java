
package pile;

import card.ICard;
import card.VeggieCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class VeggiePile implements IPile {
    public ArrayList<ICard> veggieCards = new ArrayList<>();
    public ICard[] veggieVeggieCards = new VeggieCard[2];
    private Logger logger = LogManager.getLogger();

    /**
     * Default constructor for the pile class.
     */
    public VeggiePile() {}

    /**
     * Constructs a pile with the specified list of cards.
     * Initializes the veggie cards by removing the first two cards from the list.
     *
     * @param veggieCards the list of cards to initialize the pile with
     */
    public VeggiePile(ArrayList<ICard> veggieCards) {
        this.veggieCards = veggieCards;
        this.veggieVeggieCards[0] = veggieCards.remove(0);
        this.veggieVeggieCards[1] = veggieCards.remove(0);
        this.veggieVeggieCards[0].setCriteriaSideUp(false);
        this.veggieVeggieCards[1].setCriteriaSideUp(false);
    }

    /**
     * Gets the point card from the pile.
     *
     * @return the point card, or null if the pile is empty
     */
    public ICard getPointCard() {
        if (veggieCards.isEmpty()) {
            return null;
        }
        return veggieCards.get(0);
    }

    /**
     * Buys the point card from the pile, removing it from the pile.
     *
     * @return the bought point card, or null if the pile is empty
     */
    public ICard buyPointCard() {
        if (veggieCards.isEmpty()) {
            return null;
        }
        return veggieCards.remove(0);
    }

    /**
     * Checks if the pile is empty.
     *
     * @return true if the pile is empty, false otherwise
     */
    public boolean isEmpty() {
        return veggieCards.isEmpty();
    }

    /**
     * Checks if any of the veggie cards are empty.
     *
     * @return true if any of the veggie cards are empty, false otherwise
     */
    public boolean areAnyVeggieCardsEmpty() {
        return veggieVeggieCards[0] == null || veggieVeggieCards[1] == null;
    }

    /**
     * Gets the veggie card at the specified index.
     *
     * @param index the index of the veggie card to get
     * @return the veggie card at the specified index
     */
    public ICard getVeggieCard(int index) {
        return veggieVeggieCards[index];
    }

    /**
     * Buys the veggie card at the specified index, replacing it with a new card from the pile.
     *
     * @param index the index of the veggie card to buy
     * @return the bought veggie card
     */
    public ICard buyVeggieCard(int index) {
        ICard aVeggieCard = veggieVeggieCards[index];
        if (veggieCards.size() > 0) {
            veggieVeggieCards[index] = veggieCards.remove(0);
            veggieVeggieCards[index].setCriteriaSideUp(false);
        } else {
            veggieVeggieCards[index] = null;
        }
        return aVeggieCard;
    }

    /**
     * Adds a new card to the pile.
     *
     * @param newVeggieCard the card to be added
     */
    public void addCard(ICard newVeggieCard) {
        veggieCards.add(newVeggieCard);
    }

    /**
     * Removes a card from the pile.
     *
     * @return the removed card, or null if the pile is empty
     */
    public ICard removeCard(int index) {
        if (!veggieCards.isEmpty()) {
            return veggieCards.remove(index);
        }
        return null;
    }

    /**
     * Gets the size of the pile.
     *
     * @return the number of cards in the pile
     */
    public int size() {
        return veggieCards.size();
    }

    public int totalSize() {
        return veggieCards.size() + veggieVeggieCards.length;
    }
}