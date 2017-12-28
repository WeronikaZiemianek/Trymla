package checkers.core.clientServerInterfaces;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;

import java.rmi.RemoteException;

public interface RemotePlayer extends java.rmi.Remote {

    Checker getColor() throws RemoteException;
    int makeMove(Coordinates location, Coordinates destination) throws RemoteException;
    String getLogin() throws RemoteException;
    void endMove() throws RemoteException;
    boolean createGame(int numOfPlayers) throws  RemoteException;
    boolean joinGame() throws RemoteException;
    void addBot() throws RemoteException;
    void getClientPlayer() throws RemoteException;
    Board getBoard() throws  RemoteException;
    void endJump() throws RemoteException;
}
