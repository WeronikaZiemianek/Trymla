package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.server.Player;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import checkers.server.game.GamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger;

    public DefaultPlayer(GamesManager gamesManager, String login) throws RemoteException {
        this.gamesManager = gamesManager;
        this.login = login;
        logger = LoggerFactory.getLogger(DefaultPlayer.class);
    }

    @Override
    public Checker getColor() {
        return color;
    }

    @Override
    public void update (Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
        if(clientPlayer != null) {
            try {
                clientPlayer.update(isMyTurn);
            } catch(RemoteException e) {
                logger.error("cant update client player");
            }
        } else {
            logger.warn("clientPlayer = null");
        }

    }

    @Override
    public void endGame(String login) {
        logger.info("game finished winner is: " + login);
        if(clientPlayer != null) {
            try {
                clientPlayer.gameOver(login);
            } catch(RemoteException e) {
                logger.error("cant do gameOver on clientPlayer");
            }
        } else {
            logger.warn("clientPlayer = null");
        }
    }

    @Override
    public void addNewPlayer(String login, Checker color) {
        logger.info("new player " + login + " " + color + " in player " + this.login);
        if(clientPlayer != null) {
            try {
                clientPlayer.newPlayerAdded(login, color);
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("client player is null");
        }

    }

    @Override
    public boolean makeMove(Coordinates location, Coordinates destination) throws RemoteException {
        return game.makeMove(location, destination, this);
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
    public void setGameAndColor(Game game, Checker color, Board board) {
        this.game = game;
        this.board = board;
        this.color = color;
    }

    @Override
    public String getPlayerName() {
        return login;
    }

    @Override
    public void getClientPlayer() throws RemoteException {
        clientPlayer = gamesManager.getClientPlayer(this);
    }

    @Override
    public String getLogin() throws RemoteException {
        return login;
    }
}
