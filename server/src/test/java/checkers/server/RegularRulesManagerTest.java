package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.RegularBoard;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.game.DefaultGamesManager;
import checkers.server.game.RegularGame;
import checkers.server.game.Turn;
import checkers.server.player.DefaultPlayer;
import checkers.server.rules.RegularRulesManager;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class RegularRulesManagerTest {
    private RegularGame regularGame;
    private RegularBoardFactory factory;
    private RegularRulesManager regularRulesManager;
    private RegularBoard board;
    private Turn turn;
    private DefaultGamesManager gamesManager;

    @Before
    public void createGame() throws RemoteException {
        factory = new RegularBoardFactory();
        board = factory.createNewBoard(6);
        regularRulesManager = new RegularRulesManager(board);
        gamesManager = new DefaultGamesManager();
        regularGame = new RegularGame( board);
    }

    @Test
    public void testMoveCheckMove() throws RemoteException {
        System.out.print(board.getFieldType(new Coordinates(11,3)));
        regularGame.addPlayer(new DefaultPlayer(gamesManager, "Janusz", Checker.GREEN));
        assertEquals(true , regularRulesManager.checkMove(regularGame,new Coordinates(12,4), new Coordinates(11,3), Checker.GREEN));
}

    @Test
    public void test4MovesCheckMove() {
    }

    @Test
    public void testJumpCheckMove() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer( gamesManager, "player", Checker.YELLOW);
        regularGame.addPlayer(player);
        regularGame.endMove();
        assertEquals(true, regularRulesManager.checkMove(regularGame,new Coordinates(8,4), new Coordinates(4,4), Checker.YELLOW));
    }

    @Test
    public void test4JumpsCheckMove() throws RemoteException {

    }


    @Test
    public void testEscapeFromHouse() throws RemoteException {

    }

    @Test
    public void testEscapeFromTriangle() {
    }

    @Test
    public void testFieldIsNotEmpty() {

    }

}