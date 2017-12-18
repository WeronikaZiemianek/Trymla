package checkers.server;

import checkers.server.boards.Board;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultPlayer extends UnicastRemoteObject implements RemotePlayer, Player {
    private boolean isMyTurn;
    private Board board;
    private Checker color;
    private Game game;
    private GamesMenager gamesManager;
    private String login;

    DefaultPlayer(GamesMenager gamesManager, String login) throws RemoteException {
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

    void endMove() throws RemoteException {
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
