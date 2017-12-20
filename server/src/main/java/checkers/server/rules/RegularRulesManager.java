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

    private Boolean checkChecker(Game game,Checker checker, Coordinates currLocation){
        Checker color = game.getOccupiedByType(currLocation);
        return (color.equals(checker));
    }
    private Boolean checkIfEscapesFromTriangle(Game game,Coordinates destination, Coordinates currLocation, Checker checker) {
        return (game.getFieldType(currLocation) == checker && game.getFieldType(destination) != checker);
    }

    @Override
    public int checkMove(Game game,Coordinates destination, Coordinates currLocation, Checker checker){

        if(!checkChecker(game, checker, currLocation)) {
            return  0;
        }

        if(checkIfEscapesFromTriangle(game, destination, currLocation, checker)) {
            return 0;
        }

        int diff = Math.abs(currLocation.Y()-destination.Y()) + Math.abs(currLocation.X()-destination.X());


            if(diff == 2) {
                return 1;
            }
            else if(diff == 4)
            {

                game.getTurn().playerJumped();
                System.out.print("4");
                return 1;
            }

            game.getTurn().jumpReset();
            return 0;
    }

    private boolean checkWin(){


        return false;
    }

}
