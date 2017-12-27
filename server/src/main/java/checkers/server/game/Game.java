package checkers.server.game;

import checkers.core.Coordinates;
import checkers.server.Player;
import checkers.core.boards.Board;

public interface Game { ;

    void startGame();
    Coordinates getCurrMov();
    void setCurrMov(Coordinates player);
    void endMove();
    void addPlayer(Player player);
    GameState getState();
    void disconnectPlayer(Player player);
    boolean makeMove(Coordinates currLocation, Coordinates destination, Player player);
    Board getBoard();

}

