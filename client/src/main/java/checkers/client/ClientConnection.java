package checkers.client;

import checkers.core.clientServerInterfaces.Client;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.clientServerInterfaces.PlayerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientConnection {
    String login;
    PlayerFactory playerFactory;
    Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    Registry registry;
    Client client;

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
        this.login = login;
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

    public void addClientPlayer(Client client) {
        this.client = client;
        try {
            registry =  LocateRegistry.getRegistry(1099);
            registry.bind("CLIENT".concat(login), client);
            logger.info("CLIENT" + login, client + "  bound");
        } catch(Exception e) {
            logger.error("cant find registry and create there a client player");
        }
    }

    public void deleteClientPlayerFromRegistry() {
        try {
            registry.unbind("CLIENT".concat(login));
            UnicastRemoteObject.unexportObject(client, true);
        } catch(Exception e) {
            logger.error("cant unbind client player");
        }
    }
}
