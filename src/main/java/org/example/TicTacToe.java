package org.example;

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 450);
        setResizable(false);
        setLayout(null);

        JPanel board = new JPanel(new GridLayout(3, 3, 5, 5));
        board.setSize(400, 400);
        add(board);


        for (int row = 3; row > 0; row--) {
            for (char column = 'A'; column < 'D'; column++) {
                String cellName = Character.toString(column) + row;
                JButton cell = new Cell(cellName);
                board.add(cell);

            }
        }


        setVisible(true);
    }


}