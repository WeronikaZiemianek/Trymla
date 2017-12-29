package checkers.server;
import checkers.core.clientServerInterfaces.Client;
import checkers.core.clientServerInterfaces.PlayerFactory;
import checkers.core.clientServerInterfaces.RemotePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Connection {
    private Registry registry;
    private Logger logger;

    public Connection(PlayerFactory factory) {
        logger = LoggerFactory.getLogger(Connection.class);
      //  System.setProperty("java.rmi.server.hostname", "localhost");
        try {
            logger.debug("Setting up player factory");
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
            logger.debug("Remote player " + player.getLogin() + " registered");
        } catch(RemoteException e) {
            logger.error("Remote Exception");
        } catch(AlreadyBoundException e) {
            logger.error("Factory already bound");
        }
    }

    public Client getClientPlayer(Player player) {
        try {
            return (Client) registry.lookup("CLIENT".concat(player.getPlayerName()));
        } catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unbindPlayer(RemotePlayer player) {
        try {
            registry.unbind(player.getLogin());
            UnicastRemoteObject.unexportObject(player, true);
            logger.info("client unbound");
        } catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

}
