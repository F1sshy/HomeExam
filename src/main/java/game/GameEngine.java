package game;

import network.Server;
import pile.pile;
import player.IPlayer;
import player.Player;
import pile.IPile;
import java.util.ArrayList;
import game.logic.scoreCalculator;
import game.logic.pileSetup;
import card.*;
import display.Display;
import market.Market;

public class GameEngine implements IGameEngine {
    private ArrayList<Player> players;
    private Market market;
    private Server server;
    private Display display;

    public GameEngine(ArrayList<Player> players) {
        this.players = players;
        this.server = server;
        this.display = new Display();
    }

    @Override
    public void startGame(String[] args) {
        pileSetup pileSetup = new pileSetup();
        pileSetup.setPiles(players.size());
        ArrayList<pile> piles = pileSetup.getPiles();
        this.market = new Market(piles);
        gameLoop();
    }

    public void gameLoop() {
        int currentPlayer = (int) (Math.random() * (players.size()));
        boolean keepPlaying = true;
        Player thisPlayer;
        while (keepPlaying) {
            thisPlayer = players.get(currentPlayer);
            boolean stillAvailableCards = false;
            for (IPile p : market.getPiles()) {
                if (!p.isEmpty()) {
                    stillAvailableCards = true;
                    break;
                }
            }
            if (!stillAvailableCards) {
                keepPlaying = false;
                return;
            }
            market.replaceMarket(); // Call to reshuffle the piles before player turn
            if (!thisPlayer.isBot()) {
                market.replaceMarket();
                handlePlayerTurn(thisPlayer);
            } else {
                market.replaceMarket();
                handleBotTurn(thisPlayer);
            }
            market.replaceMarket();
            currentPlayer = (currentPlayer == players.size() - 1) ? 0 : currentPlayer + 1;
        }
    }

    private void handlePlayerTurn(Player thisPlayer) {
        thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        thisPlayer.sendMessage(Display.displayHand(thisPlayer.getHand()));
        thisPlayer.sendMessage("\nThe piles are: ");
        thisPlayer.sendMessage(display.displayMarket(market.getPiles()));
        boolean validChoice = false;
        while (!validChoice) {
            thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = thisPlayer.readMessage();
            validChoice = processPlayerChoice(thisPlayer, pileChoice);
        }
        checkAndHandleCriteriaCard(thisPlayer);
        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n" + Display.displayHand(thisPlayer.getHand()) + "\n");
    }

    private void checkAndHandleCriteriaCard(Player thisPlayer) {
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
                ICard selectedCard = thisPlayer.getHand().get(cardIndex);
                selectedCard.setCriteriaSideUp(false);
            }
        }
        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n" + Display.displayHand(thisPlayer.getHand()) + "\n");
    }

    private boolean processPlayerChoice(Player thisPlayer, String pileChoice) {
        if (pileChoice.matches("\\d")) {
            int pileIndex = Integer.parseInt(pileChoice);
            if (pileIndex < 0 || pileIndex >= market.getPiles().size() || market.getPiles().get(pileIndex).getPointCard() == null) {
                thisPlayer.sendMessage("\nThis pile is empty or invalid. Please choose another pile.\n");
                return false;
            } else {
                thisPlayer.getHand().add(market.getPiles().get(pileIndex).buyPointCard());
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

    private boolean processVeggieChoice(Player thisPlayer, String pileChoice) {
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int pileIndex = choice / 3;
            int veggieIndex = choice % 3;
            if (pileIndex < 0 || pileIndex >= market.getPiles().size() || veggieIndex < 0 || veggieIndex >= 2) {
                thisPlayer.sendMessage("\nInvalid vegetable card choice. Please choose another pile.\n");
                return false;
            }
            if (market.getPiles().get(pileIndex).getVeggieCard(veggieIndex) == null) {
                thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
                return false;
            } else {
                if (takenVeggies == 2) {
                    return true;
                } else {
                    thisPlayer.getHand().add(market.getPiles().get(pileIndex).buyVeggieCard(veggieIndex));
                    takenVeggies++;
                }
            }
        }
        return true;
    }

    public void calculateAndAnnounceScores() {
        int maxScore = 0;
        int playerID = 0;
        for (Player player : players) {
            player.setScore(scoreCalculator.calculateScore(player.getHand(), player, players));
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
        sendToAllPlayers("Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n" + Display.displayHand(thisPlayer.getHand()) + "\n");
    }

    private boolean takeBestPointCard(Player thisPlayer) {
        int highestPointCardIndex = 0;
        int highestPointCardScore = 0;
        for (int i = 0; i < market.getPiles().size(); i++) {
            if (market.getPiles().get(i).getPointCard() != null) {
                ArrayList<card> tempHand = new ArrayList<>();
                tempHand.addAll(thisPlayer.getHand());
                tempHand.add(market.getPiles().get(i).getPointCard());
                int score = scoreCalculator.calculateScore(tempHand, thisPlayer, players);
                if (score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }
        if (market.getPiles().get(highestPointCardIndex).getPointCard() != null) {
            thisPlayer.getHand().add(market.getPiles().get(highestPointCardIndex).buyPointCard());
            return true;
        }
        return false;
    }

    private void takeVegetableCards(Player thisPlayer) {
        int cardsPicked = 0;
        for (IPile pile : market.getPiles()) {
            if (pile.getVeggieCard(0) != null && cardsPicked < 2) {
                thisPlayer.getHand().add(pile.buyVeggieCard(0));
                cardsPicked++;
            }
            if (pile.getVeggieCard(1) != null && cardsPicked < 2) {
                thisPlayer.getHand().add(pile.buyVeggieCard(1));
                cardsPicked++;
            }
        }
        if (cardsPicked == 0) {
            takeBestPointCard(thisPlayer);
        }
    }

    public void sendToAllPlayers(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }
}