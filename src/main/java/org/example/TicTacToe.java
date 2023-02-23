package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TicTacToe extends JFrame {
    private Controller controller;
    private final JLabel labelStatus;
    private final JButton buttonPlayer1;
    private final JButton buttonPlayer2;
    private final JButton buttonStartReset;
    private final JPanel board;
    private final JButton[][] gameGrid = new JButton[3][3];

    public TicTacToe() {
        this.controller = new Controller(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 600);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel header = new JPanel(new FlowLayout());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuGame = new JMenu("Game");
        menuGame.setName("MenuGame");
        menuBar.add(menuGame);

        JMenuItem menuHumanHuman = new JMenuItem("Human vs Human");
        menuHumanHuman.setName("MenuHumanHuman");
        JMenuItem menuHumanRobot = new JMenuItem("Human vs Robot");
        menuHumanRobot.setName("MenuHumanRobot");
        JMenuItem menuRobotHuman = new JMenuItem("Robot vs Human");
        menuRobotHuman.setName("MenuRobotHuman");
        JMenuItem menuRobotRobot = new JMenuItem("Robot vs Robot");
        menuRobotRobot.setName("MenuRobotRobot");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");

        menuGame.add(menuHumanHuman);
        menuGame.add(menuHumanRobot);
        menuGame.add(menuRobotHuman);
        menuGame.add(menuRobotRobot);
        menuGame.addSeparator();
        menuGame.add(menuExit);

        menuHumanHuman.addActionListener(event -> controller.activateHumanVsHumanMenuItem());
        menuHumanRobot.addActionListener(event -> controller.activateHumanVsRobotMenuItem());
        menuRobotHuman.addActionListener(event -> controller.activateRobotVsHumanMenuItem());
        menuRobotRobot.addActionListener(event -> controller.activateRobotVsRobotMenuItem());
        menuExit.addActionListener(event -> System.exit(0));

        this.buttonPlayer1 = new JButton("Human");
        this.buttonPlayer1.setName("ButtonPlayer1");
        this.buttonPlayer1.setBackground(Color.WHITE);
        this.buttonPlayer1.addActionListener(e -> setPlayerType(buttonPlayer1));
        header.add(this.buttonPlayer1, BorderLayout.WEST);

        this.buttonStartReset = new JButton("Start");
        this.buttonStartReset.setName("ButtonStartReset");
        this.buttonStartReset.setBackground(Color.WHITE);
        this.buttonStartReset.addActionListener(e -> this.controller.startReset());


        header.add(this.buttonStartReset, BorderLayout.CENTER);

        this.buttonPlayer2 = new JButton("Human");
        this.buttonPlayer2.setName("ButtonPlayer2");
        this.buttonPlayer2.setBackground(Color.WHITE);
        this.buttonPlayer2.addActionListener(e -> setPlayerType(buttonPlayer2));
        header.add(this.buttonPlayer2, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        this.board = new JPanel(new GridLayout(3, 3));
        createGameGrid();
        disableCells();
        add(this.board, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        labelStatus = new JLabel("Game is not started");
        labelStatus.setName("LabelStatus");

        footer.add(labelStatus, BorderLayout.WEST);

        add(footer, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void setPlayerType(JButton button) {
        button.setText(button.getText().equals("Human") ? "Robot" : "Human");
    }

    private void createGameGrid() {
        // row & column used for setting the name of the JButton
        // gridRow and gridColumn used for setting coordinates of cells[][]
        for (int row = 3, gridRow = 0; row > 0; row--, gridRow++) {
            for (char column = 'A', gridColumn = 0; column < 'D'; column++, gridColumn++) {
                JButton cell = new JButton(" ");
                cell.setName("Button" + column + row);
                cell.setFont(new Font("sans-serif",Font.PLAIN, 40));
                //cell.addActionListener(e -> controller.humanTakeTurn(cell));
                cell.addActionListener(e -> controller.takeTurn(cell));
                this.board.add(cell);
                gameGrid[gridRow][gridColumn] = cell;
            }
        }
    }

    public String getButtonPlayer1Text() {
        return this.buttonPlayer1.getText();
    }

    public void setButtonPlayer1Text(String text) {
        buttonPlayer1.setText(text);
    }

    public String getButtonPlayer2Text() {
        return this.buttonPlayer2.getText();
    }

    public void setButtonPlayer2Text(String text) {
        buttonPlayer2.setText(text);
    }

    public String getButtonStartResetText() {
        return this.buttonStartReset.getText();
    }

    public JButton[][] getGameGrid() {
        return gameGrid;
    }

    public void setLabelStatus(String text) {
        this.labelStatus.setText(text);
    }

    public void setButtonStartResetText(String text) {
        this.buttonStartReset.setText(text);
    }

    public void enablePlayerButtons() {
        this.buttonPlayer1.setEnabled(true);
        this.buttonPlayer2.setEnabled(true);
    }

    public void disablePlayerButtons() {
        this.buttonPlayer1.setEnabled(false);
        this.buttonPlayer2.setEnabled(false);
    }

    public void enableCells() {
        Arrays.stream(gameGrid)
                .flatMap(Arrays::stream)
                .forEach(cell -> cell.setEnabled(true));
    }

    public void disableCells() {
        Arrays.stream(gameGrid)
                .flatMap(Arrays::stream)
                .forEach(cell -> cell.setEnabled(false));
    }

    public void resetGameGrid() {
        Arrays.stream(this.gameGrid)
                .flatMap(Arrays::stream)
                .forEach(this::setCellBlankText);
    }

    private void setCellBlankText(JButton cell) {
        cell.setText(" ");
    }

    public void placeMoveOnView(Coordinates coordinates, PlayerTurn playerTurn) {
        gameGrid[coordinates.getRow()][coordinates.getColumn()].setText(playerTurn.toString());
    }

}

