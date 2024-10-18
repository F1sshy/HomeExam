package game.logic;

import java.util.ArrayList;
import card.card;
import pile.pile;

public interface IPileSetup {
    void setPiles(int nrPlayers);
    ArrayList<pile> getPiles();

}
