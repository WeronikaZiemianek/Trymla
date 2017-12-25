package checkers.server.game;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Player;

public interface Game { ;
    Coordinates getCurrMov();
    void setCurrMov(Coordinates player);
    Player getTurn();
    void endMove();
    void addPlayer(Player player);

}

