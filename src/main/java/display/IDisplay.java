package display;

import java.util.ArrayList;

import card.Card;
import pile.Pile;


public interface IDisplay {

    String displayMarket(ArrayList<Pile> Piles);

    static String displayHand(ArrayList<Card> hand) {
        return null;
    }
}
