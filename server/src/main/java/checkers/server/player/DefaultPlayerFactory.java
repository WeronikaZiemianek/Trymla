package checkers.server.player;

import checkers.core.Checker;
import checkers.core.PlayerFactory;
import checkers.core.RemotePlayer;
import checkers.server.game.GamesManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;

public class DefaultPlayerFactory extends UnicastRemoteObject implements PlayerFactory {
    private GamesManager menager;
    private ArrayList<DefaultPlayer> players;

    public DefaultPlayerFactory(GamesManager menager) throws RemoteException {
        players = new ArrayList<>();
        this.menager = menager;
    }

    @Override
    synchronized public boolean createPlayer(String login) throws RemoteException {
        for(RemotePlayer p : players) {
            if(Objects.equals(login, p.getLogin())) {
                return false;
            }
        }
        DefaultPlayer player = new DefaultPlayer(menager, login, Checker.RED);
        players.add(player);
        menager.addPlayer(player);
        return true;
    }
}
