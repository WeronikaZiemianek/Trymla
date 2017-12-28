package checkers.client;

import checkers.core.Checker;
import checkers.core.clientServerInterfaces.ClientPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegularClientPlayer extends UnicastRemoteObject implements ClientPlayer {
    ConsoleClient client;

    RegularClientPlayer(ConsoleClient client) throws RemoteException {
        this.client = client;
    }

    @Override
    public void update(boolean isMyTurn) throws RemoteException {
        client.drawBoard(isMyTurn);

    }

    @Override
    public void gameOver(String winnerLogin) throws RemoteException {
        client.endOfGame(winnerLogin);
    }

    @Override
    public void newPlayerAdded(String login, Checker color) throws RemoteException {
        client.newPlayer(login, color);

    }

    @Override
    public void replaceWithBot(String login, int index) throws RemoteException {
        client.renamePlayer(login, index);
    }
}
