package checkers.server;
import checkers.core.PlayerFactory;
import checkers.core.RemotePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Connection {
    private Registry registry;
    private Logger logger;

    public Connection(PlayerFactory factory) {
        logger = LoggerFactory.getLogger(Connection.class);
        try {
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("factory", factory);
            logger.info("Factory bind");
        } catch(RemoteException e) {
            logger.error("Remote Exception");
        } catch(AlreadyBoundException e) {
            logger.error("Factory already bound");
        }

    }

    public void putPlayer(RemotePlayer player) {
        try {
            registry.bind(player.getLogin(), player);
        } catch(RemoteException e) {
            logger.error("Remote Exception");
        } catch(AlreadyBoundException e) {
            logger.error("Factory already bound");
        }
    }

}
