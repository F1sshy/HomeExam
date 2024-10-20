package pile;

import card.VeggieCard;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class pileTest {

    private Pile testPile;
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
        testPile = new Pile(veggieCards);
    }

    @Test
    void getPointCard() {
        assertEquals(veggieCard3, testPile.getPointCard());
    }

    @Test
    void buyPointCard() {
        assertEquals(veggieCard3, testPile.buyPointCard());
        assertNull(testPile.getPointCard());
    }

    @Test
    void isEmpty() {
        assertFalse(testPile.isEmpty());
        testPile.buyPointCard();
        testPile.buyVeggieCard(0);
        testPile.buyVeggieCard(1);
        assertTrue(testPile.isEmpty());
    }

    @Test
    void areAnyVeggieCardsEmpty() {
        assertFalse(testPile.areAnyVeggieCardsEmpty());
        testPile.buyVeggieCard(0);
        assertTrue(testPile.areAnyVeggieCardsEmpty());
    }

    @Test
    void getVeggieCard() {
        assertEquals(veggieCard1, testPile.getVeggieCard(0));
        assertEquals(veggieCard2, testPile.getVeggieCard(1));
    }

    @Test
    void buyVeggieCard() {
        assertEquals(veggieCard1, testPile.buyVeggieCard(0));
        assertNull(testPile.getVeggieCard(0));
    }

    @Test
    void addCard() {
        VeggieCard newVeggieCard = new VeggieCard(Vegetable.LETTUCE, "Criteria4");
        testPile.addCard(newVeggieCard);
        assertEquals(newVeggieCard, testPile.getPointCard());
    }

    @Test
    void removeCard() {
        assertEquals(veggieCard3, testPile.removeCard());
        assertNull(testPile.getPointCard());
    }

    @Test
    void size() {
        assertEquals(1, testPile.size());
    }
}