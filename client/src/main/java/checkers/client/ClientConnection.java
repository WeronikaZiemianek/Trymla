package checkers.client;

import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.clientServerInterfaces.PlayerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClientConnection {
    static String login;
    static PlayerFactory playerFactory;
    static Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    ClientConnection() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", "localhost");
        try {
            logger.debug("Looking up for remote player factory");
            playerFactory = (PlayerFactory) Naming.lookup("//localhost:1099/factory");
            logger.debug("Remote player factory found");
        }
        catch( Exception e ) {
            logger.error("Can't find factory in registry");
        }
    }

    boolean addPlayer(String login) throws RemoteException {
        ClientConnection.login = login;
        return playerFactory.createPlayer(login);
    }

    RemotePlayer getPlayer() throws RemoteException {
        try {
            RemotePlayer player = (RemotePlayer) Naming.lookup("//localhost:1099/".concat(login));
            return player;
        }
        catch( Exception e ) {
            logger.error("Can't find player in registry");
        }
        return null;
    }
}
