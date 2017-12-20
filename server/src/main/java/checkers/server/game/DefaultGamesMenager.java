package checkers.server.game;

import checkers.core.PlayerFactory;
import checkers.core.RemotePlayer;
import checkers.server.player.DefaultPlayerFactory;

import java.rmi.RemoteException;
import java.sql.Connection;

public class DefaultGamesMenager implements GamesMenager{
    private PlayerFactory factory;
    private Connection connection;

    public DefaultGamesMenager(){
        try {
            factory = new DefaultPlayerFactory(this);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        connection = new Connection(factory);
    }

    @Override
    public void addPlayer(RemotePlayer player) {
        connection.putPlayer(player);
    }

    // @Override
    // public void sayHe;;p() throws RemoteException { System.out.println("Hello world");}
}
