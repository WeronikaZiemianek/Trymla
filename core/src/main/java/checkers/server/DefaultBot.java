package checkers.server;

import checkers.server.boards.Board;

public class DefaultBot {

    private boolean isMyTurn;
    private Board board;
    private Checker color;

    public DefaultBot(Board board, Checker color){
        this.board = board;
        this.color = color;
    }

    public Checker getColor(){
        return this.color;
    }


    public void update(Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public Coordinates[] makeMove() {
        return new Coordinates[2];
    }


}
