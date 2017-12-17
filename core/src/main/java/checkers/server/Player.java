package checkers.server;

import checkers.server.boards.Board;

public interface Player extends java.rmi.Remote {
    boolean isMyTurn = false;
    Board board = null;
    Checker color = Checker.EMPTY;

    Checker getColor();
    void update(Boolean isMyTurn);
    void makeMove(Coordinates location, Coordinates destination);
}
