package card;

public interface ICard {
    enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }

    card.Vegetable getVegetable();
    String getCriteria();
    boolean getCriteriaSideUp();
    void setCriteriaSideUp(boolean criteriaSideUp);
    String toString();
}
