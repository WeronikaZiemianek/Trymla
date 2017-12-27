package checkers.client;

import checkers.core.Checker;
import checkers.core.clientServerInterfaces.ClientPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegularClientPlayer extends UnicastRemoteObject implements ClientPlayer {
    Logic logic;

    RegularClientPlayer(Logic logic) throws RemoteException {
        this.logic = logic;
    }

    @Override
    public void update(boolean isMyTurn) throws RemoteException {

    }

    @Override
    public void gameOver(String winnerLogin) throws RemoteException {

    }

    @Override
    public void newPlayerAdded(String login, Checker color) throws RemoteException {

    }
}
