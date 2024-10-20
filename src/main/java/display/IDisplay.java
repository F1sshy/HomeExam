package display;

import java.util.ArrayList;

import card.VeggieCard;
import pile.VeggiePile;


public interface IDisplay {

    String displayMarket(ArrayList<VeggiePile> veggiePiles);

    static String displayHand(ArrayList<VeggieCard> hand) {
        return null;
    }
}
