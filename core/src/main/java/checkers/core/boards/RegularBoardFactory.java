package checkers.core.boards;

import checkers.core.Checker;
import checkers.core.Field;

public class RegularBoardFactory implements BoardFactory {
    //0-type, 1-column, 2-row, 3-limit
    private final int[][] TRIANGLE_INFO = {{12,0,4}, {21,7,3}, {21,9,13}, {12,16,12}, {3,9,13}, {3,7,3}};

    private Field[][] board;
    
    public RegularBoard createNewBoard(int numOfSets) throws WrongNumberOfSetsException {
        final int NUM_OF_COLUMNS = 25;
        final int NUM_OF_ROWS = 17;
        board = new Field[NUM_OF_COLUMNS][NUM_OF_ROWS];
        makeEmptyBoard();
        switch(numOfSets) {
            case 6: createUprightTriangle(TRIANGLE_INFO[2],Checker.YELLOW);
                createUpsideDownTriangle(TRIANGLE_INFO[5],Checker.BLUE);
            case 4: createUprightTriangle(TRIANGLE_INFO[4], Checker.BLACK);
                createUpsideDownTriangle(TRIANGLE_INFO[1], Checker.WHITE);
            case 2: createUprightTriangle(TRIANGLE_INFO[0],Checker.RED);
                createUpsideDownTriangle(TRIANGLE_INFO[3], Checker.GREEN);
                break;
            case 3:
                createUprightTriangle(TRIANGLE_INFO[0],Checker.RED, Checker.EMPTY);
                createUpsideDownTriangle(TRIANGLE_INFO[1], Checker.WHITE);
                createUprightTriangle(TRIANGLE_INFO[2],Checker.YELLOW, Checker.EMPTY);
                createUpsideDownTriangle(TRIANGLE_INFO[3], Checker.GREEN);
                createUprightTriangle(TRIANGLE_INFO[4], Checker.BLACK, Checker.EMPTY);
                createUpsideDownTriangle(TRIANGLE_INFO[5], Checker.BLUE);
                break;
            default: throw new WrongNumberOfSetsException();

        }
        return new RegularBoard(board, numOfSets);
    }

    private void makeEmptyBoard() {
        createCentralFields();
        createUprightTriangle(TRIANGLE_INFO[0], Checker.EMPTY, Checker.OTHER);
        createUpsideDownTriangle(TRIANGLE_INFO[1], Checker.EMPTY, Checker.OTHER);
        createUprightTriangle(TRIANGLE_INFO[2], Checker.EMPTY, Checker.OTHER);
        createUpsideDownTriangle(TRIANGLE_INFO[3], Checker.EMPTY, Checker.OTHER);
        createUprightTriangle(TRIANGLE_INFO[4], Checker.EMPTY, Checker.OTHER);
        createUpsideDownTriangle(TRIANGLE_INFO[5], Checker.EMPTY, Checker.OTHER);

    }

    private void createCentralFields(){

        for(int column=4;column<21;column++) {
            for (int row = 4; row < 13; row++){
                if((row+column)%2==0){
                    board[column][row] = new Field(Checker.EMPTY, null);
                }
            }
        }
    }

    private void createUprightTriangle(int[] triangleInfo, Checker type){
        createUprightTriangle(triangleInfo, type, null);
    }

    private void createUprightTriangle(int[] triangleInfo, Checker type, Checker occupied){
        int column = triangleInfo[0];
        int row = triangleInfo[1];
        int limit = triangleInfo[2];

        int counter=1,numberOfJumps;

        while(row<limit){
            for(numberOfJumps=0;numberOfJumps<counter;numberOfJumps++)
                if(occupied == null)
                    board[column + numberOfJumps * 2][row] = new Field(type, occupied);

            row++;
            column--;
            counter++;
        }
    }
    private void createUpsideDownTriangle(int[] triangleInfo, Checker type) {
        createUpsideDownTriangle(triangleInfo, type, null);
    }

    private void createUpsideDownTriangle(int[] triangleInfo, Checker type, Checker occupied){
        int column = triangleInfo[0];
        int row = triangleInfo[1];
        int limit = triangleInfo[2];
        int counter=1,numberOfJumps;

        while(row>limit){
            for(numberOfJumps=0;numberOfJumps<counter;numberOfJumps++)
                board[column+numberOfJumps*2][row] = new Field(type, occupied);

            row--;
            column--;
            counter++;
        }
    }
}
