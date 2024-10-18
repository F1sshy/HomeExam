package market;

import java.util.ArrayList;
import card.card;
import pile.pile;

public class Market implements IMarket {
    private ArrayList<pile> piles;

    /**
     * Gets the size of the pile.
     *
     * @return the number of cards in the pile
     */
    public Market(ArrayList<pile> piles) {
        this.piles = (piles != null) ? piles : new ArrayList<>();
    }

    /**
     * Replaces empty piles and empty veggie cards in the market with new cards.
     */
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

    /**
     * Draws a card from the largest pile in the market.
     *
     * @return the drawn card, or null if no card can be drawn
     */
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

    /**
     * Gets the list of piles in the market.
     *
     * @return the list of piles
     */
    public ArrayList<pile> getPiles() {
        return piles;
    }
}