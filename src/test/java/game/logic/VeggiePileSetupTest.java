package game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pile.VeggiePile;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class VeggiePileSetupTest {

    private VeggiePileSetup veggiePileSetup;

    @BeforeEach
    void setUp() {
        veggiePileSetup = new VeggiePileSetup();
    }

    @Test
    void setPiles() {
        veggiePileSetup.setPiles(1);
        ArrayList<VeggiePile> piles = veggiePileSetup.getPiles();
        assertNotNull(piles);
        assertEquals(3, piles.size());
    }

    @Test
    void getPiles() {
        veggiePileSetup.setPiles(1);
        ArrayList<VeggiePile> retrievedPiles = veggiePileSetup.getPiles();
        assertNotNull(retrievedPiles);
        assertEquals(3, retrievedPiles.size());
    }
}