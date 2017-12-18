package checkers.client;
import checkers.server.Player;

import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class ConnectionTest {

    ClientConnection clientConnection;

    //Start server before running this test

    @Before
    public void makeConnection() throws RemoteException {
        clientConnection = new ClientConnection();
    }

    @Test
    public void testCreatePlayer() throws RemoteException {
        assertTrue(clientConnection.addPlayer("login"));
    }

    @Test
    public void testCreatePlayerLogin() throws RemoteException {
        clientConnection.addPlayer("login");
        Player player = clientConnection.getPlayer();
        assertEquals("login", player.getLogin());
    }
}