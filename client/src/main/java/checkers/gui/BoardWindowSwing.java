package checkers.client.gui;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;
import checkers.core.boards.RegularBoard;
import checkers.core.boards.WrongMoveException;

import javax.swing.*;
import java.awt.*;

public class BoardWindowSwing extends JFrame{
Board board;
    public BoardWindowSwing() {


        // COLORS
        Color red = Color.RED;
        Color green = Color.GREEN;
        Color blue = Color.BLUE;
        Color black = Color.BLACK;
        Color yellow = Color.YELLOW;
        Color white = Color.WHITE;
        Color orange = Color.ORANGE;

        this.setTitle("MyWarcab");
        this.setLayout(new GridLayout(17, 25));
        this.setSize(650, 650);
        this.setVisible(true);

        JButton button = null;

        for(int i = 0; i < 25; i++){
            for (int j = 0; j < 17; j++) {
                if (board.getFieldOccupiedBy(new Coordinates(i, j)) == Checker.EMPTY) {
                    button = new JButton();
                    button.setBackground(orange);
                    add(button);
                }
                else{

                        button = new JButton();
                        switch (board.getFieldType(new Coordinates(i, j))) [
                        case Color.WHITE
                        {
                            button.setBackground(white);
                            break;
                        }
                        case Color.RED
                        {
                            button.setBackground(red);
                            break;
                        }
                        case Color.YELLOW
                        {
                            button.setBackground(yellow);
                            break;
                        }
                        case Color.BLUE
                        {
                            button.setBackground(blue);
                            break;
                        }
                        case Color.BLACK
                        {
                            button.setBackground(black);
                            break;
                        }
                        case Color.GREEN
                        {
                            button.setBackground(green);
                            break;
                        }

                        default {}


                    }
                    ]
            }
        }


    public static void main(String[] args) {

        String title = "My Chess Board";

        ChessBoard chessBoard = new ChessBoard(title); // Creating the instance of chess board
    }
}