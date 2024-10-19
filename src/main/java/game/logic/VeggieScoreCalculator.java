package game.logic;

import card.Card;
import player.Player;

import java.util.ArrayList;
import card.Vegetable;

public class VeggieScoreCalculator implements IScoreCalculator {
    /**
     * Calculates the total score for a player's hand based on various criteria.
     *
     * @param hand The player's hand containing cards.
     * @param thisPlayer The current player.
     * @param players The list of all players.
     * @return The total score calculated based on the criteria.
     */
    public static int calculateScore(ArrayList<Card> hand, Player thisPlayer, ArrayList<Player> players) {
        int totalScore = 0;

        for (Card criteriaCard : hand) {
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

    /**
     * Handles the criteria that involve the total number of vegetables.
     *
     * @param criteria The criteria string.
     * @param hand The player's hand.
     * @param thisPlayer The current player.
     * @param players The list of all players.
     * @return The score based on the total criteria.
     */
    private static int handleTotalCriteria(String criteria, ArrayList<Card> hand, Player thisPlayer, ArrayList<Player> players) {
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

    /**
     * Handles the criteria that involve specific types of vegetables.
     *
     * @param criteria The criteria string.
     * @param hand The player's hand.
     * @return The score based on the type criteria.
     */
    private static int handleTypeCriteria(String criteria, ArrayList<Card> hand) {
        String[] expr = criteria.split("/");
        int addScore = Integer.parseInt(expr[0].trim());
        if (expr[1].contains("MISSING")) {
            int missing = 0;
            for (Vegetable vegetable : Vegetable.values()) {
                if (CardUtils.countVegetables(hand, vegetable) == 0) {
                    missing++;
                }
            }
            return missing * addScore;
        } else {
            int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=") + 2).trim());
            int totalType = 0;
            for (Vegetable vegetable : Vegetable.values()) {
                if (CardUtils.countVegetables(hand, vegetable) >= atLeastPerVegType) {
                    totalType++;
                }
            }
            return totalType * addScore;
        }
    }

    /**
     * Handles the criteria that involve having a complete set of vegetables.
     *
     * @param hand The player's hand.
     * @return The score based on the set criteria.
     */
    private static int handleSetCriteria(ArrayList<Card> hand) {
        int addScore = 12;
        for (Vegetable vegetable : Vegetable.values()) {
            if (CardUtils.countVegetables(hand, vegetable) == 0) {
                addScore = 0;
                break;
            }
        }
        return addScore;
    }

    /**
     * Handles the criteria that involve even or odd counts of vegetables.
     *
     * @param criteria The criteria string.
     * @param hand The player's hand.
     * @return The score based on the even/odd criteria.
     */
    private static int handleEvenOddCriteria(String criteria, ArrayList<Card> hand) {
        String[] parts = criteria.split(":");
        String[] scores = parts[1].split(",");
        int evenScore = Integer.parseInt(scores[0].split("=")[1].trim());
        int oddScore = Integer.parseInt(scores[1].split("=")[1].trim());
        int count = CardUtils.countVegetables(hand, Vegetable.valueOf(parts[0].trim()));
        return (count % 2 == 0) ? evenScore : oddScore;
    }

    /**
     * Handles the criteria that involve addition of specific vegetables.
     *
     * @param criteria The criteria string.
     * @param hand The player's hand.
     * @return The score based on the addition criteria.
     */
    private static int handleAdditionCriteria(String criteria, ArrayList<Card> hand) {
        String[] parts = criteria.split("\\+");
        int scoreToAdd = Integer.parseInt(parts[1].split("=")[1].trim());
        String[] veggies = parts[0].split(" ");
        int minCount = Integer.MAX_VALUE;
        boolean sameVegetable = true;
        String firstVeg = veggies[0].trim();

        for (String veg : veggies) {
            int count = CardUtils.countVegetables(hand, Vegetable.valueOf(veg.trim()));
            if (count < minCount) {
                minCount = count;
            }
            if (!veg.trim().equals(firstVeg)) {
                sameVegetable = false;
            }
        }

        int totalScore = scoreToAdd * minCount;
        if (sameVegetable && veggies.length == 2) {
            totalScore /= 2;
        }

        return totalScore;
    }

    /**
     * Handles the criteria that involve division of specific vegetables.
     *
     * @param criteria The criteria string.
     * @param hand The player's hand.
     * @return The score based on the division criteria.
     */
    private static int handleDivisionCriteria(String criteria, ArrayList<Card> hand) {
        String[] parts = criteria.split("/");
        int scorePerUnit = Integer.parseInt(parts[0].trim());
        String vegType = parts[1].trim();
        int count = CardUtils.countVegetables(hand, Vegetable.valueOf(vegType));
        return scorePerUnit * count;
    }

    /**
     * Handles the criteria that involve subtraction of specific vegetables.
     *
     * @param criteria The criteria string.
     * @param hand The player's hand.
     * @return The score based on the subtraction criteria.
     */
    private static int handleSubtractionCriteria(String criteria, ArrayList<Card> hand) {
        String[] parts = criteria.split("-");
        int scorePerUnit = Integer.parseInt(parts[0].trim());
        String vegType = parts[1].trim();
        int count = CardUtils.countVegetables(hand, Vegetable.valueOf(vegType));
        return -scorePerUnit * count;
    }
}