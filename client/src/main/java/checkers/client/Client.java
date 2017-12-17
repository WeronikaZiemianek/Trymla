package checkers.client;

import java.rmi.RemoteException;

public class Client {
    public static void main(String [ ] args) throws RemoteException {
        Connection connection = new Connection();
        connection.rmi();
    }
}
