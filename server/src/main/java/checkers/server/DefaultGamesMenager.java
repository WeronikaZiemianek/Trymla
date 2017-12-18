package checkers.server;

import java.rmi.RemoteException;

public class DefaultGamesMenager implements GamesMenager {
    private PlayerFactory factory;
    private Connection connection;

    DefaultGamesMenager() {
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

    //  @Override
  //  public void sayHello() throws RemoteException { System.out.println("Hello world");}



}
