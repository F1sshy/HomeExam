package game;

import player.IPlayer;
import player.Player;

public interface IGameEngine {
    void startGame(String[] args);
    void gameLoop();
}


