package checkers.core.boards;

import checkers.core.Checker;
import checkers.core.Coordinates;

public interface Board {
    int getNumOfCol();
    int getNumOfRows();
    Checker getFieldOccupiedBy(Coordinates location);
    Checker getFieldType(Coordinates location);
    void makeMove(Coordinates currLocation, Coordinates destination) throws WrongMoveException;
    int getExNumOfPlayers();
    Checker colorForPlayer(int num);

}
