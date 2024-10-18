package card;

public interface ICard {

    Vegetable getVegetable();
    String getCriteria();
    boolean getCriteriaSideUp();
    void setCriteriaSideUp(boolean criteriaSideUp);
    String toString();
}
