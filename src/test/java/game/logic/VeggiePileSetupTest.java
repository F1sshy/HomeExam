package game.logic;

import org.junit.jupiter.api.BeforeEach;

class VeggiePileSetupTest {

    private VeggiePileSetup veggiePileSetup;

    @BeforeEach
    void setUp() {
        veggiePileSetup = new VeggiePileSetup();
    }
    /*
    @Test
    void setPiles() {
        veggiePileSetup.setPiles(4); // Assuming 4 players
        ArrayList<pile> piles = veggiePileSetup.getPiles();
        assertEquals(3, piles.size()); // There should be 3 piles

        int totalCards = piles.stream().mapToInt(p -> p.getCards().size()).sum();
        assertEquals(24, totalCards); // Assuming each player gets 6 cards per vegetable type
    }

    @Test
    void getPiles() {
        veggiePileSetup.setPiles(4); // Assuming 4 players
        ArrayList<pile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size()); // There should be 3 piles

        for (pile p : piles) {
            assertNotNull(p.getCards());
            assertFalse(p.getCards().isEmpty());
        }
    }

     */
}