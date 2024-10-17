package pile;

import card.card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;


public class pile implements IPile {


    public ArrayList<card> cards = new ArrayList<>();
    public card[] veggieCards = new card[2];
    //ArrayList<pile> piles = new ArrayList<>();
    ArrayList<pile> piles;
    private Logger logger = LogManager.getLogger();

    public pile() {
        this.piles = piles;
    }

    public pile(ArrayList<card> cards) {
        this.cards = cards;
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        this.veggieCards[0].criteriaSideUp = false;
        this.veggieCards[1].criteriaSideUp = false;
    }

    public card getPointCard() {
        if (cards.isEmpty()) {
            int biggestPileIndex = 0;
            int biggestSize = 0;
            for (int i = 0; i < piles.size(); i++) {
                if (i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                    biggestSize = piles.get(i).cards.size();
                    biggestPileIndex = i;
                }
            }
            if (biggestSize > 1) {
                cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size() - 1));
            } else {
                return null;
            }
        }
        return cards.get(0);
    }

    public card buyPointCard() {
        if (cards.isEmpty()) {
            int biggestPileIndex = 0;
            int biggestSize = 0;
            for (int i = 0; i < piles.size(); i++) {
                if (i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                    biggestSize = piles.get(i).cards.size();
                    biggestPileIndex = i;
                }
            }
            if (biggestSize > 1) {
                cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size() - 1));
            } else {
                return null;
            }
        }
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean areVeggieCardsEmpty() {
        return veggieCards[0] == null && veggieCards[1] == null;
    }

    public card getVeggieCard(int index) {
        return veggieCards[index];
    }

    public card buyVeggieCard(int index) {
        card aCard = veggieCards[index];
        if (cards.size() <= 1) {
            int biggestPileIndex = 0;
            int biggestSize = 0;
            for (int i = 0; i < piles.size(); i++) {
                if (i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                    biggestSize = piles.get(i).cards.size();
                    biggestPileIndex = i;
                }
            }
            if (biggestSize > 1) {
                cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size() - 1));
                veggieCards[index] = cards.remove(0);
                veggieCards[index].criteriaSideUp = false;
            } else {
                veggieCards[index] = null;
            }
        } else {
            veggieCards[index] = cards.remove(0);
            veggieCards[index].criteriaSideUp = false;
        }

        return aCard;
    }

    public void addCard(card newCard) {
        cards.add(newCard);
    }

    public card removeCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public int size() {
        return cards.size();
    }

}


