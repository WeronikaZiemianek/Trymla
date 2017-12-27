package checkers.core.clientServerInterfaces;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;

import java.rmi.RemoteException;

public interface RemotePlayer extends java.rmi.Remote {
    boolean isMyTurn = false;
    Board board = null;
    Checker color = Checker.EMPTY;

    Checker getColor() throws RemoteException;
    void makeMove(Coordinates location, Coordinates destination) throws RemoteException;
    String getLogin() throws RemoteException;
    void endMove() throws RemoteException;
    void createGame(int numOfPlayers) throws  RemoteException;
    boolean joinGame() throws RemoteException;
    void addBot() throws RemoteException;
}
