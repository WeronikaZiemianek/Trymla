package checkers.core.boards;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegularBoardFactoryTest {
    private RegularBoardFactory factory;

    @Before
    public void createFactory() {
        factory = new RegularBoardFactory();
    }

    @Test
    public void testCreatingBoardForThreePeople() {
        Board board = factory.createNewBoard(3);
        String boardString = board.toString();
        long count = boardString.chars().filter(ch -> ch == 'r').count();
        System.out.print(boardString);
        assertEquals(10, count);
    }

    @Test
    public void testCreatingBoardForSixPeople() {
        Board board = factory.createNewBoard(6);
        String boardString = board.toString();
        long count = boardString.chars().filter(ch -> ch == '.').count();
        System.out.print(boardString);
        assertEquals(61, count);
    }

    @Test(expected = WrongNumberOfSetsException.class)
    public void testWrongNumOfSets() {
        Board board = factory.createNewBoard(5);
    }

}