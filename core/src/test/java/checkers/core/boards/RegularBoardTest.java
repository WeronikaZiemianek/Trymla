package checkers.core.boards;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegularBoardTest {

    @Test
    public void testCreatingBoardForThreePeople() {
        RegularBoardFactory factory = new RegularBoardFactory();
        Board board = factory.createNewBoard(3);
        String boardString = board.toString();
        long count = boardString.chars().filter(ch -> ch == 'r').count();
        assertEquals(10, count);
        System.out.print(boardString);
    }

    @Test
    public void testCreatingBoardForSixPeople() {
        RegularBoardFactory factory = new RegularBoardFactory();
        Board board = factory.createNewBoard(6);
        String boardString = board.toString();
        long count = boardString.chars().filter(ch -> ch == 'n').count();
        assertEquals(10, count);
        System.out.print(boardString);
    }

}