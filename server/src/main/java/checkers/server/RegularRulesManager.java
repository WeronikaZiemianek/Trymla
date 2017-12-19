package checkers.server;

import checkers.core.Checker;
import checkers.core.Coordinates;

public class RegularRulesManager implements RulesManager {
    private Game game;

    RegularRulesManager(Game game) {
        this.game = game;
    }

    private Boolean checkChecker(Checker checker, Coordinates currLocation){
        Checker color = game.getOccupiedByType(currLocation);
        return (color.equals(checker));
    }
    private Boolean checkIfEscapesFromTriangle(Coordinates destination, Coordinates currLocation, Checker checker) {
        return (game.getFieldType(currLocation) == checker && game.getFieldType(destination) != checker);
    }

    @Override
    public int checkMove(Coordinates destination, Coordinates currLocation, Checker checker){

        if(!checkChecker(checker, currLocation)) {
            return  0;
        }

        if(checkIfEscapesFromTriangle(destination, currLocation, checker)) {
            return 0;
        }

        int diff = Math.abs(currLocation.Y()-destination.Y()) + Math.abs(currLocation.X()-destination.X());


            if(diff == 2) {
                return checkWin()? 3 : 1;
            }
            else if(diff == 4)
            {
                game.getTurn().playerJumped();
                return checkWin()? 3 : 2;
            }

            game.getTurn().jumpReset();
            return 0;
    }

    private boolean checkWin(){


        return false;
    }

}
