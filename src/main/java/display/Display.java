package display;

import card.VeggieCard;
import pile.Pile;
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
    public static String displayHand(ArrayList<VeggieCard> hand) {
        StringBuilder handString = new StringBuilder("Criteria:\t");
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).criteriaSideUp && hand.get(i).getVegetable() != null) {
                handString.append("[").append(i).append("] ").append(hand.get(i).criteria).append(" (").append(hand.get(i).getVegetable().toString()).append(")\t");
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
     * @param Piles the list of piles in the market
     * @return a string representation of the market
     */
    public String displayMarket(ArrayList<Pile> Piles) {
        String pileString = "Point Cards:\t";
        for (int p = 0; p< Piles.size(); p++) {
            if(Piles.get(p).getPointCard()==null) {
                pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
            }
            else
                pileString += "["+p+"]"+String.format("%-43s", Piles.get(p).getPointCard()) + "\t";
        }
        pileString += "\nVeggie Cards:\t";
        char veggieCardIndex = 'A';
        for (Pile pile : Piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(0)) + "\t";
            veggieCardIndex+=2;
        }
        pileString += "\n\t\t";
        veggieCardIndex = 'B';
        for (Pile pile : Piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(1)) + "\t";
            veggieCardIndex+=2;
        }
        return pileString;
    }

}
