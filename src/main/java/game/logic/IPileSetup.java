package game.logic;

import java.util.ArrayList;

import pile.Pile;

public interface IPileSetup {
    void setPiles(int nrPlayers);
    ArrayList<Pile> getPiles();

}
