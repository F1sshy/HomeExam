package game.logic;

import card.card;
import player.Player;
import java.util.ArrayList;

public class scoreCalculator {

    public static int calculateScore(ArrayList<card> hand, Player thisPlayer, ArrayList<Player> players) {
        int totalScore = 0;

        for (card criteriaCard : hand) {
            if (criteriaCard.criteriaSideUp) {
                String criteria = criteriaCard.criteria;
                String[] parts = criteria.split(",");

                if (criteria.contains("TOTAL") || criteria.contains("TYPE") || criteria.contains("SET")) {
                    if (criteria.contains("TOTAL")) {
                        int countVeg = CardUtils.countTotalVegetables(hand);
                        int thisHandCount = countVeg;
                        for (Player p : players) {
                            if (p.getPlayerID() != thisPlayer.getPlayerID()) {
                                int playerVeg = CardUtils.countTotalVegetables(p.getHand());
                                if (criteria.contains("MOST") && playerVeg > countVeg) {
                                    countVeg = CardUtils.countVegetables(p.getHand(), criteriaCard.vegetable);
                                }
                                if (criteria.contains("FEWEST") && playerVeg < countVeg) {
                                    countVeg = CardUtils.countVegetables(p.getHand(), criteriaCard.vegetable);
                                }
                            }
                        }
                        if (countVeg == thisHandCount) {
                            totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim());
                        }
                    }
                    if (criteria.contains("TYPE")) {
                        String[] expr = criteria.split("/");
                        int addScore = Integer.parseInt(expr[0].trim());
                        if (expr[1].contains("MISSING")) {
                            int missing = 0;
                            for (card.Vegetable vegetable : card.Vegetable.values()) {
                                if (CardUtils.countTotalVegetables(hand) == 0) {
                                    missing++;
                                }
                            }
                            totalScore += missing * addScore;
                        } else {
                            int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=") + 2).trim());
                            int totalType = 0;
                            for (card.Vegetable vegetable : card.Vegetable.values()) {
                                int countVeg = CardUtils.countTotalVegetables(hand);
                                if (countVeg >= atLeastPerVegType) {
                                    totalType++;
                                }
                            }
                            totalScore += totalType * addScore;
                        }
                    }
                    if (criteria.contains("SET")) {
                        int addScore = 12;
                        for (card.Vegetable vegetable : card.Vegetable.values()) {
                            int countVeg = CardUtils.countTotalVegetables(hand);
                            if (countVeg == 0) {
                                addScore = 0;
                                break;
                            }
                        }
                        totalScore += addScore;
                    }
                }
            }
        }
        return totalScore;
    }
}