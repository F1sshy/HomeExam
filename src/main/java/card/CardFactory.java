package card;


public class CardFactory {
    public static Card createCard(Vegetable vegetable, String criteria) {
        return new Card(vegetable, criteria);
    }

}
