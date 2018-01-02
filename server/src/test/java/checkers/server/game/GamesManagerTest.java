package checkers.server.game;
import checkers.server.player.Player;
import checkers.server.player.DefaultPlayer;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class GamesManagerTest {
    GamesManager instance;
    DefaultPlayer player;
    DefaultPlayer player2;

    @Before
    public void createInstance() throws RemoteException {
        DefaultGamesManager.makeInstance();
        instance = DefaultGamesManager.getInstance();
        player = new DefaultPlayer(instance , "player1");
        player2 = new DefaultPlayer(instance , "player2");
    }

    @Test
    public void joinGame() throws RemoteException {
        assertEquals(false, instance.joinGame(player));
    }

    @Test
    public void createGame() throws RemoteException {
        assertEquals(true, instance.createNewGame(2));
        instance.joinGame(player);
        instance.joinGame(player2);
        assertEquals(2, player.getBoard().getExNumOfPlayers());
    }

    @Test
    public void createGameUsingPlayer() throws RemoteException {
        assertEquals(true, player.createGame(2));
        assertEquals(2, player.getBoard().getExNumOfPlayers());
        instance.joinGame(player2);
    }

    @Test
    public void createAddBot() throws RemoteException {
        assertEquals(true, player.createGame(4));
        player.addBot();
        assertEquals(2, player.getGame().getNumOfPlayers());
        player.addBot();
        player.addBot();
    }
}
