package checkers.core.boards;

import checkers.core.Checker;
import checkers.core.fields.Field;

public class RegularBoardFactory implements BoardFactory {
    //0-type, 1-column, 2-row, 3-limit
    private final int[][] trianglesInfo = {{12,0,4}, {21,7,3}, {21,9,13}, {12,16,12}, {3,9,13}, {3,7,3}};
    
    private Field[][] board;
    
    public Board createNewBoard(int numOfSets) throws WrongNumberOfSetsException {
        final int NUM_OF_COLUMNS = 25;
        final int NUM_OF_ROWS = 17;
        board = new Field[NUM_OF_COLUMNS][NUM_OF_ROWS];
        makeEmptyBoard();
        switch(numOfSets) {
            case 6: createUprightTriangle(trianglesInfo[2],Checker.YELLOW);
                createUpsideDownTriangle(trianglesInfo[5],Checker.BLUE);
            case 4: createUprightTriangle(trianglesInfo[4], Checker.BLACK);
                createUpsideDownTriangle(trianglesInfo[1], Checker.WHITE);
            case 2: createUprightTriangle(trianglesInfo[0],Checker.RED);
                createUpsideDownTriangle(trianglesInfo[3], Checker.GREEN);
                break;
            case 3: createUpsideDownTriangle(trianglesInfo[1], Checker.WHITE);
                createUpsideDownTriangle(trianglesInfo[3], Checker.GREEN);
                createUpsideDownTriangle(trianglesInfo[5], Checker.BLUE);
                break;
            default: throw new WrongNumberOfSetsException();

        }
        return new RegularBoard(board);
    }

    private void makeEmptyBoard() {
        createCentralFields();
        createUprightTriangle(trianglesInfo[0], Checker.EMPTY);
        createUpsideDownTriangle(trianglesInfo[1], Checker.EMPTY);
        createUprightTriangle(trianglesInfo[2], Checker.EMPTY);
        createUpsideDownTriangle(trianglesInfo[3], Checker.EMPTY);
        createUprightTriangle(trianglesInfo[4], Checker.EMPTY);
        createUpsideDownTriangle(trianglesInfo[5], Checker.EMPTY);

    }

    private void createCentralFields(){

        for(int column=4;column<21;column++) {
            for (int row = 4; row < 13; row++){
                if((row+column)%2==0){
                    board[column][row] = new Field(Checker.EMPTY);
                }
            }
        }
    }

    private void createUprightTriangle(int[] triangleInfo, Checker type){
        int column = triangleInfo[0];
        int row = triangleInfo[1];
        int limit = triangleInfo[2];

        int counter=1,numberOfJumps;

        while(row<limit){
            for(numberOfJumps=0;numberOfJumps<counter;numberOfJumps++)
                board[column+numberOfJumps*2][row] = new Field(type);

            row++;
            column--;
            counter++;
        }
    }

    private void createUpsideDownTriangle(int[] triangleInfo, Checker type){
        int column = triangleInfo[0];
        int row = triangleInfo[1];
        int limit = triangleInfo[2];
        int counter=1,numberOfJumps;

        while(row>limit){
            for(numberOfJumps=0;numberOfJumps<counter;numberOfJumps++)
                board[column+numberOfJumps*2][row] = new Field(type);

            row--;
            column--;
            counter++;
        }
    }
}
