package checkers.server;

public interface Player {
    Checker getColor();
    void update(Boolean isMyTurn);
}
