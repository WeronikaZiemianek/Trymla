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
<<<<<<< HEAD
    void makeMove(Coordinates destination, Coordinates currLocation, Player player);
    RulesManager GetRulesManager();
=======
    boolean makeMove(Coordinates currLocation, Coordinates destination, Player player);
    Board getBoard();
>>>>>>> 3f206822c2343e24a98d89669fd6a3f46d91fccd

}

