package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.Player;

public class Turn {
    private Player player;
    private Coordinates currMov;
    private boolean jumped;

    public Turn(Player player) {
        this.player = player;
        currMov = null;
        jumped = false;
    }
    public Coordinates getCurrMov() {
        return currMov;
    }

    public void setCurrMov(Coordinates currMov) {
        this.currMov = currMov;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void playerJumped(){ this.jumped = true;}

    public void jumpReset(){ this.jumped = false;}

    public boolean getJumped(){return jumped;}

}
