package checkers.core.boards;
import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.fields.Field;

public class RegularBoard implements Board {
    private final int NUM_OF_COLUMNS = 25;
    private final int NUM_OF_ROWS = 17;

    private Field[][] board;

    RegularBoard(Field[][] board) {
        this.board = board;

    }

    public void makeMove(Coordinates currLocation, Coordinates destination) throws WrongMoveException {
        if(board[currLocation.x()][currLocation.y()] == null || board[destination.x()][destination.y()] == null) {
            throw new WrongMoveException();
        }
        Checker curr = board[currLocation.x()][currLocation.y()].getOccupiedBy();
        Checker dest = board[destination.x()][destination.y()].getOccupiedBy();

        if(curr.equals(Checker.EMPTY) || !(dest.equals(Checker.EMPTY))) {
            throw new WrongMoveException();
        }
        board[destination.x()][destination.y()].setOccupiedBy(curr);
        board[currLocation.x()][currLocation.y()].setOccupiedBy(Checker.EMPTY);

    }

    public Checker getFieldType(Coordinates location) {
        return board[location.x()][location.y()].getType();
    }

    public Checker getFieldOccupiedBy(Coordinates location) {
        return board[location.x()][location.y()].getOccupiedBy();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int row=0; row<NUM_OF_ROWS; row++) {
            for(int column = 0; column < NUM_OF_COLUMNS; column++) {
                builder.append(getChar(column, row));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private char getChar(int column, int row) {
        if(board[column][row] == null) {
            return ' ';
        }
        switch(getFieldOccupiedBy(new Coordinates(column, row))) {
            case RED: return 'r';
            case BLUE: return 'b';
            case GREEN: return 'g';
            case BLACK: return 'n';
            case WHITE: return 'w';
            case YELLOW: return 'y';
            default: return '.';
        }
    }

}
