package checkers.server.player;

import checkers.core.Checker;
import checkers.core.clientServerInterfaces.PlayerFactory;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.server.game.GamesManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;

public class DefaultPlayerFactory extends UnicastRemoteObject implements PlayerFactory {
    private GamesManager manager;
    private ArrayList<DefaultPlayer> players;

    public DefaultPlayerFactory(GamesManager manager) throws RemoteException {
        players = new ArrayList<>();
        this.manager = manager;
    }

    @Override
    synchronized public boolean createPlayer(String login) throws RemoteException {
        for(RemotePlayer p : players) {
            if(Objects.equals(login, p.getLogin())) {
                return false;
            }
        }
        DefaultPlayer player = new DefaultPlayer(manager, login, Checker.RED);
        players.add(player);
        manager.addPlayer(player);
        return true;
    }
}
