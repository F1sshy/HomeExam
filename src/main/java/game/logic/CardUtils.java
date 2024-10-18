package game.logic;

import card.card;
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
    public static int countVegetables(ArrayList<card> hand, Vegetable vegetable) {
        int count = 0;
        for (card card : hand) {
            if (!card.criteriaSideUp && card.getVegetable() == vegetable) {
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
    public static int countTotalVegetables(ArrayList<card> hand) {
        int count = 0;
        for (card card : hand) {
            if (!card.criteriaSideUp) {
                count++;
            }
        }
        return count;
    }

}