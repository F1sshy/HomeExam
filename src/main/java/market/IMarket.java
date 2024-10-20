package market;

import pile.Pile;
import card.VeggieCard;

public interface IMarket {
    void replaceMarket();

    private VeggieCard drawCardFromPile(Pile p) {
        return drawCardFromLargestPile();

    }

    private VeggieCard drawCardFromLargestPile() {
        return null;
    }

}
