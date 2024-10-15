package card;

import java.util.ArrayList;

public class Market {

    private ArrayList<pile> piles;

    public Market(ArrayList<pile> piles) {
        this.piles = piles;
    }
/*
    private String printMarket() {
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
   */
}
