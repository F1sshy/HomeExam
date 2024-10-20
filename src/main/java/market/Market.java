package market;

import java.util.ArrayList;
import card.VeggieCard;
import pile.VeggiePile;

public class Market implements IMarket {
    private ArrayList<VeggiePile> veggiePiles;
    private static Market instance;

    /**
     * Gets the size of the pile.
     *
     * @return the number of cards in the pile
     */
    public Market(ArrayList<VeggiePile> veggiePiles) {
        this.veggiePiles = (veggiePiles != null) ? veggiePiles : new ArrayList<>();
    }

    public static Market getInstance(ArrayList<VeggiePile> veggiePiles) {
        if (instance == null) {
            instance = new Market(veggiePiles);
        }
        return instance;
    }

    /**
     * Replaces empty piles and empty veggie cards in the market with new cards.
     */
    public void replaceMarket() {
        if (veggiePiles == null) return;
        for (int i = 0; i < veggiePiles.size(); i++) {
            VeggiePile p = veggiePiles.get(i);
            if (p.isEmpty()) {
                VeggieCard newVeggieCard = drawCardFromLargestPile();
                if (newVeggieCard != null) {
                    p.addCard(newVeggieCard);
                }
            }
            if (p.areAnyVeggieCardsEmpty()) {
                for (int j = 0; j < p.veggieVeggieCards.length; j++) {
                    if (p.veggieVeggieCards[j] == null) {
                        VeggieCard newVeggieVeggieCard = drawCardFromLargestPile();
                        if (newVeggieVeggieCard != null) {
                            p.veggieVeggieCards[j] = newVeggieVeggieCard;
                            p.veggieVeggieCards[j].criteriaSideUp = false;
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
    private VeggieCard drawCardFromLargestPile() {
        if (veggiePiles == null) return null;
        int largestPileIndex = -1;
        int largestSize = 0;

        for (int i = 0; i < veggiePiles.size(); i++) {
            if (veggiePiles.get(i).size() > largestSize) {
                largestSize = veggiePiles.get(i).size();
                largestPileIndex = i;
            }
        }

        if (largestPileIndex != -1 && largestSize > 0) {
            return veggiePiles.get(largestPileIndex).removeCard();
        }

        return null;
    }

    /**
     * Gets the list of piles in the market.
     *
     * @return the list of piles
     */
    public ArrayList<VeggiePile> getPiles() {
        return veggiePiles;
    }
}