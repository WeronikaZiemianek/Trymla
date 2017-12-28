package checkers.server.game;

import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.server.Player;
import checkers.core.clientServerInterfaces.RemotePlayer;

public interface GamesManager {
    void addPlayer(RemotePlayer player);
    boolean joinGame(Player player);
    boolean createNewGame(int numOfPlayers);
    void addBot();
    ClientPlayer getClientPlayer(Player player);
}
