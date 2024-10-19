package market;

import java.util.ArrayList;
import pile.Pile;

public class MarketFactory implements IMarketFactory {
    @Override
    public IMarket createMarket(ArrayList<Pile> Piles) {
        return Market.getInstance(Piles);
    }
}