package checkers.client;

import checkers.core.boards.Board;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;

import java.rmi.RemoteException;

public class Logic {
    RemotePlayer player;
    ClientConnection connection;
    ClientPlayer clientPlayer;

    Logic() throws RemoteException {
            connection = new ClientConnection();
    }

    boolean createNewPlayer(String login) {
        if(connection.addPlayer(login)) {
            player = connection.getPlayer();
            try {
                clientPlayer = new RegularClientPlayer(this);
                connection.addClientPlayer(clientPlayer);
                player.getClientPlayer();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
            System.out.print(clientPlayer);
            return true;
        } else {
            return false;
        }
    }
}
