package pile;

import card.VeggieCard;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class pileTest {

    private VeggiePile testVeggiePile;
    private VeggieCard veggieCard1;
    private VeggieCard veggieCard2;
    private VeggieCard veggieCard3;

    @BeforeEach
    void setUp() {
        veggieCard1 = new VeggieCard(Vegetable.CARROT, "Criteria1");
        veggieCard2 = new VeggieCard(Vegetable.CABBAGE, "Criteria2");
        veggieCard3 = new VeggieCard(Vegetable.TOMATO, "Criteria3");
        ArrayList<VeggieCard> veggieCards = new ArrayList<>();
        veggieCards.add(veggieCard1);
        veggieCards.add(veggieCard2);
        veggieCards.add(veggieCard3);
        testVeggiePile = new VeggiePile(veggieCards);
    }

    @Test
    void getPointCard() {
        assertSame(veggieCard3, testVeggiePile.getPointCard());
    }

    @Test
    void buyPointCard() {
        assertAll(
                () -> assertSame(veggieCard3, testVeggiePile.buyPointCard()),
                () -> assertNull(testVeggiePile.getPointCard())
        );



    }

    @Test
    void isEmpty() {
        assertFalse(testVeggiePile.isEmpty());
        testVeggiePile.buyPointCard();
        testVeggiePile.buyVeggieCard(0);
        testVeggiePile.buyVeggieCard(1);
        assertTrue(testVeggiePile.isEmpty());
    }

    @Test
    void areAnyVeggieCardsEmpty() {
        assertAll(
                () -> assertFalse(testVeggiePile.areAnyVeggieCardsEmpty()),
                () -> testVeggiePile.buyVeggieCard(0),
                () -> testVeggiePile.buyVeggieCard(0),
                () -> testVeggiePile.buyVeggieCard(0),
                () -> assertTrue(testVeggiePile.areAnyVeggieCardsEmpty())
        );
    }

    @Test
    void getVeggieCard() {
        assertAll(
                () -> assertSame(veggieCard1, testVeggiePile.getVeggieCard(0)),
                () -> assertSame(veggieCard2, testVeggiePile.getVeggieCard(1))
        );

    }

    @Test
    void buyVeggieCard() {
        assertAll(
                () -> assertSame(veggieCard1, testVeggiePile.buyVeggieCard(0)),
                () -> assertSame(veggieCard2, testVeggiePile.getVeggieCard(1))
        );

    }

    @Test
    void addCard() {
        VeggieCard newVeggieCard = new VeggieCard(Vegetable.LETTUCE, "Criteria4");
        testVeggiePile.addCard(newVeggieCard);
        testVeggiePile.buyVeggieCard(0);
        assertSame(newVeggieCard, testVeggiePile.getPointCard());
    }

    @Test
    void addCardToEmpty() {
        VeggieCard newVeggieCard = new VeggieCard(Vegetable.LETTUCE, "Criteria4");
        testVeggiePile.buyPointCard();
        testVeggiePile.addCard(newVeggieCard);
        assertSame(newVeggieCard, testVeggiePile.getPointCard());
    }

    @Test
    void removeCard() {
        var card = testVeggiePile.removeCard();
        assertNotSame(card, testVeggiePile.getPointCard());
    }

    @Test
    void size() {
        assertEquals(1, testVeggiePile.size());
    }
}