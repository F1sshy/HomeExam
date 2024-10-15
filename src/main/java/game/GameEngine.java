package game;

import interfaces.IGameEngine;
import interfaces.IPlayer;
import player.Player;
import interfaces.IPile;
import java.util.ArrayList;
import card.card;
//import game.logic.scoreCalculator;
import card.pile;
import network.Server;

public abstract class GameEngine implements IGameEngine {
    private ArrayList<Player> players;
    private ArrayList<pile> piles;
    private boolean keepPlaying = true;


    public GameEngine(ArrayList<Player> players, ArrayList<pile> piles) {
        this.players = players;
        this.piles = piles;
    }

    @Override
    public void startGame(String[] args) {
        while (keepPlaying) {
            gameLoop();
        }
    }

    public void gameLoop() {
        Player thisPlayer = players.get(currentPlayer);
        boolean stillAvailableCards = false;
        for (IPile p : piles) {
            if (!p.isEmpty()) {
                stillAvailableCards = true;
                break;
            }
        }
        if (!stillAvailableCards) {
            keepPlaying = false;
            return;
        }
        if (!thisPlayer.isBot()) {
            handlePlayerTurn(thisPlayer);
        } else {
            handleBotTurn(thisPlayer);
        }
        currentPlayer = (currentPlayer == players.size() - 1) ? 0 : currentPlayer + 1;
    }

    private void handlePlayerTurn(Player thisPlayer) {
        thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        thisPlayer.sendMessage(displayHand(thisPlayer.getHand()));
        thisPlayer.sendMessage("\nThe piles are: ");
        thisPlayer.sendMessage(printMarket());
        boolean validChoice = false;
        while (!validChoice) {
            thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = thisPlayer.readMessage();
            validChoice = processPlayerChoice(thisPlayer, pileChoice);
        }
        checkAndHandleCriteriaCard(thisPlayer);
        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n" + displayHand(thisPlayer.getHand()) + "\n");
    }

    private boolean processPlayerChoice(Player thisPlayer, String pileChoice) {
        if (pileChoice.matches("\\d")) {
            int pileIndex = Integer.parseInt(pileChoice);
            if (piles.get(pileIndex).getPointCard() == null) {
                thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
                return false;
            } else {
                thisPlayer.getHand().add(piles.get(pileIndex).buyPointCard());
                thisPlayer.sendMessage("\nYou took a card from pile " + pileIndex + " and added it to your hand.\n");
                return true;
            }
        } else {
            return processVeggieChoice(thisPlayer, pileChoice);
        }
    }

    private boolean processVeggieChoice(Player thisPlayer, String pileChoice) {
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int pileIndex = choice / 3;
            int veggieIndex = choice % 3;
            if (piles.get(pileIndex).getVeggieCard(veggieIndex) == null) {
                thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
                return false;
            } else {
                if (takenVeggies == 2) {
                    return true;
                } else {
                    thisPlayer.getHand().add(piles.get(pileIndex).buyVeggieCard(veggieIndex));
                    takenVeggies++;
                }
            }
        }
        return true;
    }


    public void calculateAndAnnounceScores() {
        int maxScore = 0;
        int playerID = 0;
        for (IPlayer player : players) {
            //player.setScore(scoreCalculator.calculateScore(player.getHand(), Player, players));
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                playerID = player.getPlayerID();
            }
        }
        for (IPlayer player : players) {
            if (player.getPlayerID() == playerID) {
                player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
            } else {
                player.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
            }
        }
    }

    private void handleBotTurn(Player thisPlayer) {
        boolean emptyPiles = false;
        int choice = (int) (Math.random() * 2);
        if (choice == 0) {
            emptyPiles = !takeBestPointCard(thisPlayer);
        }
        if (choice == 1 || emptyPiles) {
            takeVegetableCards(thisPlayer);
        }
        Server.sendToAllPlayers("Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n" + cardUtils.displayHand(thisPlayer.getHand()) + "\n");
    }

    private boolean takeBestPointCard(player thisPlayer) {
        int highestPointCardIndex = 0;
        int highestPointCardScore = 0;
        for (int i = 0; i < piles.size(); i++) {
            if (piles.get(i).getPointCard() != null) {
                ArrayList<card> tempHand = new ArrayList<>();
                tempHand.addAll(thisPlayer.getHand());
                tempHand.add(piles.get(i).getPointCard());
                int score = cardUtils.calculateScore(tempHand, thisPlayer);
                if (score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }
        if (piles.get(highestPointCardIndex).getPointCard() != null) {
            thisPlayer.getHand().add(piles.get(highestPointCardIndex).buyPointCard());
            return true;
        }
        return false;
    }

    private void takeVegetableCards(player thisPlayer) {
        int cardsPicked = 0;
        for (pileSetup pile : piles) {
            if (pile.getVeggieCards()[0] != null && cardsPicked < 2) {
                thisPlayer.getHand().add(pile.buyVeggieCard(0));
                cardsPicked++;
            }
            if (pile.getVeggieCards()[1] != null && cardsPicked < 2) {
                thisPlayer.getHand().add(pile.buyVeggieCard(1));
                cardsPicked++;
            }
        }
        if (cardsPicked == 0) {
            takeBestPointCard(thisPlayer);
        }
    }
}

