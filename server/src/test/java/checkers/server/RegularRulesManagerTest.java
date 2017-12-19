package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.RegularBoard;
import checkers.core.boards.RegularBoardFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegularRulesManagerTest {
    private RegularGame regularGame;
    private RegularBoardFactory factory;
    private RegularRulesManager regularRulesManager;
    private RegularBoard board;

    @Before
    public void createGame() {
        factory = new RegularBoardFactory();
        board = factory.createNewBoard(6);
        regularGame = new RegularGame(6, board);
        regularRulesManager = new RegularRulesManager(regularGame);
    }

    @Test
    public void testMoveCheckMove() {
        System.out.print(regularGame.getFieldType(new Coordinates(11,3)));
        assertEquals(1, regularRulesManager.checkMove(new Coordinates(12,4), new Coordinates(11,3), Checker.GREEN));
    }
    @Test
    public void testJumpCheckMove() {
        System.out.print(regularGame.getFieldType(new Coordinates(11,3)));
        assertEquals(2, regularRulesManager.checkMove(new Coordinates(11,3), new Coordinates(13,5), Checker.GREEN));
    }
}