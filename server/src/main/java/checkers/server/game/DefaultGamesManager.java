package checkers.server.game;

import checkers.core.Player;
import checkers.core.PlayerFactory;
import checkers.core.RemotePlayer;
import checkers.core.boards.Board;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
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
    }

    public void createNewGame(int numOfPlayers) {
        Board board = boardFactory.createNewBoard(numOfPlayers);
        openGame = new RegularGame(board, new RegularRulesManager(board));
    }

    @Override
    public void addPlayer(RemotePlayer player) {
        connection.putPlayer(player);
    }

    public boolean joinGame(Player player) {
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

}
