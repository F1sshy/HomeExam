package player;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerFactory implements IPlayerFactory {
    @Override
    public IPlayer createPlayer(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws IOException {
        return new Player(playerID, isBot, connection, inFromClient, outToClient);
    }
}