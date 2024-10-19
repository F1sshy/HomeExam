package pile;

import card.Card;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class pileTest {

    private Pile testPile;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void setUp() {
        card1 = new Card(Vegetable.CARROT, "Criteria1");
        card2 = new Card(Vegetable.CABBAGE, "Criteria2");
        card3 = new Card(Vegetable.TOMATO, "Criteria3");
        ArrayList<Card> Cards = new ArrayList<>();
        Cards.add(card1);
        Cards.add(card2);
        Cards.add(card3);
        testPile = new Pile(Cards);
    }

    @Test
    void getPointCard() {
        assertEquals(card3, testPile.getPointCard());
    }

    @Test
    void buyPointCard() {
        assertEquals(card3, testPile.buyPointCard());
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
        assertEquals(card1, testPile.getVeggieCard(0));
        assertEquals(card2, testPile.getVeggieCard(1));
    }

    @Test
    void buyVeggieCard() {
        assertEquals(card1, testPile.buyVeggieCard(0));
        assertNull(testPile.getVeggieCard(0));
    }

    @Test
    void addCard() {
        Card newCard = new Card(Vegetable.LETTUCE, "Criteria4");
        testPile.addCard(newCard);
        assertEquals(newCard, testPile.getPointCard());
    }

    @Test
    void removeCard() {
        assertEquals(card3, testPile.removeCard());
        assertNull(testPile.getPointCard());
    }

    @Test
    void size() {
        assertEquals(1, testPile.size());
    }
}