package game;

import player.player;
import network.client;
import network.server;
import game.logic.pileSetup;
import game.logic.cardUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class engine {
    private ArrayList<player> players = new ArrayList<>();
    private ArrayList<pileSetup> piles = new ArrayList<>();

    public void startGame(String[] args) {
        int numberPlayers = 0;
        int numberOfBots = 0;

        if (args.length == 0) {
            System.out.println("Please enter the number of players (1-6): ");
            Scanner in = new Scanner(System.in);
            numberPlayers = in.nextInt();
            System.out.println("Please enter the number of bots (0-5): ");
            numberOfBots = in.nextInt();
        } else {
            if (args[0].matches("\\d+")) {
                numberPlayers = Integer.parseInt(args[0]);
                numberOfBots = Integer.parseInt(args[1]);
            } else {
                try {
                    new client(args[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        pileSetup pileSetupInstance = new pileSetup();
        pileSetupInstance.setPiles(numberPlayers + numberOfBots);

        try {
            new server().server(numberPlayers, numberOfBots);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int currentPlayer = (int) (Math.random() * (players.size()));
        boolean keepPlaying = true;

        while (keepPlaying) {
            player thisPlayer = players.get(currentPlayer);
            keepPlaying = playerTurn(thisPlayer);
            currentPlayer = (currentPlayer == players.size() - 1) ? 0 : currentPlayer + 1;
        }

        calculateAndAnnounceScores();
    }

    private boolean playerTurn(player thisplayer) {
        if (!areCardsAvailable()) {
            return false;
        }

        if (!thisplayer.isBot()) {
            handleHumanPlayerTurn(thisplayer);
        } else {
            handleBotPlayerTurn(thisplayer);
        }

        return true;
    }

    private boolean areCardsAvailable() {
        for (pileSetup p : piles) {
            if (!p.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void handleHumanPlayerTurn(player thisPlayer) {
        thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        thisPlayer.sendMessage(cardUtils.displayHand(thisPlayer.getHand()));
        thisPlayer.sendMessage("\nThe piles are: ");
        thisPlayer.sendMessage(printMarket());

        boolean validChoice = false;
        while (!validChoice) {
            thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = thisPlayer.readMessage();
            validChoice = processHumanPlayerChoice(thisPlayer, pileChoice);
        }

        if (hasCriteriaCardInHand(thisPlayer)) {
            thisPlayer.sendMessage("\n" + cardUtils.displayHand(thisPlayer.getHand()) + "\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
            String choice = thisPlayer.readMessage();
            if (choice.matches("\\d")) {
                int cardIndex = Integer.parseInt(choice);
                thisPlayer.getHand().get(cardIndex).setCriteriaSideUp(false);
            }
        }

        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n" + cardUtils.displayHand(thisPlayer.getHand()) + "\n");
    }

    private boolean processHumanPlayerChoice(player thisPlayer, String pileChoice) {
        if (pileChoice.matches("\\d")) {
            int pileIndex = Integer.parseInt(pileChoice);
            if (piles.get(pileIndex).getPointCard() == null) {
                thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
                return false;
            } else {
                thisPlayer.getHand().add(piles.get(pileIndex).buyPointCard());
                thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
                return true;
            }
        } else {
            return processVegetableChoice(thisPlayer, pileChoice);
        }
    }

    private boolean processVegetableChoice(player thisPlayer, String pileChoice) {
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            if (Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
                thisPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
                return false;
            }
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2 : -1;
            int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
            if (piles.get(pileIndex).getVeggieCards()[veggieIndex] == null) {
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

    private boolean hasCriteriaCardInHand(player thisPlayer) {
        for (card card : thisPlayer.getHand()) {
            if (card.isCriteriaSideUp()) {
                return true;
            }
        }
        return false;
    }

    private void handleBotPlayerTurn(player thisPlayer) {
        boolean emptyPiles = false;
        int choice = (int) (Math.random() * 2);
        if (choice == 0) {
            emptyPiles = !takeBestPointCard(thisPlayer);
        }
        if (choice == 1 || emptyPiles) {
            takeVegetableCards(thisPlayer);
        }
        sendToAllPlayers("Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n" + cardUtils.displayHand(thisPlayer.getHand()) + "\n");
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

    private void calculateAndAnnounceScores() {
        sendToAllPlayers("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n");
        for (player player : players) {
            sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n" + cardUtils.displayHand(player.getHand()));
            player.setScore(cardUtils.calculateScore(player.getHand(), player));
            sendToAllPlayers("\nPlayer " + player.getPlayerID() + "'s score is: " + player.getScore());
        }

        int maxScore = 0;
        int playerID = 0;
        for (player player : players) {
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                playerID = player.getPlayerID();
            }
        }
        for (player player : players) {
            if (player.getPlayerID() == playerID) {
                player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
            } else {
                player.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
            }
        }
    }

    private void sendToAllPlayers(String message) {
        for (player player : players) {
            player.sendMessage(message);
        }
    }

    private String printMarket() {
        StringBuilder market = new StringBuilder();
        for (int i = 0; i < piles.size(); i++) {
            market.append("Pile ").append(i).append(": ").append(piles.get(i).toString()).append("\n");
        }
        return market.toString();
    }
}