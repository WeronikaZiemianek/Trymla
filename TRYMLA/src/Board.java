public class Board {

    private int[][] _boardTab;

    public Board() {
        _boardTab = new int[25][17];

        createBoard();
    }

    private void createBoard(){
        createDummyBoard();
        createCentralFields();
        createUpperHouse(12, 0, 4, 1);
        createUpperHouse(21, 9, 13, 3);
        createUpperHouse(3, 9, 13, 5);
        createDownHouse(21, 7 ,3, 2);
        createDownHouse( 12, 16, 12, 4);
        createDownHouse(3, 7, 3, 6);
    }

    public void drow(){

        for(int row=0; row<17; row++) {
            for (int column = 0; column < 25; column++)
                System.out.print(_boardTab[column][row]);
            System.out.println();
        }
    }

    private void createDummyBoard(){

        for(int column=0;column<25;column++) {
            for (int row = 0; row < 17; row++)
                _boardTab[column][row] = -1;
        }
    }

    private void createCentralFields(){

        for(int column=4;column<21;column++) {
            for (int row = 4; row < 13; row++){
                if((row+column)%2==0){
                    _boardTab[column][row] = 0;
                }
            }
        }
    }

    private void createUpperHouse(int column, int row, int limit, int houseNumber){
        int counter=1,numberOfJumps;

        while(row<limit){
            for(numberOfJumps=0;numberOfJumps<counter;numberOfJumps++)
                _boardTab[column+numberOfJumps*2][row] = houseNumber;

            row++;
            column--;
            counter++;
        }
    }

    private void createDownHouse(int column, int row, int limit, int houseNumber){
        int counter=1,numberOfJumps;

        while(row>limit){
            for(numberOfJumps=0;numberOfJumps<counter;numberOfJumps++)
                _boardTab[column+numberOfJumps*2][row] = houseNumber;

            row--;
            column--;
            counter++;
        }
    }
}
