package checkers.server.game;

import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.server.Player;
import checkers.core.clientServerInterfaces.PlayerFactory;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.boards.Board;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.player.DefaultBot;
import checkers.server.player.DefaultPlayerFactory;
import checkers.server.Connection;
import checkers.server.rules.RegularRulesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class DefaultGamesManager implements GamesManager {
    static Logger logger = LoggerFactory.getLogger(DefaultGamesManager.class);

    private PlayerFactory playerFactory;
    private Connection connection;
    private BoardFactory boardFactory;
    private ArrayList<Game> runningGames;
    private Game openGame;

    public DefaultGamesManager(){
        logger.debug("Creating player factory");
        try {
            playerFactory = new DefaultPlayerFactory(this);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        logger.debug("Connecting to factory");
        connection = new Connection(playerFactory);
        boardFactory = new RegularBoardFactory();
        runningGames = new ArrayList<>();
    }

    @Override
    synchronized public boolean createNewGame(int numOfPlayers) {
        if(openGame == null) {
            Board board = boardFactory.createNewBoard(numOfPlayers);
            openGame = new RegularGame(board, new RegularRulesManager(board));
            logger.info("created new game for " + numOfPlayers);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ClientPlayer getClientPlayer(Player player) {
        return connection.getClientPlayer(player);
    }

    @Override
    synchronized public void addPlayer(RemotePlayer player) {
        connection.putPlayer(player);
    }

    @Override
    synchronized public boolean joinGame(Player player) {
        if(openGame != null) {
            openGame.addPlayer(player);
            if(openGame.getState() == GameState.RUNNING) {
                runningGames.add(openGame);
                openGame = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public synchronized void addBot() {
        Player bot = new DefaultBot(this, "bot" + openGame.getNumOfPlayers());
        openGame.addPlayer(bot);
    }


}
