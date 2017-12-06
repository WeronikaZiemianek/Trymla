package checkers.core;

import checkers.core.boards.RegularBoard;
import checkers.core.boards.WrongMoveException;
import checkers.core.fields.Field;

public class Game implements IGame {
    private RulesManager rulesManager;
    private RegularBoard board;
    private GamesManager gamesManager;
    private GameState state;
    private PlayerAdapter turn;
    private RegularBoard board;

    Game(RegularBoard board) {
        this.board = board;
    }

    public endTurn(){

    }
}
