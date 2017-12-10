package checkers.server;

import checkers.server.boards.Board;

public class DefaultPlayer implements Player {

    private boolean isMyTurn;
    private Board board;
    private Checker color;

    public DefaultPlayer(Board board, Checker color){
        this.board = board;
        this.color = color;
    }

    public Checker getColor(){
        return this.color;
    }


    @Override
    public void update(Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    @Override
    public Coordinates[] makeMove() {
        return new Coordinates[2];
    }


}
