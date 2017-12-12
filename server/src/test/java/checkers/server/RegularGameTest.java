package checkers.server;

import checkers.server.boards.Board;
import checkers.server.boards.RegularBoardFactory;
import org.junit.Before;
import org.junit.Test;

import static checkers.server.Checker.GREEN;
import static org.junit.Assert.assertEquals;

public class RegularGameTest
{
    private RegularGame regularGame;
    private RegularBoardFactory factory;
    private Board board;

    @Before
    public void createGame() {
        factory = new RegularBoardFactory();
        board = factory.createNewBoard(6);
        regularGame = new RegularGame(6, board);
    }

    @Test
    public void testMakeMove() {
        board.makeMove(new Coordinates(12,0), new Coordinates(6, 6));
        assertEquals(Checker.EMPTY, board.getFieldOccupiedBy(new Coordinates(12,0)));
        assertEquals(Checker.GREEN, board.getFieldOccupiedBy(new Coordinates(6,6)));
    }

    @Test
    public void testGetOccupiedBy() {
        assertEquals(board.getFieldOccupiedBy(new Coordinates(12,0)), regularGame.getOccupiedByType(new Coordinates(12,0)));
    }

    @Test
    public void testGetFieldType() {
        Checker color = regularGame.getFieldType(new Coordinates(12,0));
        assertEquals(color,Checker.RED);
    }


    @Test
    public void testGetTurn() {
        DefaultPlayer player = new DefaultPlayer(board, GREEN);
        Turn turn = new Turn(player);
        assertEquals(null, regularGame.getTurn());
    }

    @Test
    public void testEndMove() {
    }
}
