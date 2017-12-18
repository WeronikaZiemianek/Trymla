package checkers.server;

import checkers.server.boards.Board;

import java.rmi.RemoteException;

public interface Player extends java.rmi.Remote {
    boolean isMyTurn = false;
    Board board = null;
    Checker color = Checker.EMPTY;

    Checker getColor() throws RemoteException;
    void update(Boolean isMyTurn) throws RemoteException;
    void makeMove(Coordinates location, Coordinates destination) throws RemoteException;
    String getLogin() throws RemoteException;
}
