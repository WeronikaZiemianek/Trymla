package checkers.client;

import checkers.server.Player;
import checkers.server.PlayerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClientConnection {
    static String login;
    static PlayerFactory playerFactory;
    static Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    ClientConnection() throws RemoteException {
        try {
            playerFactory = (PlayerFactory) Naming.lookup("factory");
        }
        catch( Exception e ) {
            logger.error("Can't find factory in registry");
        }
    }

    boolean addPlayer(String login) throws RemoteException {
        ClientConnection.login = login;
        return playerFactory.createPlayer(login);
    }

    Player getPlayer() throws RemoteException {
        try {
            Player player = (Player) Naming.lookup(login);
            return player;
        }
        catch( Exception e ) {
            logger.error("Can't find player in registry");
        }
        return null;
    }
}
