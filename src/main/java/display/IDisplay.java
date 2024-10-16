package display;

import java.util.ArrayList;

import card.card;
import card.pile;


public interface IDisplay {

    String displayMarket(ArrayList<pile> piles);

    static String displayHand(ArrayList<card> hand) {
        return null;
    }
}
