package checkers.server;

public interface RulesManager {
    int checkMove(Coordinates destination, Coordinates currLocation, Checker checker);
}
