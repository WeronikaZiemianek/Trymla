package checkers.core.clientServerInterfaces;

import checkers.core.Checker;

import java.rmi.RemoteException;

public interface ClientPlayer extends java.rmi.Remote {
    void update(boolean isMyTurn) throws RemoteException;
    void gameOver(String winnerLogin) throws RemoteException;
    void newPlayerAdded(String login, Checker color) throws RemoteException;
    void replaceWithBot(String login, int index) throws RemoteException;
}
