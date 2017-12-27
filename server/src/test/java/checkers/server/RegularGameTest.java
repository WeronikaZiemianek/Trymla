package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.game.DefaultGamesManager;
import checkers.server.game.RegularGame;
import checkers.server.player.DefaultPlayer;
import checkers.server.rules.RegularRulesManager;
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
        regularGame = new RegularGame(board, new RegularRulesManager(board));
    }

    @Test
    public void testMakeMove() throws RemoteException {
        Player Janusz = new DefaultPlayer(gamesManager,"Janusz",Checker.RED);
        regularGame.addPlayer(Janusz);
        regularGame.startGame();
        regularGame.endMove();
        regularGame.makeMove(new Coordinates(10,12),new Coordinates(9,13),Janusz);

        assertEquals(Checker.EMPTY, board.getFieldOccupiedBy(new Coordinates(9,13)));
        assertEquals(Checker.RED, board.getFieldOccupiedBy(new Coordinates(10,12)));
    }


    @Test
    public void testEndMove() {
    }
}
