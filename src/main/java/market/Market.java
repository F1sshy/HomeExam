package market;

import java.util.ArrayList;
import card.card;
import pile.pile;

public class Market implements IMarket {
    private ArrayList<pile> piles;

    public Market(ArrayList<pile> piles) {
        this.piles = piles;
    }

    public void replaceMarket() {
        for (int i = 0; i < piles.size(); i++) {
            pile p = piles.get(i);
            if (p.isEmpty() || p.areVeggieCardsEmpty()) {
                card newCard = drawCardFromPile(p);
                if (newCard != null) {
                    p.addCard(newCard);
                }
            }
        }
    }

    private card drawCardFromPile(pile p) {
        if (!p.isEmpty()) {
            return p.removeCard();
        } else {
            return drawCardFromLargestPile();
        }
    }

    private card drawCardFromLargestPile() {
        int largestPileIndex = -1;
        int largestSize = 0;

        for (int i = 0; i < piles.size(); i++) {
            if (piles.get(i).size() > largestSize) {
                largestSize = piles.get(i).size();
                largestPileIndex = i;
            }
        }

        if (largestPileIndex != -1 && largestSize > 0) {
            return piles.get(largestPileIndex).removeCard();
        }

        return null;
    }

    public ArrayList<pile> getPiles() {
        return piles;
    }
}