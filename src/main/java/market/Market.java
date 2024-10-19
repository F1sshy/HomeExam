package market;

import java.util.ArrayList;
import card.Card;
import pile.Pile;

public class Market implements IMarket {
    private ArrayList<Pile> Piles;
    private static Market instance;

    /**
     * Gets the size of the pile.
     *
     * @return the number of cards in the pile
     */
    public Market(ArrayList<Pile> Piles) {
        this.Piles = (Piles != null) ? Piles : new ArrayList<>();
    }

    public static Market getInstance(ArrayList<Pile> Piles) {
        if (instance == null) {
            instance = new Market(Piles);
        }
        return instance;
    }

    /**
     * Replaces empty piles and empty veggie cards in the market with new cards.
     */
    public void replaceMarket() {
        if (Piles == null) return;
        for (int i = 0; i < Piles.size(); i++) {
            Pile p = Piles.get(i);
            if (p.isEmpty()) {
                Card newCard = drawCardFromLargestPile();
                if (newCard != null) {
                    p.addCard(newCard);
                }
            }
            if (p.areAnyVeggieCardsEmpty()) {
                for (int j = 0; j < p.veggieCards.length; j++) {
                    if (p.veggieCards[j] == null) {
                        Card newVeggieCard = drawCardFromLargestPile();
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
    private Card drawCardFromLargestPile() {
        if (Piles == null) return null;
        int largestPileIndex = -1;
        int largestSize = 0;

        for (int i = 0; i < Piles.size(); i++) {
            if (Piles.get(i).size() > largestSize) {
                largestSize = Piles.get(i).size();
                largestPileIndex = i;
            }
        }

        if (largestPileIndex != -1 && largestSize > 0) {
            return Piles.get(largestPileIndex).removeCard();
        }

        return null;
    }

    /**
     * Gets the list of piles in the market.
     *
     * @return the list of piles
     */
    public ArrayList<Pile> getPiles() {
        return Piles;
    }
}