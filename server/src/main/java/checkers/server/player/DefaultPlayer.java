package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Player;
import checkers.core.RemotePlayer;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import checkers.server.game.GamesManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultPlayer extends UnicastRemoteObject implements RemotePlayer, Player {
    private boolean isMyTurn;
    private Board board;
    private Checker color;
    private Game game;
    private GamesManager gamesManager;
    private String login;

    public DefaultPlayer(GamesManager gamesManager, String login, Checker color) throws RemoteException {
        this.gamesManager = gamesManager;
        this.login = login;
        this.color = color;
    }

    @Override
    public Checker getColor() {
        return color;
    }

    @Override
    public void update (Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;

    }

    @Override
    public void makeMove(Coordinates location, Coordinates destination) throws RemoteException {

    }

    public void endMove() throws RemoteException {
        if(isMyTurn) {
            game.endMove();
        }
    }

    public void setGame(Game game) {
        this.game = game;
        board = game.getBoard();
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    @Override
    public String getLogin() throws RemoteException {
        return login;
    }
}
