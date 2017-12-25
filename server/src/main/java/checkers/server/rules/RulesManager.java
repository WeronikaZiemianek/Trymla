package checkers.server.rules;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.server.game.Game;

public interface RulesManager {
    boolean checkMove(Game game, Coordinates destination, Coordinates currLocation, Checker checker);
}
