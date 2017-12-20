package checkers.server.game;

import checkers.core.PlayerFactory;
import checkers.core.RemotePlayer;
import checkers.core.boards.BoardFactory;
import checkers.core.boards.RegularBoardFactory;
import checkers.server.player.DefaultPlayerFactory;
import checkers.server.Connection;

import java.rmi.RemoteException;

public class DefaultGamesManager implements GamesManager {
    private PlayerFactory playerFactory;
    private Connection connection;
    private BoardFactory boardFactory;
    private Game game;

    public DefaultGamesManager() throws RemoteException {
        try {
            playerFactory = new DefaultPlayerFactory(this);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
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
