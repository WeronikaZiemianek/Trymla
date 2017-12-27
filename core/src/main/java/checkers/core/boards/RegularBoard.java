package checkers.core.boards;
import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Field;

public class RegularBoard implements Board {
    private final int NUM_OF_COLUMNS;
    private final int NUM_OF_ROWS;

    private Field[][] board;
    private int numOfPlayers;

    RegularBoard(Field[][] board, int numOfPlayers) {
        this.board = board;
        this.numOfPlayers = numOfPlayers;
        NUM_OF_COLUMNS =board.length;
        if(NUM_OF_COLUMNS != 0) {
            NUM_OF_ROWS = board[0].length;
        } else {
            NUM_OF_ROWS = 0;
        }

    }
    @Override
    public void makeMove(Coordinates destination, Coordinates currLocation) throws WrongMoveException {
        if(board[currLocation.X()][currLocation.Y()] == null || board[destination.X()][destination.Y()] == null) {
            throw new WrongMoveException();
        }
        Checker curr = board[currLocation.X()][currLocation.Y()].getOccupiedBy();
        Checker dest = board[destination.X()][destination.Y()].getOccupiedBy();

        if(curr.equals(Checker.EMPTY) || !(dest.equals(Checker.EMPTY))) {
            throw new WrongMoveException();
        }
        board[destination.X()][destination.Y()].setOccupiedBy(curr);
        board[currLocation.X()][currLocation.Y()].setOccupiedBy(dest);

    }

    @Override
    public int getExNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public Checker getFieldType(Coordinates location) {
        return board[location.X()][location.Y()].getType();
    }

    @Override
    public Checker getFieldOccupiedBy(Coordinates location) {
        return board[location.X()][location.Y()].getOccupiedBy();
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
