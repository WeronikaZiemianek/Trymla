package checkers.server;

import checkers.server.game.DefaultGamesManager;
import checkers.server.game.GamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class Server {
    static Logger logger = LoggerFactory.getLogger(Server.class);

    private Server() throws RemoteException {
        DefaultGamesManager.makeInstance();
        logger.info("GamesManager set up");
    }

    public static void main(String [ ] args) throws InterruptedException, RemoteException {
        logger.info("Starting server");
        Server server = new Server();
        Thread.currentThread().join();
    }

}
