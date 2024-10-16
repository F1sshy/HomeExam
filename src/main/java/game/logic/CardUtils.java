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


    public static String displayHand(ArrayList<card> hand) {
        StringBuilder handString = new StringBuilder("Criteria:\t");
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).criteriaSideUp && hand.get(i).vegetable != null) {
                handString.append("[").append(i).append("] ").append(hand.get(i).criteria).append(" (").append(hand.get(i).vegetable.toString()).append(")\t");
            }
        }
        handString.append("\nVegetables:\t");
        for (Vegetable vegetable : Vegetable.values()) {
            int count = countVegetables(hand, vegetable);
            if (count > 0) {
                handString.append(vegetable).append(": ").append(count).append("\t");
            }
        }
        return handString.toString();
    }
}