package market;

import pile.pile;
import card.card;

public interface IMarket {
    void replaceMarket();

    private card drawCardFromPile(pile p) {
        return drawCardFromLargestPile();

    }

    private card drawCardFromLargestPile() {
        return null;
    }

}
