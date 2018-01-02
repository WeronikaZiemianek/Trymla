package checkers.client.consoleClient;

import checkers.core.Checker;
import checkers.core.Move;
import checkers.core.clientServerInterfaces.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegularClient extends UnicastRemoteObject implements Client {
    ConsoleClient client;

    public RegularClient(ConsoleClient client) throws RemoteException {
        this.client = client;
    }

    @Override
    public void update(boolean isMyTurn, Move lastMove) throws RemoteException {
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
