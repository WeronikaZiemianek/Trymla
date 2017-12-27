package checkers.server.game;

import checkers.core.boards.Board;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.Player;
import checkers.server.player.DefaultPlayer;
import checkers.server.rules.RegularRulesManager;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

public class RegularGameTest {
    Game game;

    @Before
    public void createGame(){
        BoardFactory factory = new RegularBoardFactory();
        Board board = factory.createNewBoard(3);
        game = new RegularGame(board, new RegularRulesManager(board));
    }

    @Test
    public void testNormalGame() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        Player player0 = new DefaultPlayer(null, "play0");
        Player player1 = new DefaultPlayer(null, "play1");
        Player player2 = new DefaultPlayer(null, "play2");
        game.addPlayer(player0);
        game.addPlayer(player1);
        game.addPlayer(player2);

    }

}