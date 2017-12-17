package checkers.server;

class Turn {
    private Player player;
    private Coordinates currMov;
    private boolean jumped;

    Turn(Player player) {
        this.player = player;
        currMov = null;
        jumped = false;
    }
    Coordinates getCurrMov() {
        return currMov;
    }

    void setCurrMov(Coordinates currMov) {
        this.currMov = currMov;
    }

    Player getPlayer() {
        return this.player;
    }

    void playerJumped(){ this.jumped = true;}

    void jumpReset(){ this.jumped = false;}

    boolean getJumped(){return jumped;}

}
