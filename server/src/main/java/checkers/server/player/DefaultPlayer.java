package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.server.Player;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
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
    private ClientPlayer clientPlayer;

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
        try {
            clientPlayer.update(isMyTurn);
        } catch(RemoteException e) {
        }

    }

    @Override
    public void endGame(String login) {
        try {
            clientPlayer.gameOver(login);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewPlayer(String login, Checker color) {
        try {
            clientPlayer.newPlayerAdded(login, color);
        } catch(RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void makeMove(Coordinates location, Coordinates destination) throws RemoteException {

    }

    @Override
    public void endMove() throws RemoteException {
        if(isMyTurn) {
            game.endMove();
        }
    }

    @Override
    public void createGame(int numOfPlayers) throws RemoteException {
        gamesManager.createNewGame(numOfPlayers);
        gamesManager.joinGame(this);

    }

    @Override
    public boolean joinGame() throws RemoteException {
        return gamesManager.joinGame(this);
    }

    @Override
    public void addBot() throws RemoteException {
        gamesManager.addBot();
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        board = game.getBoard();
    }


    @Override
    public String getLogin() throws RemoteException {
        return login;
    }
}
