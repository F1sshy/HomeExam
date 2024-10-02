package game;

import main.PointSalad;

import java.util.ArrayList;
import java.util.Scanner;

public class engine {

    public ArrayList<PointSalad.Player> players = new ArrayList<>();

    public PointSalad(String[] args) {
        int numberPlayers = 0;
        int numberOfBots = 0;
        if(args.length == 0) {
            System.out.println("Please enter the number of players (1-6): ");
            Scanner in = new Scanner(System.in);
            numberPlayers = in.nextInt();
            System.out.println("Please enter the number of bots (0-5): ");
            numberOfBots = in.nextInt();
        }
        else {
            //check if args[0] is a String (ip address) or an integer (number of players)
            if(args[0].matches("\\d+")) {
                numberPlayers = Integer.parseInt(args[0]);
                numberOfBots = Integer.parseInt(args[1]);
            }
            else {
                try {
                    client(args[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        setPiles(numberPlayers+numberOfBots);

        try {
            server(numberPlayers, numberOfBots);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set random starting player.player
        int currentPlayer = (int) (Math.random() * (players.size()));
        boolean keepPlaying = true;

        while(keepPlaying) {
            PointSalad.Player thisPlayer = players.get(currentPlayer);
            boolean stillAvailableCards = false;
            for(PointSalad.Pile p: piles) {
                if(!p.isEmpty()) {
                    stillAvailableCards = true;
                    break;
                }
            }
            if(!stillAvailableCards) {
                keepPlaying = false;
                break;
            }
            if(!thisPlayer.isBot) {
                thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
                thisPlayer.sendMessage(displayHand(thisPlayer.hand));
                thisPlayer.sendMessage("\nThe piles are: ");

                thisPlayer.sendMessage(printMarket());
                boolean validChoice = false;
                while(!validChoice) {
                    thisPlayer.sendMessage("\n\nTake either one point card.card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
                    String pileChoice = thisPlayer.readMessage();
                    if(pileChoice.matches("\\d")) {
                        int pileIndex = Integer.parseInt(pileChoice);
                        if(piles.get(pileIndex).getPointCard() == null) {
                            thisPlayer.sendMessage("\nThis card.pile is empty. Please choose another card.pile.\n");
                            continue;
                        } else {
                            thisPlayer.hand.add(piles.get(pileIndex).buyPointCard());
                            thisPlayer.sendMessage("\nYou took a card.card from card.pile " + (pileIndex) + " and added it to your hand.\n");
                            validChoice = true;
                        }
                    } else {
                        int takenVeggies = 0;
                        for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
                            if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
                                thisPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
                                validChoice = false;
                                break;
                            }
                            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
                            int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
                            int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
                            if(piles.get(pileIndex).veggieCards[veggieIndex] == null) {
                                thisPlayer.sendMessage("\nThis veggie is empty. Please choose another card.pile.\n");
                                validChoice = false;
                                break;
                            } else {
                                if(takenVeggies == 2) {
                                    validChoice = true;
                                    break;
                                } else {
                                    thisPlayer.hand.add(piles.get(pileIndex).buyVeggieCard(veggieIndex));
                                    takenVeggies++;
                                    //thisPlayer.sendMessage("\nYou took a card.card from card.pile " + (pileIndex) + " and added it to your hand.\n");
                                    validChoice = true;
                                }
                            }
                        }

                    }
                }
                //Check if the player.player has any criteria cards in their hand
                boolean criteriaCardInHand = false;
                for(PointSalad.Card card : thisPlayer.hand) {
                    if(card.criteriaSideUp) {
                        criteriaCardInHand = true;
                        break;
                    }
                }
                if(criteriaCardInHand) {
                    //Give the player.player an option to turn a criteria card.card into a veggie card.card
                    thisPlayer.sendMessage("\n"+displayHand(thisPlayer.hand)+"\nWould you like to turn a criteria card.card into a veggie card.card? (Syntax example: n or 2)");
                    String choice = thisPlayer.readMessage();
                    if(choice.matches("\\d")) {
                        int cardIndex = Integer.parseInt(choice);
                        thisPlayer.hand.get(cardIndex).criteriaSideUp = false;
                    }
                }
                thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
                sendToAllPlayers("Player " + thisPlayer.playerID + "'s hand is now: \n"+displayHand(thisPlayer.hand)+"\n");

            } else {
                // Bot logic
                // The Bot will randomly decide to take either one point card.card or two veggie cards
                // For point card.card the Bot will always take the point card.card with the highest score
                // If there are two point cards with the same score, the bot will take the first one
                // For Veggie cards the Bot will pick the first one or two available veggies
                boolean emptyPiles = false;
                // Random choice:
                int choice = (int) (Math.random() * 2);
                if(choice == 0) {
                    // Take a point card.card
                    int highestPointCardIndex = 0;
                    int highestPointCardScore = 0;
                    for(int i = 0; i < piles.size(); i++) {
                        if(piles.get(i).getPointCard() != null) {
                            ArrayList<PointSalad.Card> tempHand = new ArrayList<PointSalad.Card>();
                            for(PointSalad.Card handCard : thisPlayer.hand) {
                                tempHand.add(handCard);
                            }
                            tempHand.add(piles.get(i).getPointCard());
                            int score = calculateScore(tempHand, thisPlayer);
                            if(score > highestPointCardScore) {
                                highestPointCardScore = score;
                                highestPointCardIndex = i;
                            }
                        }
                    }
                    if(piles.get(highestPointCardIndex).getPointCard() != null) {
                        thisPlayer.hand.add(piles.get(highestPointCardIndex).buyPointCard());
                    } else {
                        choice = 1; //buy veggies instead
                        emptyPiles = true;
                    }
                } else if (choice == 1) {
                    // TODO: Check what Veggies are available and run calculateScore to see which veggies are best to pick
                    int cardsPicked = 0;
                    for(PointSalad.Pile pile : piles) {
                        if(pile.veggieCards[0] != null && cardsPicked < 2) {
                            thisPlayer.hand.add(pile.buyVeggieCard(0));
                            cardsPicked++;
                        }
                        if(pile.veggieCards[1] != null && cardsPicked < 2) {
                            thisPlayer.hand.add(pile.buyVeggieCard(1));
                            cardsPicked++;
                        }
                    }
                    if(cardsPicked == 0 && !emptyPiles) {
                        // Take a point card.card instead of veggies if there are no veggies left
                        int highestPointCardIndex = 0;
                        int highestPointCardScore = 0;
                        for(int i = 0; i < piles.size(); i++) {
                            if(piles.get(i).getPointCard() != null && piles.get(i).getPointCard().criteriaSideUp) {
                                ArrayList<PointSalad.Card> tempHand = new ArrayList<PointSalad.Card>();
                                for(PointSalad.Card handCard : thisPlayer.hand) {
                                    tempHand.add(handCard);
                                }
                                tempHand.add(piles.get(i).getPointCard());
                                int score = calculateScore(tempHand, thisPlayer);
                                if(score > highestPointCardScore) {
                                    highestPointCardScore = score;
                                    highestPointCardIndex = i;
                                }
                            }
                        }
                        if(piles.get(highestPointCardIndex).getPointCard() != null) {
                            thisPlayer.hand.add(piles.get(highestPointCardIndex).buyPointCard());
                        }
                    }
                }
                sendToAllPlayers("Bot " + thisPlayer.playerID + "'s hand is now: \n"+displayHand(thisPlayer.hand)+"\n");
            }

            if(currentPlayer == players.size()-1) {
                currentPlayer = 0;
            } else {
                currentPlayer++;
            }
        }
        sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"));
        for(PointSalad.Player player : players) {
            sendToAllPlayers("Player " + player.playerID + "'s hand is: \n"+displayHand(player.hand));
            player.score = calculateScore(player.hand, player);
            sendToAllPlayers("\nPlayer " + player.playerID + "'s score is: " + player.score);
        }

        int maxScore = 0;
        int playerID = 0;
        for(PointSalad.Player player : players) {
            if(player.score > maxScore) {
                maxScore = player.score;
                playerID = player.playerID;
            }
        }
        for(PointSalad.Player player : players) {
            if(player.playerID == playerID) {
                player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
            } else {
                player.sendMessage("\nThe winner is player.player " + playerID + " with a score of " + maxScore);
            }
        }
    }



}
