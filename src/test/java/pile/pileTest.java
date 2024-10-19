package pile;

import card.card;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class pileTest {

    private pile testPile;
    private card card1;
    private card card2;
    private card card3;

    @BeforeEach
    void setUp() {
        card1 = new card(Vegetable.CARROT, "Criteria1");
        card2 = new card(Vegetable.CABBAGE, "Criteria2");
        card3 = new card(Vegetable.TOMATO, "Criteria3");
        ArrayList<card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        testPile = new pile(cards);
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
        card newCard = new card(Vegetable.LETTUCE, "Criteria4");
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