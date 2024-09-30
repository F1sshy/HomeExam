import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointSaladTest {
    private PointSalad game;

    @BeforeEach
    public void setUp() {
        game = new PointSalad();
    }

    @Test
    public void testNumberOfPlayers() {
        assertThrows(IllegalArgumentException.class, () -> game.setPlayers(1));
        assertThrows(IllegalArgumentException.class, () -> game.setPlayers(7));
        game.setPlayers(2);
        assertEquals(2, game.players.size());
        game.setPlayers(6);
        assertEquals(6, game.players.size());
    }

    @Test
    public void testDeckFormation() {
        game.setPlayers(2);
        assertEquals(36, game.deck.size());
        game.setPlayers(3);
        assertEquals(54, game.deck.size());
        game.setPlayers(4);
        assertEquals(72, game.deck.size());
        game.setPlayers(5);
        assertEquals(90, game.deck.size());
        game.setPlayers(6);
        assertEquals(108, game.deck.size());
    }

    @Test
    public void testDeckShufflingAndPileCreation() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        assertEquals(3, game.piles.size());
        assertTrue(game.piles.get(0).size() >= 24 && game.piles.get(0).size() <= 25);
        assertTrue(game.piles.get(1).size() >= 24 && game.piles.get(1).size() <= 25);
        assertTrue(game.piles.get(2).size() >= 24 && game.piles.get(2).size() <= 25);
    }

    @Test
    public void testVegetableMarketFormation() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        game.formVegetableMarket();
        assertEquals(6, game.vegetableMarket.size());
    }

    @Test
    public void testStartPlayerSelection() {
        game.setPlayers(4);
        game.chooseStartPlayer();
        assertTrue(game.currentPlayer >= 0 && game.currentPlayer < 4);
    }

    @Test
    public void testPlayerTurnActions() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        game.formVegetableMarket();
        Player player = game.players.get(0);
        game.playerDraftsPointCard(player, 0);
        assertEquals(1, player.hand.size());
        game.playerDraftsVeggieCards(player, 0, 1);
        assertEquals(3, player.hand.size());
    }

    @Test
    public void testPointCardConversion() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        game.formVegetableMarket();
        Player player = game.players.get(0);
        game.playerDraftsPointCard(player, 0);
        game.convertPointCardToVeggie(player, 0);
        assertTrue(player.hand.get(0).isVeggie());
    }

    @Test
    public void testHandVisibility() {
        game.setPlayers(4);
        Player player = game.players.get(0);
        player.hand.add(new Card("Point", "Pepper"));
        assertEquals(1, player.hand.size());
    }

    @Test
    public void testMarketReplacement() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        game.formVegetableMarket();
        game.replaceMarket();
        assertEquals(6, game.vegetableMarket.size());
    }

    @Test
    public void testDrawPileReplenishment() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        game.formVegetableMarket();
        game.replenishDrawPile();
        assertTrue(game.piles.get(0).size() > 0);
    }

    @Test
    public void testGameContinuation() {
        game.setPlayers(4);
        game.shuffleAndCreatePiles();
        game.formVegetableMarket();
        while (!game.isGameOver()) {
            game.nextTurn();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void testScoreCalculation() {
        game.setPlayers(4);
        Player player = game.players.get(0);
        player.hand.add(new Card("Point", "Pepper"));
        player.hand.add(new Card("Point", "Lettuce"));
        game.calculateScores();
        assertTrue(player.score > 0);
    }

    @Test
    public void testWinnerAnnouncement() {
        game.setPlayers(4);
        game.calculateScores();
        Player winner = game.announceWinner();
        assertNotNull(winner);
    }
}