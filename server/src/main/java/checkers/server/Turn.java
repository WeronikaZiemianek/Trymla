package checkers.server;

class Turn {
    private Player turn;
    private Coordinates currMov;

    Turn(Player player) {
        turn = player;
        currMov = null;
    }

    Coordinates getCurrMov() {
        return currMov;
    }

    void setCurrMov(Coordinates currMov) {
        this.currMov = currMov;
    }

    Player getTurn() {
        return turn;
    }

}
