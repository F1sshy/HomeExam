package card;


public class CardFactory {
    public static card createCard(Vegetable vegetable, String criteria) {
        return new card(vegetable, criteria);
    }

}
