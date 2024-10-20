package game;

import game.logic.VeggieScoreCalculator;
import pile.Pile;
import player.IPlayer;
import pile.IPile;
import java.util.ArrayList;
import game.logic.VeggiePileSetup;
import card.*;
import display.Display;
import market.Market;

/**
 * The GameEngine class implements the IGameEngine interface and manages the main game logic.
 */
public class GameEngine implements IGameEngine {
    private ArrayList<IPlayer> players;
    private Market market;
    private Display display;
    private static GameEngine instance;

    /**
     * Private constructor to prevent instantiation.
     *
     * @param players the list of players participating in the game
     */
    public GameEngine(ArrayList<IPlayer> players) {
        this.players = players;
        this.display = new Display();
    }

    /**
     * Provides the global point of access to the GameEngine instance.
     *
     * @param players the list of players participating in the game
     * @return the single instance of GameEngine
     */
    public static GameEngine getInstance(ArrayList<IPlayer> players) {
        if (instance == null) {
            instance = new GameEngine(players);
        }
        return instance;
    }

    /**
     * Starts the game with the specified arguments.
     *
     * @param args the command line arguments
     */
    @Override
    public void startGame(String[] args) {
        VeggiePileSetup veggiePileSetup = new VeggiePileSetup();
        veggiePileSetup.setPiles(players.size());
        ArrayList<Pile> piles = veggiePileSetup.getPiles();
        this.market = Market.getInstance(piles);
        gameLoop();
        calculateAndAnnounceScores();
    }

    /**
     * The main game loop that handles player turns and game progression.
     */
    public void gameLoop() {
        int currentPlayer = (int) (Math.random() * (players.size()));
        boolean keepPlaying = true;
        IPlayer thisPlayer;
        while (keepPlaying) {
            thisPlayer = players.get(currentPlayer);
            boolean stillAvailableCards = false;
            for (IPile p : market.getPiles()) {
                if (!p.isEmpty() || p.getVeggieCard(0) != null || p.getVeggieCard(1) != null) {
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
                handlePlayerTurn(thisPlayer);
            } else {
                handleBotTurn(thisPlayer);
            }
            currentPlayer = (currentPlayer == players.size() - 1) ? 0 : currentPlayer + 1;
        }
        calculateAndAnnounceScores();
    }

    /**
     * Handles the turn for a human player.
     *
     * @param thisPlayer the player whose turn it is
     */
    private void handlePlayerTurn(IPlayer thisPlayer) {
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

    /**
     * Checks and handles the criteria card for the player.
     *
     * @param thisPlayer the player whose criteria card is being checked
     */
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
        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n" + Display.displayHand(thisPlayer.getHand()) + "\n");
    }

    /**
     * Processes the player's choice of pile.
     *
     * @param thisPlayer the player making the choice
     * @param pileChoice the choice made by the player
     * @return true if the choice is valid, false otherwise
     */
    private boolean processPlayerChoice(IPlayer thisPlayer, String pileChoice) {
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

    /**
     * Processes the player's choice of vegetable cards.
     *
     * @param thisPlayer the player making the choice
     * @param pileChoice the choice made by the player
     * @return true if the choice is valid, false otherwise
     */
    private boolean processVeggieChoice(IPlayer thisPlayer, String pileChoice) {
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int pileIndex = choice / 2; // Assuming there are 2 veggie cards per pile
            int veggieIndex = choice % 2;
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

    /**
     * Calculates and announces the scores of all players.
     */
    public void calculateAndAnnounceScores() {
        int maxScore = 0;
        int playerID = 0;
        for (IPlayer player : players) {
            player.setScore(VeggieScoreCalculator.calculateScore(player.getHand(), player, players));
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

    /**
     * Handles the turn for a bot player.
     *
     * @param thisPlayer the bot player whose turn it is
     */
    private void handleBotTurn(IPlayer thisPlayer) {
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

    /**
     * Bot player takes the best available point card.
     *
     * @param thisPlayer the bot player
     * @return true if a point card was taken, false otherwise
     */
    private boolean takeBestPointCard(IPlayer thisPlayer) {
        int highestPointCardIndex = 0;
        int highestPointCardScore = 0;
        for (int i = 0; i < market.getPiles().size(); i++) {
            if (market.getPiles().get(i).getPointCard() != null) {
                ArrayList<VeggieCard> tempHand = new ArrayList<>();
                tempHand.addAll(thisPlayer.getHand());
                tempHand.add(market.getPiles().get(i).getPointCard());
                int score = VeggieScoreCalculator.calculateScore(tempHand, thisPlayer, players);
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

    /**
     * Bot player takes available vegetable cards.
     *
     * @param thisPlayer the bot player
     */
    private void takeVegetableCards(IPlayer thisPlayer) {
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

    /**
     * Sends a message to all players.
     *
     * @param message the message to be sent
     */
    public void sendToAllPlayers(String message) {
        for (IPlayer player : players) {
            player.sendMessage(message);
        }
    }
}