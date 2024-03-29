package checkers.server;


import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.game.DefaultGamesManager;
import checkers.server.game.RegularGame;
import checkers.server.player.DefaultPlayer;
import checkers.server.player.Player;
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
        regularGame = new RegularGame(board, new RegularRulesManager(board), 0);
    }

    @Test
    public void testMakeMove() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        Player Janusz = new DefaultPlayer(gamesManager,"Janusz");
        regularGame.addPlayer(Janusz);
        System.out.println(Janusz.getColor());
        regularGame.startGame();
        regularGame.makeMove(new Coordinates(9, 13), new Coordinates(10,12), Janusz);
        assertEquals(Checker.EMPTY, board.getFieldOccupiedBy(new Coordinates(9,13)));
        assertEquals(Checker.RED, board.getFieldOccupiedBy(new Coordinates(10,12)));
    }

    @Test
    public void getNumOfPlayers () throws RemoteException {
        Player Janusz = new DefaultPlayer(gamesManager,"Janusz");
        regularGame.addPlayer(Janusz);
        Player Jan = new DefaultPlayer(gamesManager,"Janusz");
        regularGame.addPlayer(Jan);
        Player usz = new DefaultPlayer(gamesManager,"Janusz");
        regularGame.addPlayer(usz);
        Player J = new DefaultPlayer(gamesManager,"Janusz");
        regularGame.addPlayer(J);
        assertEquals(4,regularGame.getNumOfPlayers());
    }


    @Test
    public void testEndMove() {
    }
}
