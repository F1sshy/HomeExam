package game.logic;

import card.card;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    private ArrayList<card> hand;
    private card carrotCard;
    private card cabbageCard;
    private card tomatoCard;

    @BeforeEach
    void setUp() {
        hand = new ArrayList<>();
        carrotCard = new card(Vegetable.CARROT, "Criteria1");
        cabbageCard = new card(Vegetable.CABBAGE, "Criteria2");
        tomatoCard = new card(Vegetable.TOMATO, "Criteria3");

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