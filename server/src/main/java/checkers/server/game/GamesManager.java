package checkers.server.game;

import checkers.server.Player;
import checkers.core.clientServerInterfaces.RemotePlayer;

public interface GamesManager {
    void addPlayer(RemotePlayer player);
    boolean joinGame(Player player);
    void createNewGame(int numOfPlayers);
    void addBot();
}
