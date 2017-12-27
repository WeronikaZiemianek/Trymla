package checkers.client;

import java.rmi.RemoteException;

public class Client {
    public static void main(String [ ] args) throws RemoteException {
        Logic logic = new Logic();
        logic.createNewPlayer("login");
    }
}
