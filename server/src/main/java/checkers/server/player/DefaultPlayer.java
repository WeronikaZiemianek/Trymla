package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Player;
import checkers.core.RemotePlayer;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import checkers.server.game.GamesMenager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultPlayer extends UnicastRemoteObject implements RemotePlayer, Player {
    private boolean isMyTurn;
    private Board board;
    private Checker color;
    private Game game;
    private GamesMenager gamesManager;
    private String login;

    public DefaultPlayer(GamesMenager gamesManager, String login) throws RemoteException {
        this.gamesManager = gamesManager;
        this.login = login;
    }

    @Override
    public Checker getColor() {
        return null;
    }

    @Override
    public void update (Boolean isMyTurn) {

    }

    @Override
    public void makeMove(Coordinates location, Coordinates destination) throws RemoteException {

    }

    public void endMove() throws RemoteException {
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    @Override
    public String getLogin() throws RemoteException {
        return login;
    }
}
