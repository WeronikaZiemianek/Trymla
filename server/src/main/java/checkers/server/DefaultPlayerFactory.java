package checkers.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;

public class DefaultPlayerFactory extends UnicastRemoteObject implements PlayerFactory {
    private GamesMenager menager;
    private ArrayList<DefaultPlayer> players;

    DefaultPlayerFactory(GamesMenager menager) throws RemoteException {
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
        DefaultPlayer player = new DefaultPlayer(menager, login);
        players.add(player);
        menager.addPlayer(player);
        return true;
    }
}
