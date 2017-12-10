package checkers.server.boards;

import checkers.server.Checker;
import checkers.server.Coordinates;

public interface Board {
    Checker getFieldOccupiedBy(Coordinates location);
    Checker getFieldType(Coordinates location);
    void makeMove(Coordinates currLocation, Coordinates destination) throws WrongMoveException;

}
