package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.player.DefaultBot;
import checkers.server.player.DefaultPlayer;
import checkers.server.rules.RegularRulesManager;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class GameTest {
    Game game;
    DefaultPlayer player0;
    DefaultPlayer player1;
    DefaultPlayer player2;
    DefaultBot bot1;
    DefaultBot bot2;
    DefaultBot bot3;

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
        game.updatePlayers();
    }

    private void startGame2() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        bot1 = new DefaultBot("bot1");
        bot2 = new DefaultBot("bot2");
        bot3 = new DefaultBot("bot3");
        game.addPlayer(bot1);
        game.addPlayer(bot2);
        game.addPlayer(bot3);
        game.updatePlayers();
    }

    private void startGame3() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        player0 = new DefaultPlayer(null, "play0");
        bot2 = new DefaultBot("bot2");
        bot3 = new DefaultBot("bot3");
        game.addPlayer(player0);
        game.addPlayer(bot2);
        game.addPlayer(bot3);
        game.updatePlayers();
    }

    @Test
    public void testIfGameStartsProperly() throws RemoteException {
        startGame();
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    public void testMakingMoves() throws RemoteException {
        startGame();
        assertEquals(2, player0.makeMove(new Coordinates(9,13), new Coordinates(10,12)));
        System.out.println(game.getBoard());
    }

    @Test
    public void testMakingMovesByBots() throws RemoteException {
        startGame3();
        assertEquals(2, player0.makeMove(new Coordinates(9,13), new Coordinates(10,12)));
        player0.endMove();
        System.out.println(game.getBoard());
    }

    @Test
    public void testMakingTwoMovesInRow() throws RemoteException {
        startGame();
        player0.makeMove(new Coordinates(9,13), new Coordinates(10,12));
        assertEquals(-1, player0.makeMove(new Coordinates(10,12), new Coordinates(12, 12)));
    }

    @Test
    public void testMakingJump() throws RemoteException {
        startGame();
        assertEquals(4, player0.makeMove(new Coordinates(10,14), new Coordinates(12,12)));
        System.out.println(game.getBoard());
    }

    private void player0Moves() throws RemoteException {
        System.out.print(game.getBoard().toString());
        player0.makeMove(new Coordinates(11,13), new Coordinates(10,12));
        player0.endMove();
    }

    @Test
    public void player2Move() throws RemoteException {
        startGame();
        player0Moves();
        System.out.println(player1.getColor());
        assertEquals(4, player1.makeMove(new Coordinates(4,4), new Coordinates(8,4)));
    }

    private void player23Move() throws RemoteException {
        player1.makeMove(new Coordinates(4,4), new Coordinates(8,4));
        player1.endMove();
        player2.makeMove(new Coordinates(18, 4), new Coordinates(17, 5));
        player2.endMove();
    }

    @Test
    public void make2Jumps() throws RemoteException {
        startGame();
        player0Moves();
        player23Move();
        assertEquals(4, player0.makeMove(new Coordinates(13, 15), new Coordinates(11, 13)));
        assertEquals(4, player0.makeMove(new Coordinates(11, 13), new Coordinates(9, 11)));
    }

}