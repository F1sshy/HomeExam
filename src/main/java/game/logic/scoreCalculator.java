package game.logic;

import card.card;
import player.Player;
import game.logic.CardUtils;
import java.util.ArrayList;

public class scoreCalculator {

    public static int calculateScore(ArrayList<card> hand, Player thisPlayer, ArrayList<Player> players) {
        int totalScore = 0;

        for (card criteriaCard : hand) {
            if (criteriaCard.criteriaSideUp) {
                String criteria = criteriaCard.criteria;
                System.out.println("Evaluating criteria: " + criteria);

                if (criteria.contains("TOTAL")) {
                    int countVeg = CardUtils.countTotalVegetables(hand);
                    int thisHandCount = countVeg;
                    for (Player p : players) {
                        if (p.getPlayerID() != thisPlayer.getPlayerID()) {
                            int playerVeg = CardUtils.countTotalVegetables(p.getHand());
                            if (criteria.contains("MOST") && playerVeg > countVeg) {
                                countVeg = playerVeg;
                            }
                            if (criteria.contains("FEWEST") && playerVeg < countVeg) {
                                countVeg = playerVeg;
                            }
                        }
                    }
                    if (countVeg == thisHandCount) {
                        int scoreToAdd = Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim());
                        System.out.println("Adding score: " + scoreToAdd);
                        totalScore += scoreToAdd;
                    }
                }

                if (criteria.contains("TYPE")) {
                    String[] expr = criteria.split("/");
                    int addScore = Integer.parseInt(expr[0].trim());
                    if (expr[1].contains("MISSING")) {
                        int missing = 0;
                        for (card.Vegetable vegetable : card.Vegetable.values()) {
                            if (CardUtils.countVegetables(hand, vegetable) == 0) {
                                missing++;
                            }
                        }
                        System.out.println("Adding score for missing types: " + (missing * addScore));
                        totalScore += missing * addScore;
                    } else {
                        int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=") + 2).trim());
                        int totalType = 0;
                        for (card.Vegetable vegetable : card.Vegetable.values()) {
                            int countVeg = CardUtils.countVegetables(hand, vegetable);
                            if (countVeg >= atLeastPerVegType) {
                                totalType++;
                            }
                        }
                        System.out.println("Adding score for types: " + (totalType * addScore));
                        totalScore += totalType * addScore;
                    }
                }

                if (criteria.contains("SET")) {
                    int addScore = 12;
                    for (card.Vegetable vegetable : card.Vegetable.values()) {
                        int countVeg = CardUtils.countVegetables(hand, vegetable);
                        if (countVeg == 0) {
                            addScore = 0;
                            break;
                        }
                    }
                    System.out.println("Adding score for set: " + addScore);
                    totalScore += addScore;
                }

                // Additional criteria from PointSaladManifest.json
                if (criteria.contains("EVEN") || criteria.contains("ODD")) {
                    String[] parts = criteria.split(":");
                    String[] scores = parts[1].split(",");
                    int evenScore = Integer.parseInt(scores[0].split("=")[1].trim());
                    int oddScore = Integer.parseInt(scores[1].split("=")[1].trim());
                    int count = CardUtils.countVegetables(hand, card.Vegetable.valueOf(parts[0].trim()));
                    if (count % 2 == 0) {
                        totalScore += evenScore;
                    } else {
                        totalScore += oddScore;
                    }
                }

                if (criteria.contains("+")) {
                    String[] parts = criteria.split("\\+");
                    int scoreToAdd = Integer.parseInt(parts[1].split("=")[1].trim());
                    String[] veggies = parts[0].split(" ");
                    boolean allPresent = true;
                    for (String veg : veggies) {
                        if (CardUtils.countVegetables(hand, card.Vegetable.valueOf(veg.trim())) == 0) {
                            allPresent = false;
                            break;
                        }
                    }
                    if (allPresent) {
                        totalScore += scoreToAdd;
                    }
                }

                if (criteria.contains("/")) {
                    String[] parts = criteria.split("/");
                    int scorePerUnit = Integer.parseInt(parts[0].trim());
                    String vegType = parts[1].trim();
                    int count = CardUtils.countVegetables(hand, card.Vegetable.valueOf(vegType));
                    totalScore += scorePerUnit * count;
                }

                if (criteria.contains("-")) {
                    String[] parts = criteria.split("-");
                    int scorePerUnit = Integer.parseInt(parts[0].trim());
                    String vegType = parts[1].trim();
                    int count = CardUtils.countVegetables(hand, card.Vegetable.valueOf(vegType));
                    totalScore -= scorePerUnit * count;
                }
            }
        }
        System.out.println("Total score: " + totalScore);
        return totalScore;
    }
}