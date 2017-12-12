package checkers.server;

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
    public Boolean checkMove(Coordinates destination, Coordinates currLocation, Checker checker){

        if(!checkChecker(checker, currLocation)) {
            return false;
        }

        if(checkIfEscapesFromTriangle(destination, currLocation, checker)) {
            return false;
        }

        int diff = Math.abs(currLocation.Y()-destination.Y()) + Math.abs(currLocation.X()-destination.X());


            if(diff == 2) {
                return true;
            }
            else if(diff == 4)
            {
                game.getTurn().playerJumped();
                return true;
            }

            game.getTurn().jumpReset();
            return false;


    }

}
