package game.logic;

import pile.Pile;
import card.Card;
import card.Vegetable;
import card.CardFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class VeggiePileSetup implements IPileSetup {

    ArrayList<Pile> Piles = new ArrayList<>();

    /**
     * Sets up the piles based on the number of players.
     *
     * @param nrPlayers the number of players
     */
    public void setPiles(int nrPlayers) {
        ArrayList<Card> deck = createDeck(nrPlayers);
        shuffleDeck(deck);
        divideDeckIntoPiles(deck);
    }

    /**
     * Creates a deck of cards based on the number of players.
     *
     * @param nrPlayers the number of players
     * @return the created deck of cards
     */
    private ArrayList<Card> createDeck(int nrPlayers) {
        ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> deckPepper = new ArrayList<>();
        ArrayList<Card> deckLettuce = new ArrayList<>();
        ArrayList<Card> deckCarrot = new ArrayList<>();
        ArrayList<Card> deckCabbage = new ArrayList<>();
        ArrayList<Card> deckOnion = new ArrayList<>();
        ArrayList<Card> deckTomato = new ArrayList<>();

        try (InputStream fInputStream = new FileInputStream("src/main/resources/PointSaladManifest.json");
             Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

            String jsonString = scanner.hasNext() ? scanner.next() : "";
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray cardsArray = jsonObject.getJSONArray("cards");

            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardJson = cardsArray.getJSONObject(i);
                JSONObject criteriaObj = cardJson.getJSONObject("criteria");

                deckPepper.add(CardFactory.createCard(Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
                deckLettuce.add(CardFactory.createCard(Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
                deckCarrot.add(CardFactory.createCard(Vegetable.CARROT, criteriaObj.getString("CARROT")));
                deckCabbage.add(CardFactory.createCard(Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
                deckOnion.add(CardFactory.createCard(Vegetable.ONION, criteriaObj.getString("ONION")));
                deckTomato.add(CardFactory.createCard(Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
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

    /**
     * Shuffles the deck of cards.
     *
     * @param deck the deck of cards to be shuffled
     */
    private void shuffleDeck(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    /**
     * Divides the deck of cards into piles.
     *
     * @param deck the deck of cards to be divided
     */
    private void divideDeckIntoPiles(ArrayList<Card> deck) {
        ArrayList<Card> pile1 = new ArrayList<>();
        ArrayList<Card> pile2 = new ArrayList<>();
        ArrayList<Card> pile3 = new ArrayList<>();

        for (int i = 0; i < deck.size(); i++) {
            if (i % 3 == 0) {
                pile1.add(deck.get(i));
            } else if (i % 3 == 1) {
                pile2.add(deck.get(i));
            } else {
                pile3.add(deck.get(i));
            }
        }

        Piles.add(new Pile(pile1));
        Piles.add(new Pile(pile2));
        Piles.add(new Pile(pile3));

    }

    /**
     * Gets the piles of cards.
     *
     * @return the piles of cards
     */
    public ArrayList<Pile> getPiles() {
        return Piles;
    }

}