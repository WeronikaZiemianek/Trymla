package checkers.core.boards;

import checkers.core.Checker;
import checkers.core.Coordinates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegularBoardTest {
    private RegularBoardFactory factory;

    @Before
    public void createFactory() {
        factory = new RegularBoardFactory();
    }

    @Test
    public void testMakeMove() {
        Board board = factory.createNewBoard(6);
        board.makeMove( new Coordinates(12,0), new Coordinates(6,6 ));
        assertEquals(Checker.EMPTY, board.getFieldOccupiedBy(new Coordinates(12,0)));
    }

    @Test
    public void testMakingMoves() {
        Board board = factory.createNewBoard(3);
        board.makeMove(new Coordinates(21,7 ), new Coordinates(6, 6));
        board.makeMove(new Coordinates(20, 4), new Coordinates(8, 10));
        board.makeMove(new Coordinates(13, 13), new Coordinates(14, 10));
        board.makeMove(new Coordinates(13, 15), new Coordinates(21, 7));
        String boardString = board.toString();
        long countN = boardString.chars().filter(ch -> ch == 'n').count();
        long countR = boardString.chars().filter(ch -> ch == 'r').count();
        assertEquals(10, countN);
        assertEquals(10, countR);
    }

    @Test(expected = WrongMoveException.class)
    public void testWrongMove() {
        Board board = factory.createNewBoard(3);
        board.makeMove(new Coordinates(20, 4), new Coordinates(21,7));
    }

    @Test
    public void colorForPlayerInBoardFor4() {
        Board board = factory.createNewBoard(4);
        assertEquals(Checker.BLACK ,board.colorForPlayer(3));
    }

    @Test
    public void colorForPlayerInBoardFor3() {
        Board board = factory.createNewBoard(3);
        assertEquals(Checker.YELLOW,board.colorForPlayer(1));
    }

    @Test
    public void WrongMoveExeption() {
        try{
            Board board = factory.createNewBoard(3);
            board.makeMove(new Coordinates(0,0),new Coordinates(0,0));
            assertTrue(false);
        }
        catch (WrongMoveException e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void WrongNumberOfSetsExeptoion(){
        try{
            Board board = factory.createNewBoard(7);

            assertTrue(false);
        }
        catch (WrongNumberOfSetsException e)
        {
            assertTrue(true);
        }
    }

}