package checkers.server.game;

import checkers.core.Coordinates;
import checkers.server.Player;

public class Turn {
    private Player player;
    private Coordinates currMov;

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    private boolean canMove;

    public boolean getCanMove() {
        return canMove;
    }

    public Turn(Player player) {
        this.player = player;
        currMov = null;
        canMove = true;
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
