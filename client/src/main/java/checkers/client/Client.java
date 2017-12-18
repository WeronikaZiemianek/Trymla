package checkers.client;

import java.rmi.RemoteException;

public class Client {
    public static void main(String [ ] args) throws RemoteException {
        ClientConnection clientConnection = new ClientConnection();
        System.out.println(clientConnection.addPlayer("login"));
    }
}
