package checkers.server;

import checkers.server.Checker;
import checkers.server.Coordinates;

public interface Game {
    Checker getOccupiedByType(Coordinates location);
    Checker getFieldType(Coordinates location);
    Coordinates getCurrMov();
    Turn getTrun();
    void endMove();

}

