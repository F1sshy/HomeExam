package display;

import java.util.ArrayList;

import card.card;
import card.pile;
import interfaces.IPlayer;
import interfaces.IPile;


public interface IDisplay {

    String displayMarket(ArrayList<pile> piles);

    static String displayHand(ArrayList<card> hand) {
        return null;
    }
}
