package checkers.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Connection {
    Connection(GamesMenager gamesMenager) {
        Logger logger = LoggerFactory.getLogger(Connection.class);
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("menager", gamesMenager);
        } catch(RemoteException e) {
            logger.error("Can't create registry");
        } catch(AlreadyBoundException e) {
            logger.error("Menager already bound");
        }

    }

}
