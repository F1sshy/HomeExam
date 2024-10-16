package network;

public interface IServer {
    void server(int numberPlayers, int numberOfBots) throws Exception;
    void sendToAllPlayers(String message);
}
