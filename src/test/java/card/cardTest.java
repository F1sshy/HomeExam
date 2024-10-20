package card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class cardTest {

    private VeggieCard veggieCard;

    @BeforeEach
    void setUp() {
        veggieCard = new VeggieCard(Vegetable.CARROT, "TEST");

    }

    @Test
    void testToString() {
        assertEquals("TEST (CARROT)", veggieCard.toString());
    }

    @Test
    void getCriteriaSideUp() {
        assertEquals(true, veggieCard.getCriteriaSideUp());
    }

    @Test
    void getVegetable() {
        assertEquals(Vegetable.CARROT, veggieCard.getVegetable());
    }

    @Test
    void getCriteria() {
        assertEquals("TEST", veggieCard.getCriteria());
    }

    @Test
    void setCriteriaSideUp() {
        veggieCard.setCriteriaSideUp(false);
        assertEquals(false, veggieCard.getCriteriaSideUp());
    }
}