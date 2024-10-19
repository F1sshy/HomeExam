package market;

import java.util.ArrayList;
import pile.Pile;

public interface IMarketFactory {
    IMarket createMarket(ArrayList<Pile> Piles);
}