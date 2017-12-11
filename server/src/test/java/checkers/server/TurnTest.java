package checkers.server;

import checkers.server.boards.Board;
import checkers.server.boards.RegularBoardFactory;
import org.junit.Before;
import org.junit.Test;

import static checkers.server.Checker.GREEN;
import static org.junit.Assert.assertEquals;

public class TurnTest {

    private RegularGame regularGame;
    private RegularBoardFactory factory;

    @Before
    public void createGame() {
        regularGame = new RegularGame(6);
        factory = new RegularBoardFactory();
    }

    @Test
    public void testGetCurrentMove() {
        Board board = factory.createNewBoard(6);
        DefaultPlayer player = new DefaultPlayer(board, Checker.GREEN);
        Turn turn = new Turn(player);
        assertEquals(null, turn.getCurrMov());
    }

    @Test
    public void testSetCurrentMove() {
        Board board = factory.createNewBoard(6);
        DefaultPlayer player = new DefaultPlayer(board, Checker.GREEN);
        Turn turn = new Turn(player);
        turn.setCurrMov(new Coordinates(6,6));
        assertEquals((new Coordinates(6,6)).X(), turn.getCurrMov().X());
        assertEquals((new Coordinates(6,6)).Y(), turn.getCurrMov().Y());
    }

    @Test
    public void testGetPlayer() {
        Board board = factory.createNewBoard(6);
        DefaultPlayer player = new DefaultPlayer(board, GREEN);
        Turn turn = new Turn(player);
        turn.setCurrMov(new Coordinates(12,0));
        assertEquals(new Coordinates(12,0).X(), turn.getCurrMov().X());
        assertEquals(new Coordinates(12,0).Y(), turn.getCurrMov().Y());
    }

    @Test
    public void testPlayerJumped() {
        Board board = factory.createNewBoard(6);
        DefaultPlayer player = new DefaultPlayer(board, GREEN);
        Turn turn = new Turn(player);
        turn.playerJumped();
        assertEquals(true, turn.getJumped());
    }

    @Test
    public void testJumpReset() {
        Board board = factory.createNewBoard(6);
        DefaultPlayer player = new DefaultPlayer(board, GREEN);
        Turn turn = new Turn(player);
        turn.playerJumped();
        assertEquals(true, turn.getJumped());
        turn.jumpReset();
        assertEquals(false, turn.getJumped());
    }
}
