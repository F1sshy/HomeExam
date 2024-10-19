package card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardFactoryTest {

    private Card card = CardFactory.createCard(Vegetable.CARROT, "TEST");
    @Test
    void createCard() {
        assertEquals("TEST (CARROT)", card.toString());
    }
}