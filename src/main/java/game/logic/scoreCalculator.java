package game.logic;


import card.card;
import player.Player;



import java.util.ArrayList;
import game.logic.CardUtils;

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
                        int countVeg = CardUtils.countTotalVegetables(hand);
                        int thisHandCount = countVeg;
                        for (Player p : players) {
                            if (p.getPlayerID() != thisPlayer.getPlayerID()) {
                                int playerVeg = CardUtils.countTotalVegetables(p.getHand());
                                if ((criteria.indexOf("MOST") >= 0) && (playerVeg > countVeg)) {
                                    countVeg = CardUtils.countVegetables(p.getHand(), criteriaCard.vegetable);
                                }
                                if ((criteria.indexOf("FEWEST") >= 0) && (playerVeg < countVeg)) {
                                    countVeg = CardUtils.countVegetables(p.getHand(), criteriaCard.vegetable);
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
                                if (CardUtils.countTotalVegetables() == 0) {
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
                                int countVeg = CardUtils.countTotalVegetables(hand);
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
                            int countVeg = CardUtils.countTotalVegetables(hand);
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
