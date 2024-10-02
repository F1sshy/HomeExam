package game;

import card.pile;
import card.card;
import main.PointSalad;
import network.server;
import network.client;
import player.player;
import player.bot;

import main.PointSalad;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class logic {

    public ArrayList<PointSalad.Player> players = new ArrayList<>();


    public void setPiles(int nrPlayers) {
        ArrayList<PointSalad.Card> deckPepper = new ArrayList<>();
        ArrayList<PointSalad.Card> deckLettuce = new ArrayList<>();
        ArrayList<PointSalad.Card> deckCarrot = new ArrayList<>();
        ArrayList<PointSalad.Card> deckCabbage = new ArrayList<>();
        ArrayList<PointSalad.Card> deckOnion = new ArrayList<>();
        ArrayList<PointSalad.Card> deckTomato = new ArrayList<>();

        try (InputStream fInputStream = new FileInputStream("src/main.main/resources/PointSaladManifest.json");
             Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

            // Read the entire JSON file into a String
            String jsonString = scanner.hasNext() ? scanner.next() : "";

            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Get the "cards" array from the JSONObject
            JSONArray cardsArray = jsonObject.getJSONArray("cards");

            // Iterate over each card.card in the array
            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardJson = cardsArray.getJSONObject(i);

                // Get the criteria object from the card.card JSON
                JSONObject criteriaObj = cardJson.getJSONObject("criteria");

                // Add each vegetable card.card to the deck with its corresponding criteria
                deckPepper.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
                deckLettuce.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
                deckCarrot.add(new PointSalad.Card(PointSalad.Card.Vegetable.CARROT, criteriaObj.getString("CARROT")));
                deckCabbage.add(new PointSalad.Card(PointSalad.Card.Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
                deckOnion.add(new PointSalad.Card(PointSalad.Card.Vegetable.ONION, criteriaObj.getString("ONION")));
                deckTomato.add(new PointSalad.Card(PointSalad.Card.Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shuffle each deck
        shuffleDeck(deckPepper);
        shuffleDeck(deckLettuce);
        shuffleDeck(deckCarrot);
        shuffleDeck(deckCabbage);
        shuffleDeck(deckOnion);
        shuffleDeck(deckTomato);

        int cardsPerVeggie = nrPlayers/2 * 6;

        ArrayList<PointSalad.Card> deck = new ArrayList<>();
        for(int i = 0; i < cardsPerVeggie; i++) {
            deck.add(deckPepper.remove(0));
            deck.add(deckLettuce.remove(0));
            deck.add(deckCarrot.remove(0));
            deck.add(deckCabbage.remove(0));
            deck.add(deckOnion.remove(0));
            deck.add(deckTomato.remove(0));
        }
        shuffleDeck(deck);

        //divide the deck into 3 piles
        ArrayList<PointSalad.Card> pile1 = new ArrayList<>();
        ArrayList<PointSalad.Card> pile2 = new ArrayList<>();
        ArrayList<PointSalad.Card> pile3 = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            if (i % 3 == 0) {
                pile1.add(deck.get(i));
            } else if (i % 3 == 1) {
                pile2.add(deck.get(i));
            } else {
                pile3.add(deck.get(i));
            }
        }
        pile.piles.add(new pile(pile1));
        pile.piles.add(new pile(pile2));
        pile.piles.add(new pile(pile3));
    }

    public int calculateScore(ArrayList<PointSalad.Card> hand, PointSalad.Player thisPlayer) {
        //System.out.println("DEBUG: \n" + displayHand(hand));
        int totalScore = 0;

        for (PointSalad.Card criteriaCard : hand) {
            if (criteriaCard.criteriaSideUp) {
                String criteria = criteriaCard.criteria;
                String[] parts = criteria.split(",");

                //ID 18
                if(criteria.indexOf("TOTAL") >= 0 || criteria.indexOf("TYPE") >= 0 || criteria.indexOf("SET") >= 0) {
                    if(criteria.indexOf("TOTAL")>=0) {
                        int countVeg = countTotalVegetables(hand);
                        int thisHandCount = countVeg;
                        for(PointSalad.Player p : players) {
                            if(p.playerID != thisPlayer.playerID) {
                                int playerVeg = countTotalVegetables(p.hand);
                                if((criteria.indexOf("MOST")>=0) && (playerVeg > countVeg)) {
                                    countVeg = countTotalVegetables(p.hand);
                                }
                                if((criteria.indexOf("FEWEST")>=0) && (playerVeg < countVeg)) {
                                    countVeg = countTotalVegetables(p.hand);
                                }
                            }
                        }
                        if(countVeg == thisHandCount) {
                            //int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
                            //System.out.print("ID18 MOST/FEWEST: "+aScore + " " );
                            totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
                        }
                    }
                    if(criteria.indexOf("TYPE")>=0) {
                        String[] expr = criteria.split("/");
                        int addScore = Integer.parseInt(expr[0].trim());
                        if(expr[1].indexOf("MISSING")>=0) {
                            int missing = 0;
                            for (PointSalad.Card.Vegetable vegetable : PointSalad.Card.Vegetable.values()) {
                                if(countVegetables(hand, vegetable) == 0) {
                                    missing++;
                                }
                            }
                            //int aScore = missing * addScore;
                            //System.out.print("ID18 TYPE MISSING: "+aScore + " ");
                            totalScore += missing * addScore;
                        }
                        else {
                            int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=")+2).trim());
                            int totalType = 0;
                            for(PointSalad.Card.Vegetable vegetable : PointSalad.Card.Vegetable.values()) {
                                int countVeg = countVegetables(hand, vegetable);
                                if(countVeg >= atLeastPerVegType) {
                                    totalType++;
                                }
                            }
                            //int aScore = totalType * addScore;
                            //System.out.print("ID18 TYPE >=: "+aScore + " ");
                            totalScore += totalType * addScore;
                        }
                    }
                    if(criteria.indexOf("SET")>=0) {
                        int addScore = 12;
                        for (PointSalad.Card.Vegetable vegetable : PointSalad.Card.Vegetable.values()) {
                            int countVeg = countVegetables(hand, vegetable);
                            if(countVeg == 0) {
                                addScore = 0;
                                break;
                            }
                        }
                        //System.out.print("ID18 SET: "+addScore + " ");
                        totalScore += addScore;
                    }
                }
                //ID1 and ID2
                else if((criteria.indexOf("MOST")>=0) || (criteria.indexOf("FEWEST")>=0)) { //ID1, ID2
                    int vegIndex = criteria.indexOf("MOST")>=0 ? criteria.indexOf("MOST")+5 : criteria.indexOf("FEWEST")+7;
                    String veg = criteria.substring(vegIndex, criteria.indexOf("=")).trim();
                    int countVeg = countVegetables(hand, PointSalad.Card.Vegetable.valueOf(veg));
                    int nrVeg = countVeg;
                    for(PointSalad.Player p : players) {
                        if(p.playerID != thisPlayer.playerID) {
                            int playerVeg = countVegetables(p.hand, PointSalad.Card.Vegetable.valueOf(veg));
                            if((criteria.indexOf("MOST")>=0) && (playerVeg > nrVeg)) {
                                nrVeg = countVegetables(p.hand, PointSalad.Card.Vegetable.valueOf(veg));
                            }
                            if((criteria.indexOf("FEWEST")>=0) && (playerVeg < nrVeg)) {
                                nrVeg = countVegetables(p.hand, PointSalad.Card.Vegetable.valueOf(veg));
                            }
                        }
                    }
                    if(nrVeg == countVeg) {
                        //System.out.print("ID1/ID2: "+Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim()) + " ");
                        totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
                    }
                }

                //ID3, ID4, ID5, ID6, ID7, ID8, ID9, ID10, ID11, ID12, ID13, ID14, ID15, ID16, ID17
                else if(parts.length > 1 || criteria.indexOf("+")>=0 || parts[0].indexOf("/")>=0) {
                    if(criteria.indexOf("+")>=0) { //ID5, ID6, ID7, ID11, ID12, ID13
                        String expr = criteria.split("=")[0].trim();
                        String[] vegs = expr.split("\\+");
                        int[] nrVeg = new int[vegs.length];
                        int countSameKind = 1;
                        for(int j = 1; j < vegs.length; j++) {
                            if(vegs[0].trim().equals(vegs[j].trim())) {
                                countSameKind++;
                            }
                        }
                        if(countSameKind > 1) {
                            //System.out.print("ID5/ID11: "+ ((int)countVegetables(hand, Card.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
                            totalScore +=  ((int)countVegetables(hand, PointSalad.Card.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
                        } else {
                            for(int i = 0; i < vegs.length; i++) {
                                nrVeg[i] = countVegetables(hand, PointSalad.Card.Vegetable.valueOf(vegs[i].trim()));
                            }
                            //find the lowest number in the nrVeg array
                            int min = nrVeg[0];
                            for (int x = 1; x < nrVeg.length; x++) {
                                if (nrVeg[x] < min) {
                                    min = nrVeg[x];
                                }
                            }
                            //System.out.print("ID6/ID7/ID12/ID13: "+min * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
                            totalScore += min * Integer.parseInt(criteria.split("=")[1].trim());
                        }
                    }
                    else if(parts[0].indexOf("=")>=0) { //ID3
                        String veg = parts[0].substring(0, parts[0].indexOf(":"));
                        int countVeg = countVegetables(hand, PointSalad.Card.Vegetable.valueOf(veg));
                        //System.out.print("ID3: "+((countVeg%2==0)?7:3) + " ");
                        totalScore += (countVeg%2==0)?7:3;
                    }
                    else { //ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
                        for(int i = 0; i < parts.length; i++) {
                            String[] veg = parts[i].split("/");
                            //System.out.print("ID4/ID8/ID9/ID10/ID14/ID15/ID16/ID17: " + Integer.parseInt(veg[0].trim()) * countVegetables(hand, Card.Vegetable.valueOf(veg[1].trim())) + " ");
                            totalScore += Integer.parseInt(veg[0].trim()) * countVegetables(hand, PointSalad.Card.Vegetable.valueOf(veg[1].trim()));
                        }
                    }
                }
            }
        }
        return totalScore;
    }

    private int countVegetables(ArrayList<PointSalad.Card> hand, PointSalad.Card.Vegetable vegetable) {
        int count = 0;
        for (PointSalad.Card card : hand) {
            if (!card.criteriaSideUp && card.vegetable == vegetable) {
                count++;
            }
        }
        return count;
    }

    private int countTotalVegetables(ArrayList<PointSalad.Card> hand) {
        int count = 0;
        for (PointSalad.Card card : hand) {
            if (!card.criteriaSideUp) {
                count++;
            }
        }
        return count;
    }

    public void shuffleDeck(ArrayList<PointSalad.Card> deck) {
        Collections.shuffle(deck);
    }

    public String displayHand(ArrayList<PointSalad.Card> hand) {
        String handString = "Criteria:\t";
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i).criteriaSideUp && hand.get(i).vegetable != null) {
                handString += "["+i+"] "+hand.get(i).criteria + " ("+hand.get(i).vegetable.toString()+")"+"\t";
            }
        }
        handString += "\nVegetables:\t";
        //Sum up the number of each vegetable and show the total number of each vegetable
        for (PointSalad.Card.Vegetable vegetable : PointSalad.Card.Vegetable.values()) {
            int count = countVegetables(hand, vegetable);
            if(count > 0) {
                handString += vegetable + ": " + count + "\t";
            }
        }
        return handString;
    }
}
