package card;

import interfaces.IPile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class pile implements IPile {


    public ArrayList<card> cards = new ArrayList<>();
    public card[] veggieCards = new card[2];

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
        return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
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

    public void setPiles(int nrPlayers) {
        ArrayList<card> deckPepper = new ArrayList<>();
        ArrayList<card> deckLettuce = new ArrayList<>();
        ArrayList<card> deckCarrot = new ArrayList<>();
        ArrayList<card> deckCabbage = new ArrayList<>();
        ArrayList<card> deckOnion = new ArrayList<>();
        ArrayList<card> deckTomato = new ArrayList<>();

        try (InputStream fInputStream = new FileInputStream("src/main/resources/PointSaladManifest.json");
             Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

            String jsonString = scanner.hasNext() ? scanner.next() : "";
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray cardsArray = jsonObject.getJSONArray("cards");

            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardJson = cardsArray.getJSONObject(i);
                JSONObject criteriaObj = cardJson.getJSONObject("criteria");

                deckPepper.add(new card(card.Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
                deckLettuce.add(new card(card.Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
                deckCarrot.add(new card(card.Vegetable.CARROT, criteriaObj.getString("CARROT")));
                deckCabbage.add(new card(card.Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
                deckOnion.add(new card(card.Vegetable.ONION, criteriaObj.getString("ONION")));
                deckTomato.add(new card(card.Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        shuffleDeck(deckPepper);
        shuffleDeck(deckLettuce);
        shuffleDeck(deckCarrot);
        shuffleDeck(deckCabbage);
        shuffleDeck(deckOnion);
        shuffleDeck(deckTomato);

        int cardsPerVeggie = nrPlayers / 2 * 6;

        ArrayList<card> deck = new ArrayList<>();
        for (int i = 0; i < cardsPerVeggie; i++) {
            deck.add(deckPepper.remove(0));
            deck.add(deckLettuce.remove(0));
            deck.add(deckCarrot.remove(0));
            deck.add(deckCabbage.remove(0));
            deck.add(deckOnion.remove(0));
            deck.add(deckTomato.remove(0));
        }
        shuffleDeck(deck);

        ArrayList<card> pile1 = new ArrayList<>();
        ArrayList<card> pile2 = new ArrayList<>();
        ArrayList<card> pile3 = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            if (i % 3 == 0) {
                pile1.add(deck.get(i));
            } else if (i % 3 == 1) {
                pile2.add(deck.get(i));
            } else {
                pile3.add(deck.get(i));
            }
        }
        piles.add(new pile(pile1));
        piles.add(new pile(pile2));
        piles.add(new pile(pile3));
    }

    private void shuffleDeck(ArrayList<card> deck) {
        Collections.shuffle(deck);
    }


}