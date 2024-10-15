package game.logic;

/*
import card.card;
import player.Player;



import java.util.ArrayList;

import static game.logic.cardUtils.countVegetables;
import static game.logic.cardUtils.countTotalVegetables;

public class scoreCalculator {

    public static int calculateScore(ArrayList<card> hand, Player thisPlayer, ArrayList<Player> players) {
        //System.out.println("DEBUG: \n" + displayHand(hand));
        int totalScore = 0;

        for (card criteriaCard : hand) {
            if (criteriaCard.criteriaSideUp) {
                String criteria = criteriaCard.criteria;
                String[] parts = criteria.split(",");

                //ID 18
                if (criteria.indexOf("TOTAL") >= 0 || criteria.indexOf("TYPE") >= 0 || criteria.indexOf("SET") >= 0) {
                    if (criteria.indexOf("TOTAL") >= 0) {
                        int countVeg = countTotalVegetables(hand);
                        int thisHandCount = countVeg;
                        for (player p : players) {
                            if (p.playerID != thisPlayer.playerID) {
                                int playerVeg = countTotalVegetables(p.hand);
                                if ((criteria.indexOf("MOST") >= 0) && (playerVeg > countVeg)) {
                                    countVeg = countTotalVegetables(p.hand);
                                }
                                if ((criteria.indexOf("FEWEST") >= 0) && (playerVeg < countVeg)) {
                                    countVeg = countTotalVegetables(p.hand);
                                }
                            }
                        }
                        if (countVeg == thisHandCount) {
                            //int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
                            //System.out.print("ID18 MOST/FEWEST: "+aScore + " " );
                            totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim());
                        }
                    }
                    if (criteria.indexOf("TYPE") >= 0) {
                        String[] expr = criteria.split("/");
                        int addScore = Integer.parseInt(expr[0].trim());
                        if (expr[1].indexOf("MISSING") >= 0) {
                            int missing = 0;
                            for (card.Vegetable vegetable : card.Vegetable.values()) {
                                if (countVegetables(hand, vegetable) == 0) {
                                    missing++;
                                }
                            }
                            //int aScore = missing * addScore;
                            //System.out.print("ID18 TYPE MISSING: "+aScore + " ");
                            totalScore += missing * addScore;
                        } else {
                            int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=") + 2).trim());
                            int totalType = 0;
                            for (card.Vegetable vegetable : card.Vegetable.values()) {
                                int countVeg = countVegetables(hand, vegetable);
                                if (countVeg >= atLeastPerVegType) {
                                    totalType++;
                                }
                            }
                            //int aScore = totalType * addScore;
                            //System.out.print("ID18 TYPE >=: "+aScore + " ");
                            totalScore += totalType * addScore;
                        }
                    }
                    if (criteria.indexOf("SET") >= 0) {
                        int addScore = 12;
                        for (card.Vegetable vegetable : card.Vegetable.values()) {
                            int countVeg = countVegetables(hand, vegetable);
                            if (countVeg == 0) {
                                addScore = 0;
                                break;
                            }
                        }
                        //System.out.print("ID18 SET: "+addScore + " ");
                        totalScore += addScore;
                    }
                }
            }
        }
        return totalScore;
    }


}

 */