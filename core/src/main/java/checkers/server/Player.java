package checkers.server;

import checkers.server.boards.Board;

public interface Player {

    Checker getColor();
    void update(Boolean isMyTurn);
    Coordinates[] makeMove();
}
