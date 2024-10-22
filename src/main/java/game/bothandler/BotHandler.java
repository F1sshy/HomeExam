package game.bothandler;

import player.IPlayer;
import market.VeggieMarket;
import game.logic.VeggieScoreCalculator;
import card.ICard;
import pile.IPile;

import java.util.ArrayList;

public class BotHandler {
    private VeggieMarket veggieMarket;
    private ArrayList<IPlayer> players;

    public BotHandler(VeggieMarket veggieMarket, ArrayList<IPlayer> players) {
        this.veggieMarket = veggieMarket;
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
        for (int i = 0; i < veggieMarket.getPiles().size(); i++) {
            if (veggieMarket.getPiles().get(i).getPointCard() != null) {
                ArrayList<ICard> tempHand = new ArrayList<>();
                tempHand.addAll(thisPlayer.getHand());
                tempHand.add(veggieMarket.getPiles().get(i).getPointCard());
                int score = VeggieScoreCalculator.calculateScore(tempHand, thisPlayer, players);
                if (score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }
        if (veggieMarket.getPiles().get(highestPointCardIndex).getPointCard() != null) {
            thisPlayer.getHand().add(veggieMarket.getPiles().get(highestPointCardIndex).buyPointCard());
            return true;
        }
        return false;
    }

    private void takeVegetableCards(IPlayer thisPlayer) {
        int cardsPicked = 0;
        for (IPile pile : veggieMarket.getPiles()) {
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