package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.Player;

public class Turn {
    private Player player;
    private Coordinates currMov;

    public Turn(Player player) {
        this.player = player;
        currMov = null;
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

}
