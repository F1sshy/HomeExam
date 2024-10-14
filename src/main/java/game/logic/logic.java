package game.logic;

import main.PointSalad;
import card.card;


import java.util.ArrayList;

public class logic {
    private pileSetup pileSetup;
    private scoreCalculator scoreCalculator;

    public logic() {
        this.pileSetup = new pileSetup();
        this.scoreCalculator = new scoreCalculator();
    }

    public void setPiles(int nrPlayers) {
        pileSetup.setPiles(nrPlayers);
    }

    public int calculateScore(ArrayList<card.card> hand, player.player thisPlayer, ArrayList<player.player> players) {
        return scoreCalculator.calculateScore(hand, thisPlayer, players);
    }

    public String displayHand(ArrayList<card.card> hand) {
        return cardUtils.displayHand(hand);
    }
}