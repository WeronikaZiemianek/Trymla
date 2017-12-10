package checkers.server;

public interface RulesManager {
    public Boolean checkMove(Coordinates destination, Coordinates currLocation, Checker checker);
}
