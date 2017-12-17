package checkers.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    static Logger logger = LoggerFactory.getLogger(Server.class);

    private Server() throws RemoteException {
        GamesMenager gamesMenager = new DefaultGamesMenager();
        Connection connection = new Connection(gamesMenager);
    }

    public static void main(String [ ] args) throws InterruptedException, RemoteException {
        Server server = new Server();
        Thread.currentThread().join();

    }

}
