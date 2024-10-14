package game;

import main.PointSalad;
import network.client;
import network.server;
import game.logic.logic;
import game.logic.pileSetup;

import java.util.ArrayList;
import java.util.Scanner;

public class engine {

    public void startGame(String[] args) {
        int numberPlayers = 0;
        int numberOfBots = 0;
        //int nrPlayers = 0;
        if(args.length == 0) {
            System.out.println("Please enter the number of players (1-6): ");
            Scanner in = new Scanner(System.in);
            numberPlayers = in.nextInt();
            System.out.println("Please enter the number of bots (0-5): ");
            numberOfBots = in.nextInt();
            //nrPlayers = numberPlayers + numberOfBots;
        } else {
            if(args[0].matches("\\d+")) {
                numberPlayers = Integer.parseInt(args[0]);
                numberOfBots = Integer.parseInt(args[1]);
            } else {
                try {
                    client(args[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //TODO: Fix
        pileSetup.setPiles(numberPlayers + numberOfBots);

        try {
            new server(numberPlayers, numberOfBots);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int currentPlayer = (int) (Math.random() * (players.size()));
        boolean keepPlaying = true;

        while(keepPlaying) {
            PointSalad.Player thisPlayer = players.get(currentPlayer);
            keepPlaying = playerTurn(thisPlayer);
            currentPlayer = (currentPlayer == players.size() - 1) ? 0 : currentPlayer + 1;
        }

        calculateAndAnnounceScores();
    }

    private boolean playerTurn(PointSalad.Player thisPlayer) {
        if (!areCardsAvailable()) {
            return false;
        }

        if (!thisPlayer.isBot) {
            handleHumanPlayerTurn(thisPlayer);
        } else {
            handleBotPlayerTurn(thisPlayer);
        }

        return true;
    }

    private boolean areCardsAvailable() {
        for (PointSalad.Pile p : piles) {
            if (!p.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void handleHumanPlayerTurn(player.player thisPlayer) {
        thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        thisPlayer.sendMessage(displayHand(thisPlayer.hand));
        thisPlayer.sendMessage("\nThe piles are: ");
        thisPlayer.sendMessage(printMarket());

        boolean validChoice = false;
        while (!validChoice) {
            thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = thisPlayer.readMessage();
            validChoice = processHumanPlayerChoice(thisPlayer, pileChoice);
        }

        if (hasCriteriaCardInHand(thisPlayer)) {
            thisPlayer.sendMessage("\n" + displayHand(thisPlayer.hand) + "\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
            String choice = thisPlayer.readMessage();
            if (choice.matches("\\d")) {
                int cardIndex = Integer.parseInt(choice);
                thisPlayer.hand.get(cardIndex).criteriaSideUp = false;
            }
        }

        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        sendToAllPlayers("Player " + thisPlayer.playerID + "'s hand is now: \n" + displayHand(thisPlayer.hand) + "\n");
    }

    private boolean processHumanPlayerChoice(PointSalad.Player thisPlayer, String pileChoice) {
        if (pileChoice.matches("\\d")) {
            int pileIndex = Integer.parseInt(pileChoice);
            if (piles.get(pileIndex).getPointCard() == null) {
                thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
                return false;
            } else {
                thisPlayer.hand.add(piles.get(pileIndex).buyPointCard());
                thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
                return true;
            }
        } else {
            return processVegetableChoice(thisPlayer, pileChoice);
        }
    }

    private boolean processVegetableChoice(PointSalad.Player thisPlayer, String pileChoice) {
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            if (Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
                thisPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
                return false;
            }
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2 : -1;
            int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
            if (piles.get(pileIndex).veggieCards[veggieIndex] == null) {
                thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
                return false;
            } else {
                if (takenVeggies == 2) {
                    return true;
                } else {
                    thisPlayer.hand.add(piles.get(pileIndex).buyVeggieCard(veggieIndex));
                    takenVeggies++;
                }
            }
        }
        return true;
    }

    private boolean hasCriteriaCardInHand(PointSalad.Player thisPlayer) {
        for (PointSalad.Card card : thisPlayer.hand) {
            if (card.criteriaSideUp) {
                return true;
            }
        }
        return false;
    }

    private void handleBotPlayerTurn(PointSalad.Player thisPlayer) {
        boolean emptyPiles = false;
        int choice = (int) (Math.random() * 2);
        if (choice == 0) {
            emptyPiles = !takeBestPointCard(thisPlayer);
        }
        if (choice == 1 || emptyPiles) {
            takeVegetableCards(thisPlayer);
        }
        sendToAllPlayers("Bot " + thisPlayer.playerID + "'s hand is now: \n" + displayHand(thisPlayer.hand) + "\n");
    }

    private boolean takeBestPointCard(PointSalad.Player thisPlayer) {
        int highestPointCardIndex = 0;
        int highestPointCardScore = 0;
        for (int i = 0; i < piles.size(); i++) {
            if (piles.get(i).getPointCard() != null) {
                ArrayList<PointSalad.Card> tempHand = new ArrayList<>();
                tempHand.addAll(thisPlayer.hand);
                tempHand.add(piles.get(i).getPointCard());
                int score = calculateScore(tempHand, thisPlayer);
                if (score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }
        if (piles.get(highestPointCardIndex).getPointCard() != null) {
            thisPlayer.hand.add(piles.get(highestPointCardIndex).buyPointCard());
            return true;
        }
        return false;
    }

    private void takeVegetableCards(PointSalad.Player thisPlayer) {
        int cardsPicked = 0;
        for (PointSalad.Pile pile : piles) {
            if (pile.veggieCards[0] != null && cardsPicked < 2) {
                thisPlayer.hand.add(pile.buyVeggieCard(0));
                cardsPicked++;
            }
            if (pile.veggieCards[1] != null && cardsPicked < 2) {
                thisPlayer.hand.add(pile.buyVeggieCard(1));
                cardsPicked++;
            }
        }
        if (cardsPicked == 0) {
            takeBestPointCard(thisPlayer);
        }
    }

    private void calculateAndAnnounceScores() {
        sendToAllPlayers("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n");
        for (PointSalad.Player player : players) {
            sendToAllPlayers("Player " + player.playerID + "'s hand is: \n" + displayHand(player.hand));
            player.score = calculateScore(player.hand, player);
            sendToAllPlayers("\nPlayer " + player.playerID + "'s score is: " + player.score);
        }

        int maxScore = 0;
        int playerID = 0;
        for (PointSalad.Player player : players) {
            if (player.score > maxScore) {
                maxScore = player.score;
                playerID = player.playerID;
            }
        }
        for (PointSalad.Player player : players) {
            if (player.playerID == playerID) {
                player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
            } else {
                player.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
            }
        }
    }
}
