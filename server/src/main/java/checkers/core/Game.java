package checkers.core;

import checkers.core.boards.RegularBoard;
import checkers.core.boards.WrongMoveException;
import checkers.core.fields.Field;

import java.util.HashMap;
import java.util.Map;

public class Game implements IGame {
    private RulesManager rulesManager;
    private RegularBoard board;
    private GamesManager gamesManager;
    private GameState state;
    private PlayerAdapter turn;
    private PlayerAdapter players[]=new PlayerAdapter[6];
    private int whoseTurn=0;

    Game(RegularBoard board) {
        this.board = board;
    }

    public void makeMove(Coordinates destionation, Coordinates currLocation, Checker set){

        if(rulesManager.checkChecker(turn, set)){
            if(rulesManager.checkMove(destionation, currLocation, set)){
                board.makeMove(currLocation, destionation);
            }
        }
    }

    public void wasMoveFinished(){
        if(rulesManager.endTurn()){
            endTurn();
        }
    }

    public void endTurn(){
        turn=players[whoseTurn];
        whoseTurn++;
        if(whoseTurn==6){
            whoseTurn=0;
        }
    }
}
