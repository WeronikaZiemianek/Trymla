package checkers.server;

import checkers.core.Checker;
import checkers.core.boards.Board;
import checkers.server.game.Game;

public interface Player {
    Checker getColor();
    void update(Boolean isMyTurn);
    void endGame(String login);
    void addNewPlayer(String login, Checker color);
    void setGameAndColor(Game game, Checker color, Board board);
    String getPlayerName();
}
