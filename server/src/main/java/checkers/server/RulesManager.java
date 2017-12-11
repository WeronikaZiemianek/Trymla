package checkers.server;

public interface RulesManager {
    Boolean checkMove(Coordinates destination, Coordinates currLocation, Checker checker);
}
