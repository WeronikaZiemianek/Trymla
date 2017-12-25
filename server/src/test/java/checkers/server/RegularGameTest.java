package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Player;
import checkers.core.boards.Board;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.game.DefaultGamesManager;
import checkers.server.game.RegularGame;
import checkers.server.game.Turn;
import checkers.server.player.DefaultPlayer;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class RegularGameTest
{
    private RegularGame regularGame;
    private RegularBoardFactory factory;
    private Board board;
    private DefaultGamesManager gamesManager;

    @Before
    public void createGame() throws RemoteException {
        factory = new RegularBoardFactory();
        board = factory.createNewBoard(6);
        regularGame = new RegularGame( board);
    }

    @Test
    public void testMakeMove() throws RemoteException {
        Player Janusz = new DefaultPlayer(gamesManager,"Janusz",Checker.RED);
        regularGame.addPlayer(Janusz);
        regularGame.endMove();
        regularGame.makeMove(new Coordinates(10,12),new Coordinates(9,13),Janusz);

        assertEquals(Checker.EMPTY, board.getFieldOccupiedBy(new Coordinates(9,13)));
        assertEquals(Checker.RED, board.getFieldOccupiedBy(new Coordinates(10,12)));
    }

    @Test
    public void testGetTurn() throws RemoteException {
        Player player = new DefaultPlayer(null, "login", Checker.EMPTY);
        Turn turn = new Turn(player);
        assertEquals(null, regularGame.getTurn());
    }

    @Test
    public void testEndMove() {
    }
}
