package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.server.Player;
import checkers.server.rules.RulesManager;

public interface Game { ;

    void startGame();
    Coordinates getCurrMov();
    void setCurrMov(Coordinates player);
    void endMove();
    void addPlayer(Player player);
    GameState getState();
    void disconnectPlayer(Player player);
    RulesManager GetRulesManager();
    int makeMove(Coordinates currLocation, Coordinates destination, Player player);
    Board getBoard();
    public int getNumOfPlayers();

}

