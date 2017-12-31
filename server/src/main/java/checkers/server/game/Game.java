package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.Move;
import checkers.core.boards.Board;
import checkers.server.Player;
import checkers.server.rules.RulesManager;

public interface Game { ;
    void startGame();
    Coordinates getCurrMov();
    void endMove(Move lastMove, Player player);
    boolean addPlayer(Player player);
    GameState getState();
    void disconnectPlayer(Player player);
    RulesManager GetRulesManager();
    int makeMove(Coordinates currLocation, Coordinates destination, Player player);
    Board getBoard();
    int getNumOfPlayers();
    void updatePlayers(Move lastMove);
    boolean canMove();
    void endJump(Move lastMove, Player player);
    void increaseNumOfBots();
}

