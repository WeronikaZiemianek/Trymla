package checkers.core;

import checkers.core.fields.Field;

public interface RegularRulesManager {
    Boolean checkMove(Coordinates destionation, Coordinates currLocation, Checker checker);
    Boolean checkChecker(PlayerAdapter Player, Checker checker);
    Boolean endTurn();
    Boolean chceckEnd();
}
