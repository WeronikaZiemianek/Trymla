package checkers.server;

import java.rmi.RemoteException;

public interface PlayerFactory extends java.rmi.Remote  {
    boolean createPlayer(String login) throws RemoteException;
}
