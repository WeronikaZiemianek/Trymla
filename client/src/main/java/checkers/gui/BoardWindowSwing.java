package checkers.gui;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.boards.Board;

import javax.swing.*;
import java.awt.*;

public class BoardWindowSwing extends JFrame {
    Board board;
    Color red = Color.RED;
    Color green = Color.GREEN;
    Color blue = Color.BLUE;
    Color black = Color.BLACK;
    Color yellow = Color.YELLOW;
    Color white = Color.WHITE;
    Color orange = Color.ORANGE;

    public BoardWindowSwing(int rows, int cols) {

        this.setTitle("MyWarcab");
        this.setLayout(new GridLayout(17, 25));
        this.setSize(650, 650);
        this.setVisible(true);

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                {
                    JButton button = new JButton(Integer.toString(i + 1 + j));

                    if (board.getFieldOccupiedBy(new Coordinates(i, j)) == Checker.EMPTY) {
                        button.setBackground(orange);
                    } else {

                        switch (board.getFieldType(new Coordinates(i, j))) {
                            case WHITE:{
                                button.setBackground(white);
                                break;
                            }
                            case RED: {
                                button.setBackground(red);
                                break;
                            }
                            case YELLOW: {
                                button.setBackground(yellow);
                                break;
                            }
                            case BLUE: {
                                button.setBackground(blue);
                                break;
                            }
                            case BLACK: {
                                button.setBackground(black);
                                break;
                            }
                            case GREEN: {
                                button.setBackground(green);
                                break;
                            }
                        }
                    }
                    pane.add(button);
                }
            }
        }
    }

    public static void main() {
        int rows = 25;
        int cols = 17;
        BoardWindowSwing gt = new BoardWindowSwing(rows, cols);
        gt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gt.pack();
        gt.setVisible(true);
    }

}
