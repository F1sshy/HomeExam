package game.logic;

import card.Card;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    private ArrayList<Card> hand;
    private Card carrotCard;
    private Card cabbageCard;
    private Card tomatoCard;

    @BeforeEach
    void setUp() {
        hand = new ArrayList<>();
        carrotCard = new Card(Vegetable.CARROT, "Criteria1");
        cabbageCard = new Card(Vegetable.CABBAGE, "Criteria2");
        tomatoCard = new Card(Vegetable.TOMATO, "Criteria3");

        carrotCard.criteriaSideUp = false;
        cabbageCard.criteriaSideUp = false;
        tomatoCard.criteriaSideUp = false;

        hand.add(carrotCard);
        hand.add(cabbageCard);
        hand.add(tomatoCard);
    }

    @Test
    void countVegetables() {
        assertEquals(1, CardUtils.countVegetables(hand, Vegetable.CARROT));
        assertEquals(1, CardUtils.countVegetables(hand, Vegetable.CABBAGE));
        assertEquals(1, CardUtils.countVegetables(hand, Vegetable.TOMATO));
        assertEquals(0, CardUtils.countVegetables(hand, Vegetable.LETTUCE));
    }

    @Test
    void countTotalVegetables() {
        assertEquals(3, CardUtils.countTotalVegetables(hand));
    }
}