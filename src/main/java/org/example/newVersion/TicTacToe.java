package org.example.newVersion;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public class TicTacToe extends JFrame {
    private final JLabel labelStatus;
    private final JButton buttonPlayer1;
    private final JButton buttonPlayer2;
    private final JButton buttonStartReset;
    private final JPanel board;
    private PlayerTurn playerTurn = PlayerTurn.X;
    private final JButton[][] gameGrid = new JButton[3][3];
    private final JButton[][] vectors = new JButton[8][3];
    private GameStatus gameStatus = GameStatus.GAME_NOT_STARTED;

    Predicate<JButton> isEmptyCell = cell -> cell.getText().equals(" ");
    Predicate<GameStatus> gameIsNotStarted = gameState -> gameState.equals(GameStatus.GAME_NOT_STARTED);
    Predicate<GameStatus> gameIsInProgress = gameState -> gameState.equals(GameStatus.GAME_IN_PROGRESS);

    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 600);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel header = new JPanel(new FlowLayout());



        this.buttonPlayer1 = new JButton("Human");
        this.buttonPlayer1.setName("ButtonPlayer1");
        this.buttonPlayer1.setBackground(Color.WHITE);
        this.buttonPlayer1.addActionListener(e -> setPlayerType(buttonPlayer1));
        header.add(this.buttonPlayer1, BorderLayout.WEST);

        this.buttonStartReset = new JButton("Start");
        this.buttonStartReset.setName("ButtonStartReset");
        this.buttonStartReset.setBackground(Color.WHITE);
        this.buttonStartReset.addActionListener(e -> startReset());
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
        setRowVectors();
        setColumnVectors();
        setDiagonalVectors();

        JPanel footer = new JPanel(new BorderLayout());
        labelStatus = new JLabel("Game is not started");
        labelStatus.setName("LabelStatus");

        footer.add(labelStatus, BorderLayout.WEST);

        add(footer, BorderLayout.SOUTH);
        setVisible(true);






    }

// ******************************************************************************************

    private void startThread() {
        Thread t1 = new Thread(() -> {
            do {
                if (buttonPlayer1.getText().equals("Robot") && playerTurn == PlayerTurn.X) {
                    robotTakeTurn();
                } else if (buttonPlayer2.getText().equals("Robot") && playerTurn == PlayerTurn.O ) {
                    System.out.println("test");
                    robotTakeTurn();
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (gameStatus.equals(GameStatus.GAME_IN_PROGRESS) || gameStatus.equals(GameStatus.GAME_NOT_STARTED)
                    && (buttonStartReset.getText().equals("Reset")));
        });
        t1.start();
    }

    private void robotTakeTurn() {
        Optional<JButton> emptyCell = Arrays.stream(gameGrid)
                .flatMap(Arrays::stream)
                .filter(isEmptyCell)
                .findFirst();
        emptyCell.get().setText(playerTurn.toString());
        updateGameStatus();
        changePlayerTurn();
    }

    private void startReset() {
        if (this.buttonStartReset.getText().equals("Start")) {
            startGame();
        } else {
            resetGame();
        }
    }

    private void startGame() {
        this.buttonStartReset.setText("Reset");
        disablePlayerButtons();
        gameStatus = GameStatus.GAME_IN_PROGRESS;
        labelStatus.setText(GameStatus.GAME_IN_PROGRESS.getValue());
        enableCells();
        if (buttonPlayer1.getText().equals("Robot") || buttonPlayer2.getText().equals("Robot")) {
            startThread();
        }
    }

    private void resetGame() {
        Arrays.stream(gameGrid)
                .flatMap(array -> Arrays.stream(array))
                .forEach(cell -> cell.setText(" "));
        playerTurn = PlayerTurn.X;
        gameStatus = GameStatus.GAME_NOT_STARTED;
        labelStatus.setText(GameStatus.GAME_NOT_STARTED.getValue());
        this.buttonStartReset.setText("Start");
        enablePlayerButtons();
        disableCells();
    }

    private void disablePlayerButtons() {
        this.buttonPlayer1.setEnabled(false);
        this.buttonPlayer2.setEnabled(false);
    }

    private void enablePlayerButtons() {
        this.buttonPlayer1.setEnabled(true);
        this.buttonPlayer2.setEnabled(true);
    }

    private void disableCells() {
        Arrays.stream(gameGrid)
                .flatMap(Arrays::stream)
                .forEach(cell -> cell.setEnabled(false));
    }

    private void enableCells() {
        Arrays.stream(gameGrid)
                .flatMap(Arrays::stream)
                .forEach(cell -> cell.setEnabled(true));
    }

    private void createGameGrid() {
        // row & column used for setting the name of the JButton
        // gridRow and gridColumn used for setting coordinates of cells[][]
        for (int row = 3, gridRow = 0; row > 0; row--, gridRow++) {
            for (char column = 'A', gridColumn = 0; column < 'D'; column++, gridColumn++) {
                JButton cell = new JButton(" ");
                cell.setName("Button" + column + row);
                cell.setFont(new Font("sans-serif",Font.PLAIN, 40));
                cell.addActionListener(e -> {
                    if ((playerTurn == PlayerTurn.X && buttonPlayer1.getText().equals("Human"))
                            || (playerTurn == PlayerTurn.O && buttonPlayer2.getText().equals("Human"))) {
                        humanTakeTurn(cell);
                    }

                } );
                this.board.add(cell);
                gameGrid[gridRow][gridColumn] = cell;
            }
        }
    }

    private void humanTakeTurn(JButton cell) {
        if (isEmptyCell.test(cell) && (gameIsNotStarted.or(gameIsInProgress).test(gameStatus))) {
            cell.setText(playerTurn.toString());
            updateGameStatus();
            changePlayerTurn();
        }

    }



    private void updateGameStatus() {
        if (gameIsNotStarted.test(gameStatus)) {
            gameStatus = GameStatus.GAME_IN_PROGRESS;
        } else if (isThreeInRow("X")) {
            gameStatus = GameStatus.X_WINS;
        } else if (isThreeInRow("O")) {
            gameStatus = GameStatus.O_WINS;
        } else if (!hasEmptyCell()) {
            gameStatus = GameStatus.DRAW;
        }
        labelStatus.setText(gameStatus.getValue());
    }

    private boolean isThreeInRow(String player) {
        long count = Arrays.stream(vectors)
                .map(Arrays::stream)
                .filter(a -> a.allMatch(cell -> cell.getText().equals(player)))
                .count();
        return count >= 1;
    }

    private boolean hasEmptyCell() {
        long count = Arrays.stream(vectors)
                .map(Arrays::stream)
                .filter(a -> a.anyMatch(cell -> cell.getText().equals(" ")))
                .count();
        return count > 0;
    }

    private void changePlayerTurn() {
        this.playerTurn = playerTurn == PlayerTurn.X ? PlayerTurn.O : PlayerTurn.X;
    }

    private void setPlayerType(JButton button) {
        button.setText(button.getText().equals("Human") ? "Robot" : "Human");
    }


    private void setRowVectors() {
        this.vectors[0] = this.gameGrid[0];
        this.vectors[1] = this.gameGrid[1];
        this.vectors[2] = this.gameGrid[2];
    }

    private void setColumnVectors() {
        // column 1
        this.vectors[3][0] = this.gameGrid[0][0];
        this.vectors[3][1] = this.gameGrid[1][0];
        this.vectors[3][2] = this.gameGrid[2][0];
        // column 2
        this.vectors[4][0] = this.gameGrid[0][1];
        this.vectors[4][1] = this.gameGrid[1][1];
        this.vectors[4][2] = this.gameGrid[2][1];
        // column 3
        this.vectors[5][0] = this.gameGrid[0][2];
        this.vectors[5][1] = this.gameGrid[1][2];
        this.vectors[5][2] = this.gameGrid[2][2];
    }

    private void setDiagonalVectors() {
        // major diagonal
        this.vectors[6][0] = this.gameGrid[0][0];
        this.vectors[6][1] = this.gameGrid[1][1];
        this.vectors[6][2] = this.gameGrid[2][2];
        // minor diagonal
        this.vectors[7][0] = this.gameGrid[2][0];
        this.vectors[7][1] = this.gameGrid[1][1];
        this.vectors[7][2] = this.gameGrid[0][2];
    }











}
