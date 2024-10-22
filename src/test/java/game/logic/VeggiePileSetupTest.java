package game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pile.VeggiePile;
import card.ICard;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VeggiePileSetupTest {

    private VeggiePileSetup veggiePileSetup;

    @BeforeEach
    void setUp() {
        veggiePileSetup = new VeggiePileSetup();
    }

    // Tests for specific number of players
    // Piles are going to be 2 less than instructed
    // because the veggie cards are not included in the piles

    @Test
    void setPiles_twoPlayers() {
        veggiePileSetup.setPiles(2);
        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size());
        assertEquals(10, piles.get(0).size());
        assertEquals(10, piles.get(1).size());
        assertEquals(10, piles.get(2).size());
    }

    @Test
    void setPiles_threePlayers() {
        veggiePileSetup.setPiles(3);
        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size());
        assertEquals(16, piles.get(0).size());
        assertEquals(16, piles.get(1).size());
        assertEquals(16, piles.get(2).size());
    }

    @Test
    void setPiles_fourPlayers() {
        veggiePileSetup.setPiles(4);
        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size());
        assertEquals(22, piles.get(0).size());
        assertEquals(22, piles.get(1).size());
        assertEquals(22, piles.get(2).size());
    }

    @Test
    void setPiles_fivePlayers() {
        veggiePileSetup.setPiles(5);
        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size());
        assertEquals(28, piles.get(0).size());
        assertEquals(28, piles.get(1).size());
        assertEquals(28, piles.get(2).size());
    }

    @Test
    void setPiles_sixPlayers() {
        veggiePileSetup.setPiles(6);
        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size());
        // Assuming the total deck size is 108 cards
        assertEquals(34, piles.get(0).size());
        assertEquals(34, piles.get(1).size());
        assertEquals(34, piles.get(2).size());
    }

//    @Test
//    void vegetableMarketSetup() {
//        veggiePileSetup.setPiles(3);
//        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
//        assertNotNull(piles);
//        assertEquals(3, piles.size());
//
//        // Check that two cards are flipped over from each pile
//        for (VeggiePile pile : piles) {
//            assertEquals(2, pile.getFlippedCards().size());
//        }
//    }
}