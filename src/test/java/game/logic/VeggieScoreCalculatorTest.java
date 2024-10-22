package game.logic;

import card.ICard;
import card.VeggieCard;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VeggieScoreCalculatorTest {

    private ArrayList<ICard> hand;

    @BeforeEach
    void setUp() {
        hand = new ArrayList<>();
        VeggieCard carrotCard = new VeggieCard(Vegetable.CARROT, "Criteria1");
        carrotCard.setCriteriaSideUp(false);
        hand.add(carrotCard);

        VeggieCard cabbageCard = new VeggieCard(Vegetable.CABBAGE, "Criteria2");
        cabbageCard.setCriteriaSideUp(false);
        hand.add(cabbageCard);

        VeggieCard tomatoCard = new VeggieCard(Vegetable.TOMATO, "Criteria3");
        tomatoCard.setCriteriaSideUp(false);
        hand.add(tomatoCard);

        VeggieCard cabbageCard2 = new VeggieCard(Vegetable.CABBAGE, "Criteria4");
        cabbageCard2.setCriteriaSideUp(false);
        hand.add(cabbageCard2);
    }

    @Test
    void testHandleSetCriteria() {
        int score = VeggieScoreCalculator.handleSetCriteria(hand);
        assertEquals(0, score); // Assuming all vegetables are present
    }

    @Test
    void testHandleEvenOddCriteria() {
        String criteria = "CARROT: even=10, odd=5";
        int score = VeggieScoreCalculator.handleEvenOddCriteria(criteria, hand);
        assertEquals(5, score); // Assuming odd number of CARROTs
    }

    @Test
    void testHandleAdditionCriteria() {
        String criteria = "CARROT + CABBAGE = 4";
        int score = VeggieScoreCalculator.handleAdditionCriteria(criteria, hand);
        assertEquals(4, score); // Assuming 1 CARROT and 1 CABBAGE
    }

    @Test
    void testHandleDivisionCriteria() {
        String criteria = "3 / CARROT";
        int score = VeggieScoreCalculator.handleDivisionCriteria(criteria, hand);
        assertEquals(3, score); // Assuming 1 CARROT
    }

    @Test
    void testHandleSubtractionCriteria() {
        String criteria = "2 - TOMATO";
        int score = VeggieScoreCalculator.handleSubtractionCriteria(criteria, hand);
        assertEquals(-2, score); // Assuming 1 TOMATO
    }
}