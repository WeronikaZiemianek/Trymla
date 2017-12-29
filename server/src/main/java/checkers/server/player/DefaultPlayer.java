package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Move;
import checkers.server.Player;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import checkers.server.game.GameState;
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
    public Board getBoard() { return board; }

    @Override
    public void endJump() throws RemoteException {
        game.updatePlayers();
    }

    @Override
    public void disconnect() throws RemoteException {
        if(game != null && game.getState() != GameState.CLOSED) {
            game.disconnectPlayer(this);
        }
        gamesManager.removePlayer(this);
    }

    @Override
    public void update (Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
        if(clientPlayer != null) {
            try {
                clientPlayer.update(isMyTurn);
            } catch(RemoteException e) {
                e.printStackTrace();
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
    public int makeMove(Coordinates location, Coordinates destination) throws RemoteException {
        if(isMyTurn) {
            logger.info("Making move player " + login);
            return game.makeMove(location, destination, this);
        } else {
            logger.warn("it's not my turn");
            return -1;
        }
    }

    @Override
    public void endMove() throws RemoteException {
        if(isMyTurn) {
            game.endMove();
        }
    }

    @Override
    public boolean createGame(int numOfPlayers) throws RemoteException {
        boolean create = gamesManager.createNewGame(numOfPlayers);
        gamesManager.joinGame(this);
        return create;

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
    public void replaceWithBot(String login, int index) {
        try {
            clientPlayer.replaceWithBot(login, index);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getClientPlayer() throws RemoteException {
        clientPlayer = gamesManager.getClientPlayer(this);
        if(clientPlayer == null) {
            logger.error("client player is null");
        } else {
            logger.info("client player added to player");
        }
    }

    @Override
    public String getLogin() throws RemoteException {
        return login;
    }

    @Override
    public Move getLastMove() throws RemoteException {
        return game.getLastMove();
    }
}