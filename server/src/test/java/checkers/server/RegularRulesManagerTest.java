package checkers.server;

import checkers.server.boards.RegularBoard;
import checkers.server.boards.RegularBoardFactory;
import org.junit.Before;

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
    public void testGetFieldType() {
        System.out.print(regularGame.getFieldType(new Coordinates(11,3)));
        assertEquals(true, regularRulesManager.checkMove(new Coordinates(12,4), new Coordinates(11,3), Checker.RED));
    }
}