package checkers.server;

import checkers.server.game.DefaultGamesMenager;
import checkers.server.game.GamesMenager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class Server {
    static Logger logger = LoggerFactory.getLogger(Server.class);

    private Server() throws RemoteException {
        GamesMenager gamesMenager = new DefaultGamesMenager();
    }

    public static void main(String [ ] args) throws InterruptedException, RemoteException {
        Server server = new Server();
        Thread.currentThread().join();

    }

}
