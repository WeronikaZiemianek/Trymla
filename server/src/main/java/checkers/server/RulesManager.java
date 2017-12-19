package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;

public interface RulesManager {
    int checkMove(Coordinates destination, Coordinates currLocation, Checker checker);
}
