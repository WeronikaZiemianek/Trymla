package checkers.client.consoleClient;

import checkers.client.consoleClient.ConsoleClient;

import java.rmi.RemoteException;

public class Client {
    public static void main(String [ ] args) throws RemoteException {
        ConsoleClient consoleClient = new ConsoleClient();
    }
}
