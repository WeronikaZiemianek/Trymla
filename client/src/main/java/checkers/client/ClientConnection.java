package checkers.client;

import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.clientServerInterfaces.PlayerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientConnection {
    static String login;
    static PlayerFactory playerFactory;
    static Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    Registry registry;

    public ClientConnection() throws RemoteException {
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

    public boolean addPlayer(String login) {
        ClientConnection.login = login;
        try {
            return playerFactory.createPlayer(login);
        } catch(RemoteException e) {
            logger.error("cant crate player in factory");
        }
        return false;
    }

    public RemotePlayer getPlayer() {
        try {
            RemotePlayer player = (RemotePlayer) Naming.lookup("//localhost:1099/".concat(login));
            return player;
        }
        catch( Exception e ) {
            logger.error("Can't find player in registry");
        }
        return null;
    }

    public void addClientPlayer(ClientPlayer clientPlayer) {
        try {
            registry =  LocateRegistry.getRegistry(1099);
            registry.bind("CLIENT".concat(login), clientPlayer);
            logger.info("CLIENT" + login, clientPlayer + "  bound");
        } catch(Exception e) {
            logger.error("cant find registry and create there a client player");
        }
    }

    void deleteClientPlayerFromRegistry() {
        try {
            registry.unbind("CLIENT".concat(login));
        } catch(Exception e) {
            logger.error("cant unbind client player");
        }
    }
}
