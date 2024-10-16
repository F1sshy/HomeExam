package display;

import card.card;
import card.pile;
import game.logic.CardUtils;

import java.util.ArrayList;

public class Display implements IDisplay {


    public static String displayHand(ArrayList<card> hand) {
        StringBuilder handString = new StringBuilder("Criteria:\t");
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).criteriaSideUp && hand.get(i).vegetable != null) {
                handString.append("[").append(i).append("] ").append(hand.get(i).criteria).append(" (").append(hand.get(i).vegetable.toString()).append(")\t");
            }
        }
        handString.append("\nVegetables:\t");
        for (card.Vegetable vegetable : card.Vegetable.values()) {
            int count = CardUtils.countVegetables(hand, vegetable);
            if (count > 0) {
                handString.append(vegetable).append(": ").append(count).append("\t");
            }
        }
        return handString.toString();
    }

    public String displayMarket(ArrayList<pile> piles) {
        String pileString = "Point Cards:\t";
        for (int p=0; p<piles.size(); p++) {
            if(piles.get(p).getPointCard()==null) {
                pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
            }
            else
                pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPointCard()) + "\t";
        }
        pileString += "\nVeggie Cards:\t";
        char veggieCardIndex = 'A';
        for (pile pile : piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(0)) + "\t";
            veggieCardIndex++;
        }
        pileString += "\n\t\t";
        for (pile pile : piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(1)) + "\t";
            veggieCardIndex++;
        }
        return pileString;
    }

}
