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

        VeggieCard cabbageCard3 = new VeggieCard(Vegetable.CABBAGE, "Criteria4");
        cabbageCard3.setCriteriaSideUp(false);
        hand.add(cabbageCard3);

        VeggieCard criteCard1 = new VeggieCard(Vegetable.TOMATO, "CARROT + CABBAGE = 4");
        hand.add(criteCard1);

        VeggieCard criteCard2 = new VeggieCard(Vegetable.TOMATO, " TOMATO: EVEN=10, ODD=5");
        hand.add(criteCard2);

        VeggieCard criteCard3 = new VeggieCard(Vegetable.TOMATO, "2/CABBAGE,  1/CARROT,  -2/TOMATO");
        hand.add(criteCard3);



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
        String criteria = "CARROT + CABBAGE + TOMATO = 4";
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
        String criteria = "-2 / TOMATO";
        int score = VeggieScoreCalculator.handleSubtractionCriteria(criteria, hand);
        assertEquals(-2, score); // Assuming 1 TOMATO
    }

    @Test
    void testHandleComplexCriteria() {
        int score = VeggieScoreCalculator.calculateScore(hand, null, null);
        assertEquals(14, score); // Assuming 1 CARROT, 1 CABBAGE, 1 TOMATO
    }
}