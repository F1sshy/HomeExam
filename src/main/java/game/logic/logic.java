package game.logic;

import main.PointSalad;
import card.card;
import java.util.Collections;

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

    public int calculateScore(ArrayList<card> hand, player.player thisPlayer, ArrayList<player.player> players) {
        return scoreCalculator.calculateScore(hand, thisPlayer, players);
    }

    public String displayHand(ArrayList<card> hand) {
        return cardUtils.displayHand(hand);
    }

    public int countVegetables(ArrayList<card> hand, card.Vegetable vegetable) {
        int count = 0;
        for (card card : hand) {
            if (!card.criteriaSideUp && card.vegetable == vegetable) {
                count++;
            }
        }
        return count;
    }

    public int countTotalVegetables(ArrayList<card> hand) {
        int count = 0;
        for (card card : hand) {
            if (!card.criteriaSideUp) {
                count++;
            }
        }
        return count;
    }

    public void shuffleDeck(ArrayList<card> deck) {
        Collections.shuffle(deck);
    }
}

