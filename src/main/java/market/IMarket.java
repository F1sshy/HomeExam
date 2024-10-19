package market;

import pile.Pile;
import card.Card;

public interface IMarket {
    void replaceMarket();

    private Card drawCardFromPile(Pile p) {
        return drawCardFromLargestPile();

    }

    private Card drawCardFromLargestPile() {
        return null;
    }

}
