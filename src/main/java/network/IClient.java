package network;

public interface IClient {
    void run(String ipAddress, int port) throws Exception;
}
