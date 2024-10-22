package game;

import game.logic.VeggieScoreCalculator;
import pile.VeggiePile;
import player.IPlayer;
import pile.IPile;
import java.util.ArrayList;
import game.logic.VeggiePileSetup;
import display.Display;
import market.VeggieMarket;
import game.bothandler.BotHandler;
import game.playerhandler.PlayerHandler;

public class GameEngine implements IGameEngine {
    private ArrayList<IPlayer> players;
    private VeggieMarket veggieMarket;
    private Display display;
    private static GameEngine instance;
    private PlayerHandler playerHandler;
    private BotHandler botHandler;

    private GameEngine(ArrayList<IPlayer> players) {
        this.players = players;
        this.display = new Display();
    }

    public static GameEngine getInstance(ArrayList<IPlayer> players) {
        if (instance == null) {
            instance = new GameEngine(players);
        }
        return instance;
    }

    @Override
    public void startGame(String[] args) {
        VeggiePileSetup veggiePileSetup = new VeggiePileSetup();
        veggiePileSetup.setPiles(players.size());
        ArrayList<VeggiePile> veggiePiles = veggiePileSetup.getPiles();
        this.veggieMarket = VeggieMarket.getInstance(veggiePiles);
        this.playerHandler = new PlayerHandler(veggieMarket, display);
        this.botHandler = new BotHandler(veggieMarket, players);
        gameLoop();
        calculateAndAnnounceScores();
    }

    public void gameLoop() {
        int currentPlayer = (int) (Math.random() * (players.size()));
        boolean keepPlaying = true;
        IPlayer thisPlayer;
        while (keepPlaying) {
            thisPlayer = players.get(currentPlayer);
            boolean stillAvailableCards = false;
            for (IPile p : veggieMarket.getPiles()) {
                if (!p.isEmpty() || p.getVeggieCard(0) != null || p.getVeggieCard(1) != null) {
                    stillAvailableCards = true;
                    break;
                }
            }
            if (!stillAvailableCards) {
                keepPlaying = false;
                return;
            }
            veggieMarket.replaceMarket();
            if (!thisPlayer.isBot()) {
                playerHandler.handlePlayerTurn(thisPlayer);
            } else {
                botHandler.handleBotTurn(thisPlayer);
            }
            currentPlayer = (currentPlayer == players.size() - 1) ? 0 : currentPlayer + 1;
        }
        calculateAndAnnounceScores();
    }

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
}