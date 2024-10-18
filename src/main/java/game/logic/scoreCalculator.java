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
                    totalScore += handleTotalCriteria(criteria, hand, thisPlayer, players);
                } else if (criteria.contains("TYPE")) {
                    totalScore += handleTypeCriteria(criteria, hand);
                } else if (criteria.contains("SET")) {
                    totalScore += handleSetCriteria(hand);
                } else if (criteria.contains("EVEN") || criteria.contains("ODD")) {
                    totalScore += handleEvenOddCriteria(criteria, hand);
                } else if (criteria.contains("+")) {
                    totalScore += handleAdditionCriteria(criteria, hand);
                } else if (criteria.contains("/")) {
                    totalScore += handleDivisionCriteria(criteria, hand);
                } else if (criteria.contains("-")) {
                    totalScore += handleSubtractionCriteria(criteria, hand);
                }
            }
        }
        System.out.println("Total score: " + totalScore);
        return totalScore;
    }

    private static int handleTotalCriteria(String criteria, ArrayList<card> hand, Player thisPlayer, ArrayList<Player> players) {
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
            return Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim());
        }
        return 0;
    }

    private static int handleTypeCriteria(String criteria, ArrayList<card> hand) {
        String[] expr = criteria.split("/");
        int addScore = Integer.parseInt(expr[0].trim());
        if (expr[1].contains("MISSING")) {
            int missing = 0;
            for (card.Vegetable vegetable : card.Vegetable.values()) {
                if (CardUtils.countVegetables(hand, vegetable) == 0) {
                    missing++;
                }
            }
            return missing * addScore;
        } else {
            int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=") + 2).trim());
            int totalType = 0;
            for (card.Vegetable vegetable : card.Vegetable.values()) {
                if (CardUtils.countVegetables(hand, vegetable) >= atLeastPerVegType) {
                    totalType++;
                }
            }
            return totalType * addScore;
        }
    }

    private static int handleSetCriteria(ArrayList<card> hand) {
        int addScore = 12;
        for (card.Vegetable vegetable : card.Vegetable.values()) {
            if (CardUtils.countVegetables(hand, vegetable) == 0) {
                addScore = 0;
                break;
            }
        }
        return addScore;
    }

    private static int handleEvenOddCriteria(String criteria, ArrayList<card> hand) {
        String[] parts = criteria.split(":");
        String[] scores = parts[1].split(",");
        int evenScore = Integer.parseInt(scores[0].split("=")[1].trim());
        int oddScore = Integer.parseInt(scores[1].split("=")[1].trim());
        int count = CardUtils.countVegetables(hand, card.Vegetable.valueOf(parts[0].trim()));
        return (count % 2 == 0) ? evenScore : oddScore;
    }

    private static int handleAdditionCriteria(String criteria, ArrayList<card> hand) {
        String[] parts = criteria.split("\\+");
        int scoreToAdd = Integer.parseInt(parts[1].split("=")[1].trim());
        String[] veggies = parts[0].split(" ");
        for (String veg : veggies) {
            if (CardUtils.countVegetables(hand, card.Vegetable.valueOf(veg.trim())) == 0) {
                return 0;
            }
        }
        return scoreToAdd;
    }

    private static int handleDivisionCriteria(String criteria, ArrayList<card> hand) {
        String[] parts = criteria.split("/");
        int scorePerUnit = Integer.parseInt(parts[0].trim());
        String vegType = parts[1].trim();
        int count = CardUtils.countVegetables(hand, card.Vegetable.valueOf(vegType));
        return scorePerUnit * count;
    }

    private static int handleSubtractionCriteria(String criteria, ArrayList<card> hand) {
        String[] parts = criteria.split("-");
        int scorePerUnit = Integer.parseInt(parts[0].trim());
        String vegType = parts[1].trim();
        int count = CardUtils.countVegetables(hand, card.Vegetable.valueOf(vegType));
        return -scorePerUnit * count;
    }
}