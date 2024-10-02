package card;

import main.PointSalad;

public class card {

    public static class Card {
        public enum Vegetable {
            PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
        }

        public PointSalad.Card.Vegetable vegetable;
        public String criteria;
        public boolean criteriaSideUp = true;

        public Card(PointSalad.Card.Vegetable vegetable, String criteria) {
            this.vegetable = vegetable;
            this.criteria = criteria;
        }

        @Override
        public String toString() {
            if(criteriaSideUp) {
                return criteria + " (" + vegetable + ")";
            } else {
                return vegetable.toString();
            }
        }
    }
}
