package card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class cardTest {

    private card card;

    @BeforeEach
    void setUp() {
        card = new card(Vegetable.CARROT, "TEST");

    }

    @Test
    void testToString() {
        assertEquals("TEST (CARROT)", card.toString());
    }

    @Test
    void getCriteriaSideUp() {
        assertEquals(true, card.getCriteriaSideUp());
    }

    @Test
    void getVegetable() {
        assertEquals(Vegetable.CARROT, card.getVegetable());
    }

    @Test
    void getCriteria() {
        assertEquals("TEST", card.getCriteria());
    }

    @Test
    void setCriteriaSideUp() {
        card.setCriteriaSideUp(false);
        assertEquals(false, card.getCriteriaSideUp());
    }
}