package game.logic;

import card.ICard;
import card.Vegetable;
import player.IPlayer;

import java.util.ArrayList;

public class VeggieScoreCalculator implements IScoreCalculator {

    public static int calculateScore(ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        int totalScore = 0;

        for (ICard criteriaVeggieCard : hand) {
            if (criteriaVeggieCard.getCriteriaSideUp()) {
                String criteria = criteriaVeggieCard.getCriteria();
                System.out.println("Evaluating criteria: " + criteria);

                if (criteria.contains("TOTAL")) {
                    totalScore += handleTotalCriteria(criteria, hand, thisPlayer, players);
                } else if (criteria.contains("TYPE")) {
                    totalScore += handleTypeCriteria(criteria, hand);
                } else if (criteria.contains("SET")) {
                    totalScore += handleSetCriteria(hand);
                } else if (criteria.contains("EVEN") || criteria.contains("ODD")) {
                    totalScore += handleEvenOddCriteria(criteria, hand);
                }else if (criteria.contains(",")) {
                    totalScore += handleComplexCriteria(criteria, hand);
                }else if (criteria.contains("+")) {
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

    private static int handleTotalCriteria(String criteria, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        int countVeg = CardUtils.countTotalVegetables(hand);
        int thisHandCount = countVeg;
        for (IPlayer p : players) {
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
    private static int handleTypeCriteria(String criteria, ArrayList<ICard> hand) {
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

    protected static int handleSetCriteria(ArrayList<ICard> hand) {
        int addScore = 12;
        for (Vegetable vegetable : Vegetable.values()) {
            if (CardUtils.countVegetables(hand, vegetable) == 0) {
                addScore = 0;
                break;
            }
        }
        return addScore;
    }

    protected static int handleEvenOddCriteria(String criteria, ArrayList<ICard> hand) {
        String[] parts = criteria.split(":");
        String[] scores = parts[1].split(",");
        int evenScore = Integer.parseInt(scores[0].split("=")[1].trim());
        int oddScore = Integer.parseInt(scores[1].split("=")[1].trim());
        int count = CardUtils.countVegetables(hand, Vegetable.valueOf(parts[0].trim()));
        return (count % 2 == 0) ? evenScore : oddScore;
    }

    protected static int handleAdditionCriteria(String criteria, ArrayList<ICard> hand) {
        String[] parts = criteria.split("=");
        if (parts.length < 2) {
            System.err.println("Invalid criteria format: " + criteria);
            return 0;
        }

        int scoreToAdd;
        try {
            scoreToAdd = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing score to add: " + parts[1]);
            return 0;
        }

        String[] veggies = parts[0].split("\\+");
        if (veggies.length == 0) {
            System.err.println("No vegetables specified in criteria: " + parts[0]);
            return 0;
        }

        int minCount = Integer.MAX_VALUE;
        for (String veg : veggies) {
            try {
                int count = CardUtils.countVegetables(hand, Vegetable.valueOf(veg.trim()));
                if (count < minCount) {
                    minCount = count;
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid vegetable type: " + veg.trim());
                return 0;
            }
        }

        return scoreToAdd * minCount;
    }

    protected static int handleDivisionCriteria(String criteria, ArrayList<ICard> hand) {
        String[] parts = criteria.split("/");
        int scorePerUnit = Integer.parseInt(parts[0].trim());
        String vegType = parts[1].trim();
        int count = CardUtils.countVegetables(hand, Vegetable.valueOf(vegType));
        return scorePerUnit * count;
    }

    protected static int handleSubtractionCriteria(String criteria, ArrayList<ICard> hand) {
        String[] parts = criteria.split(",");
        int totalScore = 0;

        for (String part : parts) {
            String[] subParts = part.split("/");
            if (subParts.length < 2) {
                System.err.println("Invalid criteria format: " + part);
                continue;
            }
            try {
                int scorePerUnit = Integer.parseInt(subParts[0].trim());
                String vegType = subParts[1].trim();
                int count = CardUtils.countVegetables(hand, Vegetable.valueOf(vegType));
                totalScore += scorePerUnit * count;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in criteria: " + part);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid vegetable type: " + subParts[1].trim());
            }
        }

        return totalScore;
    }

    protected static int handleComplexCriteria(String criteria, ArrayList<ICard> hand) {
        int score = 0;
        String[] criteriaParts = criteria.split(",");
        for (String part : criteriaParts) {
            part = part.trim();
            if (part.contains("/")) {
                score += handleDivisionCriteria(part, hand);
            } else if (part.contains("-")) {
                score += handleSubtractionCriteria(part, hand);
            } else if (part.contains("+")) {
                score += handleAdditionCriteria(part, hand);
            } else if (part.contains(":")) {
                score += handleEvenOddCriteria(part, hand);
            }
        }
        return score;
    }
}