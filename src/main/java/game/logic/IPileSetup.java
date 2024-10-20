package game.logic;

import java.util.ArrayList;

import pile.VeggiePile;

public interface IPileSetup {
    void setPiles(int nrPlayers);
    ArrayList<VeggiePile> getPiles();

}
