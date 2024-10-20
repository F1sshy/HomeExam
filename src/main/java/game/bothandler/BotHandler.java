package game.bothandler;

import card.VeggieCard;
import player.IPlayer;
import market.Market;
import game.logic.VeggieScoreCalculator;
import card.ICard;
import pile.IPile;
import display.Display;

import java.util.ArrayList;

public class BotHandler {
    private Market market;
    private ArrayList<IPlayer> players;

    public BotHandler(Market market, ArrayList<IPlayer> players) {
        this.market = market;
        this.players = players;
    }

    public void handleBotTurn(IPlayer thisPlayer) {
        boolean emptyPiles = false;
        int choice = (int) (Math.random() * 2);
        if (choice == 0) {
            emptyPiles = !takeBestPointCard(thisPlayer);
        }
        if (choice == 1 || emptyPiles) {
            takeVegetableCards(thisPlayer);
        }
    }

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
}