package checkers.server.rules;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.server.game.Game;

public class RegularRulesManager implements RulesManager {
    private Board board;

    public RegularRulesManager(Board board) {
        this.board = board;
    }

    private Boolean checkChecker(Checker checker, Coordinates currLocation){
        Checker color = board.getFieldOccupiedBy(currLocation);
        return (color.equals(checker));
    }
    private Boolean checkIfEscapesFromTriangle(Coordinates destination, Coordinates currLocation, Checker checker) {
        return (board.getFieldType(currLocation) == checker && board.getFieldType(destination) != checker);
    }

    @Override
    public boolean checkMove(Game game,Coordinates destination, Coordinates currLocation, Checker checker){

        if(!checkChecker(checker, currLocation)) {
            return  false;
        }

        if(checkIfEscapesFromTriangle(destination, currLocation, checker)) {
            return false;
        }

        if(game.getCurrMov() != null && game.getCurrMov() != currLocation) {
            return false;
        }

        int diff = Math.abs(currLocation.Y()-destination.Y()) + Math.abs(currLocation.X()-destination.X());

        if(diff == 2) {
            if(game.getCurrMov() == null) {
                game.endMove();
                return true;
            } else {
                return false;
            }
        }
        else if(diff == 4)
        {
            return true;
        }
        return false;
    }


}
