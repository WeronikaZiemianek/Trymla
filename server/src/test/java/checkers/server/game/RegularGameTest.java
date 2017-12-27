package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.Player;
import checkers.server.player.DefaultPlayer;
import checkers.server.rules.RegularRulesManager;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class RegularGameTest {
    Game game;
    DefaultPlayer player0;
    DefaultPlayer player1;
    DefaultPlayer player2;

    @Before
    public void createGame(){
        BoardFactory factory = new RegularBoardFactory();
        Board board = factory.createNewBoard(3);
        game = new RegularGame(board, new RegularRulesManager(board));
    }

    private void startGame() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        player0 = new DefaultPlayer(null, "play0");
        player1 = new DefaultPlayer(null, "play1");
        player2 = new DefaultPlayer(null, "play2");
        game.addPlayer(player0);
        game.addPlayer(player1);
        game.addPlayer(player2);
    }

    @Test
    public void testIfGameStartsProperly() throws RemoteException {
        startGame();
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    public void testMakingMoves() throws RemoteException {
        startGame();
        assertEquals(true, player0.makeMove(new Coordinates(9,13), new Coordinates(10,12)));
        System.out.println(game.getBoard());
    }

    @Test
    public void testMakingTwoMovesInRow() throws RemoteException {
        startGame();
        player0.makeMove(new Coordinates(9,13), new Coordinates(10,12));
        assertEquals(false, player0.makeMove(new Coordinates(10,12), new Coordinates(12, 12)));
    }

    @Test
    public void testMakingTwoJumpsInRow() throws RemoteException {
        startGame();
        System.out.println(game.getBoard());
      //  assertEquals(true, player0.makeMove(new Coordinates(9,13), new Coordinates(,15)));
      //  assertEquals(false, player0.makeMove(new Coordinates(8,14), new Coordinates(10, 16)));
    }

}