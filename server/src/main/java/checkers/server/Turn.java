package checkers.server;

class Turn {
    private Player player;
    private Coordinates currMov;

    Turn(Player player) {
        this.player = player;
        currMov = null;
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

}
