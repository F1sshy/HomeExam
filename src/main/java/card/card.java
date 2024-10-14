package card;

public class card {
    public enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }

    public Vegetable vegetable;
    public String criteria;
    public boolean criteriaSideUp = true;

    public card(Vegetable vegetable, String criteria) {
        this.vegetable = vegetable;
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        if (criteriaSideUp) {
            return criteria + " (" + vegetable + ")";
        } else {
            return vegetable.toString();
        }
    }
}
