package card;

import main.PointSalad;

import java.util.ArrayList;

public class pile {

    public static ArrayList<PointSalad.Pile> piles = new ArrayList<>();



        public ArrayList<PointSalad.Card> cards = new ArrayList<PointSalad.Card>();
        public PointSalad.Card[] veggieCards = new PointSalad.Card[2];

        public pile(ArrayList<PointSalad.Card> cards) {
            this.cards = cards;
            this.veggieCards[0] = cards.remove(0);
            this.veggieCards[1] = cards.remove(0);
            this.veggieCards[0].criteriaSideUp = false;;
            this.veggieCards[1].criteriaSideUp = false;
        }
        public PointSalad.Card getPointCard() {
            if(cards.isEmpty()) {
                //remove from the bottom of the biggest of the other piles
                int biggestPileIndex = 0;
                int biggestSize = 0;
                for(int i = 0; i < piles.size(); i++) {
                    if(i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                        biggestSize = piles.get(i).cards.size();
                        biggestPileIndex = i;
                    }
                }
                if(biggestSize > 1) {
                    cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size()-1));
                } else // we can't remove active point cards from other piles
                    return null;
            }
            return cards.get(0);
        }
        public PointSalad.Card buyPointCard() {
            if(cards.isEmpty()) {
                //remove from the bottom of the biggest of the other piles
                int biggestPileIndex = 0;
                int biggestSize = 0;
                for(int i = 0; i < piles.size(); i++) {
                    if(i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                        biggestSize = piles.get(i).cards.size();
                        biggestPileIndex = i;
                    }
                }
                if(biggestSize > 1) {
                    cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size()-1));
                } else { // we can't remove active point cards from other piles
                    return null;
                }
            }
            return cards.remove(0);
        }
        public PointSalad.Card getVeggieCard(int index) {
            return veggieCards[index];
        }
        public PointSalad.Card buyVeggieCard(int index) {
            PointSalad.Card aCard = veggieCards[index];
            if(cards.size() <=1) {
                //remove from the bottom of the biggest of the other piles
                int biggestPileIndex = 0;
                int biggestSize = 0;
                for(int i = 0; i < piles.size(); i++) {
                    if(i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                        biggestSize = piles.get(i).cards.size();
                        biggestPileIndex = i;
                    }
                }
                if(biggestSize > 1) {
                    cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size()-1));
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
        public boolean isEmpty() {
            return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
        }
    }

