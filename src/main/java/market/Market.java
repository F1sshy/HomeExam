package market;

import java.util.ArrayList;
import card.card;
import pile.pile;

public class Market implements IMarket {
    private ArrayList<pile> piles;

    public Market(ArrayList<pile> piles) {
        this.piles = (piles != null) ? piles : new ArrayList<>();
    }

    public void replaceMarket() {
        if (piles == null) return;
        for (int i = 0; i < piles.size(); i++) {
            pile p = piles.get(i);
            if (p.isEmpty()) {
                card newCard = drawCardFromLargestPile();
                if (newCard != null) {
                    p.addCard(newCard);
                }
            }
            if (p.areAnyVeggieCardsEmpty()) {
                for (int j = 0; j < p.veggieCards.length; j++) {
                    if (p.veggieCards[j] == null) {
                        card newVeggieCard = drawCardFromLargestPile();
                        if (newVeggieCard != null) {
                            p.veggieCards[j] = newVeggieCard;
                            p.veggieCards[j].criteriaSideUp = false;
                        }
                    }
                }
            }
        }
    }

    private card drawCardFromLargestPile() {
        if (piles == null) return null;
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