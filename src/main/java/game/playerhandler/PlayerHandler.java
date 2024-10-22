package game.playerhandler;

import player.IPlayer;
import market.VeggieMarket;
import display.Display;
import card.ICard;


public class PlayerHandler {
    private VeggieMarket veggieMarket;
    private Display display;

    public PlayerHandler(VeggieMarket veggieMarket, Display display) {
        this.veggieMarket = veggieMarket;
        this.display = display;
    }

    public void handlePlayerTurn(IPlayer thisPlayer) {
        thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        thisPlayer.sendMessage(Display.displayHand(thisPlayer.getHand()));
        thisPlayer.sendMessage("\nThe piles are: ");
        thisPlayer.sendMessage(display.displayMarket(veggieMarket.getPiles()));
        boolean validChoice = false;
        while (!validChoice) {
            thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = thisPlayer.readMessage();
            validChoice = processPlayerChoice(thisPlayer, pileChoice);
        }
        checkAndHandleCriteriaCard(thisPlayer);
        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
    }

    private void checkAndHandleCriteriaCard(IPlayer thisPlayer) {
        boolean criteriaCardInHand = false;
        for (ICard card : thisPlayer.getHand()) {
            if (card.getCriteriaSideUp()) {
                criteriaCardInHand = true;
                break;
            }
        }
        if (criteriaCardInHand) {
            thisPlayer.sendMessage("\n" + Display.displayHand(thisPlayer.getHand()) + "\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
            String choice = thisPlayer.readMessage();
            if (choice.matches("\\d")) {
                int cardIndex = Integer.parseInt(choice);
                if (cardIndex >= 0 && cardIndex < thisPlayer.getHand().size()) {
                    ICard selectedCard = thisPlayer.getHand().get(cardIndex);
                    selectedCard.setCriteriaSideUp(false);
                } else {
                    thisPlayer.sendMessage("\nInvalid index. Please enter a valid card index.\n");
                    checkAndHandleCriteriaCard(thisPlayer); // Recursively prompt for valid input
                }
            }
        }
    }

    private boolean processPlayerChoice(IPlayer thisPlayer, String pileChoice) {
        if (pileChoice.matches("\\d")) {
            int pileIndex = Integer.parseInt(pileChoice);
            if (pileIndex < 0 || pileIndex >= veggieMarket.getPiles().size() || veggieMarket.getPiles().get(pileIndex).getPointCard() == null) {
                thisPlayer.sendMessage("\nThis pile is empty or invalid. Please choose another pile.\n");
                return false;
            } else {
                thisPlayer.getHand().add(veggieMarket.getPiles().get(pileIndex).buyPointCard());
                thisPlayer.sendMessage("\nYou took a card from pile " + pileIndex + " and added it to your hand.\n");
                return true;
            }
        } else if (pileChoice.matches("[A-Za-z]+") && pileChoice.length() <= 2) {
            return processVeggieChoice(thisPlayer, pileChoice);
        } else {
            thisPlayer.sendMessage("\nInvalid input. Please enter a valid pile number or vegetable card choice.\n");
            return false;
        }
    }

    private boolean processVeggieChoice(IPlayer thisPlayer, String pileChoice) {
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int pileIndex = choice / 2; // Assuming there are 2 veggie cards per pile
            int veggieIndex = choice % 2;
            if (pileIndex < 0 || pileIndex >= veggieMarket.getPiles().size() || veggieIndex < 0 || veggieIndex >= 2) {
                thisPlayer.sendMessage("\nInvalid vegetable card choice. Please choose another pile.\n");
                return false;
            }
            if (veggieMarket.getPiles().get(pileIndex).getVeggieCard(veggieIndex) == null) {
                thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
                return false;
            } else {
                if (takenVeggies == 2) {
                    return true;
                } else {
                    thisPlayer.getHand().add(veggieMarket.getPiles().get(pileIndex).buyVeggieCard(veggieIndex));
                    takenVeggies++;
                }
            }
        }
        return true;
    }
}