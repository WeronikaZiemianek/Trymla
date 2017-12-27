package checkers.server;

import checkers.core.Checker;
import checkers.server.game.Game;

public interface Player {
    Checker getColor();
    void update(Boolean isMyTurn);
    void endGame(String login);
    void addNewPlayer(String login, Checker color);
    void setGame(Game game);
}
