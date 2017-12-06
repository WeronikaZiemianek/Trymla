package checkers.core;

import checkers.core.boards.RegularBoard;

public class RulesManager implements RegularRulesManager{
    private Coordinates currMove;
    private Coordinates targetMove;
    private Boolean wasJump=false;
    private int xDifference;
    private int yDifference;
    private Boolean endMove;
    private RegularBoard board;

    RulesManager(RegularBoard board) {
        this.board = board;
    }

    @Override
    public Boolean checkChecker(PlayerAdapter Player, Checker checker){

        if(checker.equals(Player)){
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public Boolean checkMove(Coordinates destination, Coordinates currLocation, Checker checker){

        // czy w obręcie swojego domku
        if(checker.equals(board.getFieldType(currLocation)) && !(checker.equals(board.getFieldType(destination)))){
            return false;
        }

        xDifference=Math.abs(currLocation.x()-destination.x());
        yDifference=Math.abs(currLocation.y()-destination.y());

        // czy ruch dozwolony
        if((xDifference+yDifference==2)&&(wasJump=false)){
            //przesunięcie
            endMove=true;
            return true;
        }

        if(xDifference+yDifference==4){
            //skok
            wasJump=true;
            return true;
        }
        return false;
    }

    @Override
    public Boolean chceckEnd(){
        return endMove;
    }

    @Override
    public void endTurn(){
        wasJump=false;
    }

}
