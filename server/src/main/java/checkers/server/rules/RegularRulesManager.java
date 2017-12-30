package checkers.server.rules;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegularRulesManager implements RulesManager {
    private Board board;
    private Logger logger;

    public RegularRulesManager(Board board) {
        this.board = board;
        logger = LoggerFactory.getLogger(RegularRulesManager.class);
    }

    private Boolean checkChecker(Checker checker, Coordinates currLocation){
        Checker color = board.getFieldOccupiedBy(currLocation);
        return (color.equals(checker));
    }
    private Boolean checkIfEscapesFromTriangle(Coordinates currLocation, Coordinates destination, Checker checker) {
        return (board.getFieldType(currLocation) == checker && board.getFieldType(destination) != checker);
    }

    @Override
    public int checkMove(Game game, Coordinates currLocation, Coordinates destination, Checker checker){

        if(!checkChecker(checker, currLocation)) {
            return  -1;
        }

        if(checkIfEscapesFromTriangle(currLocation, destination, checker)) {
            return -1;
        }

        if(game.getCurrMov() != null) {
            if(!(game.getCurrMov().equals(currLocation))) {
                return -1;
            }
        }

        if(!game.canMove()) {
            return -1;
        }


        Checker curr = board.getFieldOccupiedBy(currLocation);
        Checker dest = board.getFieldOccupiedBy(destination);

        if(curr.equals(Checker.EMPTY) || !(dest.equals(Checker.EMPTY)))
            return -1;

        int yDiff = Math.abs(currLocation.Y()-destination.Y());
        int xDiff = Math.abs(currLocation.X()-destination.X());
        if(xDiff == 3 || yDiff == 3) {
            return -1;
        }
        int diff = yDiff + xDiff;

        if(diff == 2) {
            if(game.getCurrMov() == null) {
                if(yDiff == 2) {
                    return -1;
                }
                return 2;
            } else {
                return -1;
            }
        }
        else if(diff == 4)
        {
            if(yDiff == 4) {
                return -1;
            }
            Coordinates between = new Coordinates((currLocation.X() + destination.X())/2,(currLocation.Y()+destination.Y())/2);
            logger.debug(between.X() + " " + between.Y());
            if(board.getFieldOccupiedBy(between) != Checker.EMPTY) {
                return 4;
            } else {
                return -1;
            }
        }
        return -1;
    }


}
