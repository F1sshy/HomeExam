package game.logic;

import card.VeggieCard;
import card.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VeggieCardUtilsTest {

    private ArrayList<VeggieCard> hand;
    private VeggieCard carrotVeggieCard;
    private VeggieCard cabbageVeggieCard;
    private VeggieCard tomatoVeggieCard;

    @BeforeEach
    void setUp() {
        hand = new ArrayList<>();
        carrotVeggieCard = new VeggieCard(Vegetable.CARROT, "Criteria1");
        cabbageVeggieCard = new VeggieCard(Vegetable.CABBAGE, "Criteria2");
        tomatoVeggieCard = new VeggieCard(Vegetable.TOMATO, "Criteria3");

        carrotVeggieCard.criteriaSideUp = false;
        cabbageVeggieCard.criteriaSideUp = false;
        tomatoVeggieCard.criteriaSideUp = false;

        hand.add(carrotVeggieCard);
        hand.add(cabbageVeggieCard);
        hand.add(tomatoVeggieCard);
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