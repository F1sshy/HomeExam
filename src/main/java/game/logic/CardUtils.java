package game.logic;

import card.ICard;
import card.Vegetable;

import java.util.ArrayList;

public class CardUtils {

    /**
     * Counts the number of specified vegetable cards in the player's hand.
     *
     * @param hand the player's hand of cards
     * @param vegetable the vegetable type to count
     * @return the count of specified vegetable cards
     */
    public static int countVegetables(ArrayList<ICard> hand, Vegetable vegetable) {
        int count = 0;
        for (ICard veggieCard : hand) {
            if (!veggieCard.getCriteriaSideUp() && veggieCard.getVegetable() == vegetable) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the total number of vegetable cards in the player's hand.
     *
     * @param hand the player's hand of cards
     * @return the total count of vegetable cards
     */
    public static int countTotalVegetables(ArrayList<ICard> hand) {
        int count = 0;
        for (ICard veggieCard : hand) {
            if (!veggieCard.getCriteriaSideUp()) {
                count++;
            }
        }
        return count;
    }

}