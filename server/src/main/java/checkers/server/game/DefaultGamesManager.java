package checkers.server.game;

import checkers.core.PlayerFactory;
import checkers.core.RemotePlayer;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.player.DefaultPlayerFactory;
import checkers.server.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class DefaultGamesManager implements GamesManager {
    static Logger logger = LoggerFactory.getLogger(DefaultGamesManager.class);

    private PlayerFactory playerFactory;
    private Connection connection;
    private BoardFactory boardFactory;
    private Game game;


    public DefaultGamesManager(){
        logger.debug("Creating player factory");
        try {
            playerFactory = new DefaultPlayerFactory(this);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        logger.debug("Connecting to factory");
        connection = new Connection(playerFactory);

        // To zamknąć i obsługiwac w UI
        boardFactory = new RegularBoardFactory();
        game = new RegularGame(boardFactory.createNewBoard(6));

    }

    @Override
    public void addPlayer(RemotePlayer player) {
        connection.putPlayer(player);
    }

    // @Override
    // public void sayHe;;p() throws RemoteException { System.out.println("Hello world");}
}
