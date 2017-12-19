package checkers.server;

import checkers.core.Coordinates;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class TurnTest {

    @Test
    public void testGetCurrentMove() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer(null, "login");
        Turn turn = new Turn(player);
        assertEquals(null, turn.getCurrMov());
    }

    @Test
    public void testSetCurrentMove() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer(null, "login");
        Turn turn = new Turn(player);
        turn.setCurrMov(new Coordinates(6,6));
        assertEquals((new Coordinates(6,6)).X(), turn.getCurrMov().X());
        assertEquals((new Coordinates(6,6)).Y(), turn.getCurrMov().Y());
    }

    @Test
    public void testGetPlayer() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer(null, "login");
        Turn turn = new Turn(player);
        turn.setCurrMov(new Coordinates(12,0));
        assertEquals(new Coordinates(12,0).X(), turn.getCurrMov().X());
        assertEquals(new Coordinates(12,0).Y(), turn.getCurrMov().Y());
    }

    @Test
    public void testPlayerJumped() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer(null, "login");
        Turn turn = new Turn(player);
        turn.playerJumped();
        assertEquals(true, turn.getJumped());
    }

    @Test
    public void testJumpReset() throws RemoteException {
        DefaultPlayer player = new DefaultPlayer(null, "login");
        Turn turn = new Turn(player);
        turn.playerJumped();
        assertEquals(true, turn.getJumped());
        turn.jumpReset();
        assertEquals(false, turn.getJumped());
    }
}
