package game.logic;

import card.card;
import card.card.Vegetable;

import java.util.ArrayList;

public class CardUtils {



    public static int countVegetables(ArrayList<card> hand, Vegetable vegetable) {
        int count = 0;
        for (card card : hand) {
            if (!card.criteriaSideUp && card.vegetable == vegetable) {
                count++;
            }
        }
        return count;
    }

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