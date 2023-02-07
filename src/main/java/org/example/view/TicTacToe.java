package org.example.view;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TicTacToe extends JFrame {
    private Controller controller;
    private static JLabel labelStatus;
    private ArrayList<JButton> cells;
    private JPanel board;

    public TicTacToe() {
        cells = new ArrayList<>();
        this.controller = new Controller(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 600);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        this.board = new JPanel();
        this.board.setLayout(new GridLayout(3, 3));

        createCells();

        add(board, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
        this.labelStatus = new JLabel("Game is not started");
        labelStatus.setName("LabelStatus");

        footer.add(labelStatus, BorderLayout.WEST);

        JButton buttonReset = new JButton("RESET");
        buttonReset.setName("ButtonReset");
        buttonReset.addActionListener(e -> controller.resetGame(cells));
        footer.add(buttonReset, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void createCells() {
        for (int row = 3, gridRow = 0; row > 0; row--, gridRow++) {
            for (char column = 'A', gridColumn = 0; column < 'D'; column++, gridColumn++) {
                String cellName = Character.toString(column) + row;
                JButton cell = new Cell(cellName, controller);
                cells.add(cell);
                board.add(cell);
            }
        }
    }

    public static void setLabelStatus(String text) {
        labelStatus.setText(text);
    }

}