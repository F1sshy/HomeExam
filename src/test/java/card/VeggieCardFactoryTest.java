package card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VeggieCardFactoryTest {

    private VeggieCard veggieCard = CardFactory.createCard(Vegetable.CARROT, "TEST");
    @Test
    void createCard() {
        assertEquals("TEST (CARROT)", veggieCard.toString());
    }
}