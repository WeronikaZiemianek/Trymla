package checkers.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultGamesMenager extends UnicastRemoteObject implements GamesMenager {

    protected DefaultGamesMenager() throws RemoteException {
    }

    @Override
    public void sayHello() throws RemoteException { System.out.println("Hello world");}
}
