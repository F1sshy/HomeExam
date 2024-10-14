package game.logic;

import main.PointSalad;

import java.util.ArrayList;

public class scoreCalculator {

    public int calculateScore(ArrayList<PointSalad.Card> hand, PointSalad.Player thisPlayer, ArrayList<PointSalad.Player> players) {
        int totalScore = 0;

        for (PointSalad.Card criteriaCard : hand) {
            if (criteriaCard.criteriaSideUp) {
                String criteria = criteriaCard.criteria;
                String[] parts = criteria.split(",");

                if (criteria.contains("TOTAL") || criteria.contains("TYPE") || criteria.contains("SET")) {
                    totalScore += calculateSpecialCriteriaScore(criteria, hand, thisPlayer, players);
                } else if (criteria.contains("MOST") || criteria.contains("FEWEST")) {
                    totalScore += calculateMostFewestScore(criteria, hand, thisPlayer, players);
                } else if (parts.length > 1 || criteria.contains("+") || parts[0].contains("/")) {
                    totalScore += calculateComplexCriteriaScore(criteria, hand);
                }
            }
        }

        return totalScore;
    }

    private int calculateSpecialCriteriaScore(String criteria, ArrayList<PointSalad.Card> hand, PointSalad.Player thisPlayer, ArrayList<PointSalad.Player> players) {
        int score = 0;
        // Implementation for special criteria score calculation
        return score;
    }

    private int calculateMostFewestScore(String criteria, ArrayList<PointSalad.Card> hand, PointSalad.Player thisPlayer, ArrayList<PointSalad.Player> players) {
        int score = 0;
        // Implementation for most/fewest score calculation
        return score;
    }

    private int calculateComplexCriteriaScore(String criteria, ArrayList<PointSalad.Card> hand) {
        int score = 0;
        // Implementation for complex criteria score calculation
        return score;
    }
}