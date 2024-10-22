package market;

import java.util.ArrayList;

import card.ICard;
import pile.VeggiePile;

public class VeggieMarket implements IMarket {
    private ArrayList<VeggiePile> veggiePiles;
    private static VeggieMarket instance;

    public VeggieMarket(ArrayList<VeggiePile> veggiePiles) {
        this.veggiePiles = (veggiePiles != null) ? veggiePiles : new ArrayList<>();
    }

    public static VeggieMarket getInstance(ArrayList<VeggiePile> veggiePiles) {
        if (instance == null) {
            instance = new VeggieMarket(veggiePiles);
        }
        return instance;
    }

    /**
     * Replaces the market with new cards if any piles are empty.
     */
    public void replaceMarket() {
        if (veggiePiles == null) return;
        for (VeggiePile p : veggiePiles) {
            if (p.areAnyVeggieCardsEmpty()) {
                for (int j = 0; j < p.veggieVeggieCards.length; j++) {
                    if (p.veggieVeggieCards[j] == null) {
                        ICard newVeggieCard = drawCardFromTopOfPile(p);
                        if (newVeggieCard == null) {
                            newVeggieCard = drawCardFromBottomOfLargestPile();
                        }
                        if (newVeggieCard != null) {
                            p.veggieVeggieCards[j] = newVeggieCard;
                            p.veggieVeggieCards[j].setCriteriaSideUp(false);
                        }
                    }
                }
            }

            if (p.isEmpty()) {
                ICard newPointCard = drawCardFromTopOfPile(p);
                if (newPointCard == null) {
                    newPointCard = drawCardFromBottomOfLargestPile();
                }
                if (newPointCard != null) {
                    p.addCard(newPointCard);
                }
            }

        }
    }

    /**
     * Draws a card from the top of the pile.
     *
     * @param pile the pile to draw from
     * @return the card drawn from the pile
     */
    private ICard drawCardFromTopOfPile(VeggiePile pile) {
        return pile.removeCard(0);
    }

    /**
     * Draws a card from the bottom of the largest pile.
     *
     * @return the card drawn from the pile
     */
    private ICard drawCardFromBottomOfLargestPile() {
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
            return veggiePiles.get(largestPileIndex).removeCard(veggiePiles.get(largestPileIndex).size() - 1);
        }

        return null;
    }

    public ArrayList<VeggiePile> getPiles() {
        return veggiePiles;
    }
}