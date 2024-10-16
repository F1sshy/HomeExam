package card;

public interface IPile {
    card getPointCard();
    card buyPointCard();
    card getVeggieCard(int index);
    card buyVeggieCard(int index);
    boolean isEmpty();
}
