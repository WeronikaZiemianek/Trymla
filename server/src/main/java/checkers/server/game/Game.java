package checkers.server.game;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Player;

public interface Game {
    Checker getOccupiedByType(Coordinates location);
    Checker getFieldType(Coordinates location);
    Coordinates getCurrMov();
    Turn getTurn();
    void endMove();
    void addPlayer(Player player);

}

