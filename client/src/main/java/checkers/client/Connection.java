package checkers.client;

import checkers.server.GamesMenager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Connection {
    static Logger logger = LoggerFactory.getLogger(Connection.class);
    public static void rmi() throws RemoteException {
        try {
            GamesMenager gamesMenager = (GamesMenager) Naming.lookup("menager");
            gamesMenager.sayHello();
        }
        catch( Exception e ) {
            logger.error("Can't find games menager in registry");
        }
    }
}
