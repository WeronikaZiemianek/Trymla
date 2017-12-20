package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.RegularBoard;
import checkers.core.boards.RegularBoardFactory;
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
    private DefaultGamesMenager gamesManager;

    @Before
    public void createGame() {
        factory = new RegularBoardFactory();
        board = factory.createNewBoard(6);
        regularGame = new RegularGame(6, board);
        regularRulesManager = new RegularRulesManager(regularGame, board);
        gamesManager = new DefaultGamesMenager();
    }

    @Test
    public void testMoveCheckMove() {
        System.out.print(regularGame.getFieldType(new Coordinates(11,3)));
        assertEquals(1, regularRulesManager.checkMove(new Coordinates(12,4), new Coordinates(11,3), Checker.GREEN));
    }

    @Test
    public void test4MovesCheckMove() {
    }

    @Test
    public void testJumpCheckMove() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer( gamesManager, "player");
        turn = new Turn(player);
        assertEquals(1, regularRulesManager.checkMove(new Coordinates(8,4), new Coordinates(4,4), Checker.YELLOW));
    }

    @Test
    public void test4JumpsCheckMove() throws RemoteException {

    }


    @Test
    public void testEscapeFromHouse() {
    }

    @Test
    public void testEscapeFromTriangle() {
    }

    @Test
    public void testFieldIsNotEmpty() {

    }

}