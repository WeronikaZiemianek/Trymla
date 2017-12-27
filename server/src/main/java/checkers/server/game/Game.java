package checkers.server.game;

import checkers.core.Coordinates;
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
    void makeMove(Coordinates destination, Coordinates currLocation, Player player);
    RulesManager GetRulesManager();

}

