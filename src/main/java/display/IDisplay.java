package display;

import java.util.ArrayList;

import card.VeggieCard;
import pile.Pile;


public interface IDisplay {

    String displayMarket(ArrayList<Pile> Piles);

    static String displayHand(ArrayList<VeggieCard> hand) {
        return null;
    }
}
