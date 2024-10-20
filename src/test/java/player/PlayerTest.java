package player;

import card.VeggieCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import card.Vegetable;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private PipedInputStream pipedInputStream;
    private PipedOutputStream pipedOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    @BeforeEach
    void setUp() throws IOException {
        pipedInputStream = new PipedInputStream();
        pipedOutputStream = new PipedOutputStream(pipedInputStream);
        objectOutputStream = new ObjectOutputStream(pipedOutputStream);
        objectInputStream = new ObjectInputStream(pipedInputStream);
        player = new Player(1, false, null, objectInputStream, objectOutputStream);
    }

    @Test
    void getPlayerID() {
        assertEquals(1, player.getPlayerID());
    }



    @Test
    void getHand() {
        assertTrue(player.getHand().isEmpty());
        VeggieCard newVeggieCard = new VeggieCard(Vegetable.CARROT, "Criteria1");
        player.addCardToHand(newVeggieCard);
        assertEquals(1, player.getHand().size());
        assertEquals(newVeggieCard, player.getHand().get(0));
    }

    @Test
    void addCardToHand() {
        VeggieCard newVeggieCard = new VeggieCard(Vegetable.CARROT, "Criteria1");
        player.addCardToHand(newVeggieCard);
        assertEquals(1, player.getHand().size());
        assertEquals(newVeggieCard, player.getHand().get(0));
    }

    @Test
    void getScore() {
        assertEquals(0, player.getScore());
        player.setScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void setScore() {
        player.setScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void isBot() throws IOException {
        assertFalse(player.isBot());
        Player botPlayer = new Player(2, true, null, null, null);
        assertTrue(botPlayer.isBot());
    }
}