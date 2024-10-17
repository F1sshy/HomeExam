package game.logic;

import pile.pile;
import card.card;
import card.card.Vegetable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class pileSetup {

    ArrayList<pile> piles = new ArrayList<>();

    public void setPiles(int nrPlayers) {
        ArrayList<card> deck = createDeck(nrPlayers);
        shuffleDeck(deck);
        divideDeckIntoPiles(deck);
    }

    private ArrayList<card> createDeck(int nrPlayers) {
        ArrayList<card> deck = new ArrayList<>();
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

                deckPepper.add(new card(Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
                deckLettuce.add(new card(Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
                deckCarrot.add(new card(Vegetable.CARROT, criteriaObj.getString("CARROT")));
                deckCabbage.add(new card(Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
                deckOnion.add(new card(Vegetable.ONION, criteriaObj.getString("ONION")));
                deckTomato.add(new card(Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        int cardsPerVeggie = (int)(((float) nrPlayers / 2) * 6);
        for (int i = 0; i < cardsPerVeggie; i++) {
            deck.add(deckPepper.remove(0));
            deck.add(deckLettuce.remove(0));
            deck.add(deckCarrot.remove(0));
            deck.add(deckCabbage.remove(0));
            deck.add(deckOnion.remove(0));
            deck.add(deckTomato.remove(0));
        }

        return deck;
    }

    private void shuffleDeck(ArrayList<card> deck) {
        Collections.shuffle(deck);
    }

    private void divideDeckIntoPiles(ArrayList<card> deck) {
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

    public ArrayList<pile> getPiles() {
        return piles;
    }

}