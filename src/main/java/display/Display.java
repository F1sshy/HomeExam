package display;

import card.ICard;
import card.VeggieCard;
import pile.VeggiePile;
import game.logic.CardUtils;
import card.Vegetable;
import java.util.ArrayList;

public class Display implements IDisplay {


    /**
     * Displays the player's hand of cards.
     *
     * @param hand the player's hand of cards
     * @return a string representation of the player's hand
     */
    public static String displayHand(ArrayList<ICard> hand) {
        StringBuilder handString = new StringBuilder("Criteria:\t");
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getCriteriaSideUp() && hand.get(i).getVegetable() != null) {
                handString.append("[").append(i).append("] ").append(hand.get(i).getCriteria()).append(" (").append(hand.get(i).getVegetable().toString()).append(")\t");
            }
        }
        handString.append("\nVegetables:\t");
        for (Vegetable vegetable : Vegetable.values()) {
            int count = CardUtils.countVegetables(hand, vegetable);
            if (count > 0) {
                handString.append(vegetable).append(": ").append(count).append("\t");
            }
        }
        return handString.toString();
    }

    /**
     * Displays the market of piles.
     *
     * @param veggiePiles the list of piles in the market
     * @return a string representation of the market
     */
    public String displayMarket(ArrayList<VeggiePile> veggiePiles) {
        StringBuilder pileString = new StringBuilder("Point Cards:\t");
        for (int p = 0; p < veggiePiles.size(); p++) {
            if (veggiePiles.get(p).getPointCard() == null) {
                pileString.append("[").append(p).append("]").append(String.format("%-43s", "Empty")).append("\t");
            } else {
                pileString.append("[").append(p).append("]").append(String.format("%-43s", veggiePiles.get(p).getPointCard())).append("\t");
            }
        }
        pileString.append("\nVeggie Cards:\t");
        char veggieCardIndex = 'A';
        for (VeggiePile veggiePile : veggiePiles) {
            if (veggiePile.getVeggieCard(0) != null) {
                pileString.append("[").append(veggieCardIndex).append("]").append(String.format("%-43s", veggiePile.getVeggieCard(0))).append("\t");
            } else {
                pileString.append("[").append(veggieCardIndex).append("]").append(String.format("%-43s", "Empty")).append("\t");
            }
            veggieCardIndex += 2;
        }
        pileString.append("\n\t\t");
        veggieCardIndex = 'B';
        for (VeggiePile veggiePile : veggiePiles) {
            if (veggiePile.getVeggieCard(1) != null) {
                pileString.append("[").append(veggieCardIndex).append("]").append(String.format("%-43s", veggiePile.getVeggieCard(1))).append("\t");
            } else {
                pileString.append("[").append(veggieCardIndex).append("]").append(String.format("%-43s", "Empty")).append("\t");
            }
            veggieCardIndex += 2;
        }
        return pileString.toString();
    }

}
