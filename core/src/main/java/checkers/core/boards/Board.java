package checkers.core.boards;

import checkers.core.Checker;
import checkers.core.Coordinates;

public interface Board {
    Checker getFieldOccupiedBy(Coordinates location);
    Checker getFieldType(Coordinates location);
    void makeMove(Coordinates currLocation, Coordinates destination) throws WrongMoveException;

}
