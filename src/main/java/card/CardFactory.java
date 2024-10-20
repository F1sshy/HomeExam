package card;


public class CardFactory {
    public static VeggieCard createCard(Vegetable vegetable, String criteria) {
        return new VeggieCard(vegetable, criteria);
    }

}
