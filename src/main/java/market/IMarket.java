package market;

import pile.VeggiePile;
import card.VeggieCard;

public interface IMarket {
    void replaceMarket();

    private VeggieCard drawCardFromPile(VeggiePile p) {
        return drawCardFromLargestPile();

    }

    private VeggieCard drawCardFromLargestPile() {
        return null;
    }

}
