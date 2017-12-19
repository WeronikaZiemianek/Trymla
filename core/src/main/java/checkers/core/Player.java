package checkers.core;

public interface Player {
    Checker getColor();
    void update(Boolean isMyTurn);
}
