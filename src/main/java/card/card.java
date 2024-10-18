package card;

/**
 * Sends a message to all connected players.
 *
 * @param message the message to be sent
 */
public class card implements ICard {

    /**
     * Enum representing the types of vegetables.
     */
    public enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }

    public Vegetable vegetable;
    public String criteria;
    public boolean criteriaSideUp = true;

    /**
     * Constructs a card with the specified vegetable and criteria.
     *
     * @param vegetable the vegetable type of the card
     * @param criteria the criteria of the card
     */
    public card(Vegetable vegetable, String criteria) {
        this.vegetable = vegetable;
        this.criteria = criteria;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return the string representation of the card
     */
    @Override
    public String toString() {
        if (criteriaSideUp) {
            return criteria + " (" + vegetable + ")";
        } else {
            return vegetable.toString();
        }
    }

    /**
     * Gets the criteria side up status of the card.
     *
     * @return true if the criteria side is up, false otherwise
     */
    public boolean getCriteriaSideUp() {
        return this.criteriaSideUp;
    }

    /**
     * Gets the vegetable type of the card.
     *
     * @return the vegetable type of the card
     */
    public Vegetable getVegetable() {
        return this.vegetable;
    }

    /**
     * Gets the criteria of the card.
     *
     * @return the criteria of the card
     */
    @Override
    public String getCriteria() {
        // TODO Auto-generated method stub
        return this.criteria;
    }

    /**
     * Sets the criteria side up status of the card.
     *
     * @param value the new criteria side up status
     */
    public void setCriteriaSideUp(boolean value) {
        this.criteriaSideUp = value;
    }
}


