package org.example.newVersion;

import javax.swing.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    private final TicTacToe ticTacToeView;

    private PlayerTurn playerTurn;
    private final JButton[][] vectors;
    private GameStatus gameStatus;

    private String player1;
    private String player2;

    Predicate<JButton> isEmptyCell = cell -> cell.getText().equals(" ");
    Predicate<GameStatus> gameIsNotStarted = gameState -> gameState.equals(GameStatus.GAME_NOT_STARTED);
    Predicate<GameStatus> gameIsInProgress = gameState -> gameState.equals(GameStatus.GAME_IN_PROGRESS);

    public Controller(TicTacToe ticTacToe) {
        this.ticTacToeView = ticTacToe;
    }

    {
        this.playerTurn = PlayerTurn.X;
        this.vectors = new JButton[8][3];
        this.gameStatus = GameStatus.GAME_NOT_STARTED;
        this.player1 = "Human";
        this.player2 = "Human";
    }

    private void startThread() {
        Thread t1 = new Thread(() -> {
            do {
                if (ticTacToeView.getButtonPlayer1Text().equals("Robot") && playerTurn == PlayerTurn.X) {
                    robotTakeTurn();
                } else if (ticTacToeView.getButtonPlayer2Text().equals("Robot") && playerTurn == PlayerTurn.O ) {
                    robotTakeTurn();
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (gameStatus.equals(GameStatus.GAME_IN_PROGRESS) || gameStatus.equals(GameStatus.GAME_NOT_STARTED)
                    && (ticTacToeView.getButtonStartResetText().equals("Reset")));
        });
        t1.start();
    }

    private void robotTakeTurn() {
        Optional<JButton> emptyCell = Arrays.stream(ticTacToeView.getGameGrid())
                .flatMap(Arrays::stream)
                .filter(isEmptyCell)
                .findFirst();
        emptyCell.get().setText(playerTurn.toString());
        updateGameStatus();
        changePlayerTurn();
        setLabelStatus();
    }

    private void updateGameStatus() {
        JButton[][] gameGrid = ticTacToeView.getGameGrid();
        setRowVectors(gameGrid);
        setColumnVectors(gameGrid);
        setDiagonalVectors(gameGrid);
        if (this.gameIsNotStarted.test(gameStatus)) {
            gameStatus = GameStatus.GAME_IN_PROGRESS;
        } else if (isThreeInRow("X")) {
            gameStatus = GameStatus.X_WINS;
        } else if (isThreeInRow("O")) {
            gameStatus = GameStatus.O_WINS;
        } else if (!hasEmptyCell()) {
            gameStatus = GameStatus.DRAW;
        }
    }

    private void setLabelStatus() {
        String text = "";
        if (this.gameStatus == GameStatus.GAME_IN_PROGRESS) {
            text = String.format("The turn of %s Player (%s)",
                    playerTurn == PlayerTurn.X ? player1 : player2,
                    playerTurn.toString());
        } else if (this.gameStatus == GameStatus.X_WINS) {
            text = String.format("The %s Player (X) wins", player1);
        } else if (this.gameStatus == GameStatus.O_WINS) {
            text = String.format("The %s Player (O) wins", player2);
        } else if (this.gameStatus == GameStatus.DRAW) {
            text = "Draw";
        }
        ticTacToeView.setLabelStatus(text);
    }

    private void changePlayerTurn() {
        this.playerTurn = playerTurn == PlayerTurn.X ? PlayerTurn.O : PlayerTurn.X;
    }

    private boolean isThreeInRow(String player) {
        long count = Arrays.stream(vectors)
                .map(Arrays::stream)
                .filter(a -> a.allMatch(cell -> cell.getText().equals(player)))
                .count();
        return count >= 1;
    }

    private void setRowVectors(JButton[][] gameGrid) {
        this.vectors[0] = gameGrid[0];
        this.vectors[1] = gameGrid[1];
        this.vectors[2] = gameGrid[2];
    }

    private void setColumnVectors(JButton[][] gameGrid) {
        // column 1
        this.vectors[3][0] = gameGrid[0][0];
        this.vectors[3][1] = gameGrid[1][0];
        this.vectors[3][2] = gameGrid[2][0];
        // column 2
        this.vectors[4][0] = gameGrid[0][1];
        this.vectors[4][1] = gameGrid[1][1];
        this.vectors[4][2] = gameGrid[2][1];
        // column 3
        this.vectors[5][0] = gameGrid[0][2];
        this.vectors[5][1] = gameGrid[1][2];
        this.vectors[5][2] = gameGrid[2][2];
    }

    private void setDiagonalVectors(JButton[][] gameGrid) {
        // major diagonal
        this.vectors[6][0] = gameGrid[0][0];
        this.vectors[6][1] = gameGrid[1][1];
        this.vectors[6][2] = gameGrid[2][2];
        // minor diagonal
        this.vectors[7][0] = gameGrid[2][0];
        this.vectors[7][1] = gameGrid[1][1];
        this.vectors[7][2] = gameGrid[0][2];
    }

    private boolean hasEmptyCell() {
        long count = Arrays.stream(vectors)
                .map(Arrays::stream)
                .filter(a -> a.anyMatch(cell -> cell.getText().equals(" ")))
                .count();
        return count > 0;
    }

    public void startReset() {
        if (ticTacToeView.getButtonStartResetText().equals("Start")) {
            startGame();
        } else {
            resetGame();
        }
    }

    private void startGame() {
        ticTacToeView.setButtonStartResetText("Reset");
        ticTacToeView.disablePlayerButtons();
        gameStatus = GameStatus.GAME_IN_PROGRESS;
        //ticTacToeView.setLabelStatus(GameStatus.GAME_IN_PROGRESS.getValue());
        setLabelStatus();
        ticTacToeView.enableCells();
        this.player1 = ticTacToeView.getButtonPlayer1Text();
        this.player2 = ticTacToeView.getButtonPlayer2Text();
        if (ticTacToeView.getButtonPlayer1Text().equals("Robot") || ticTacToeView.getButtonPlayer2Text().equals("Robot")) {
            startThread();
        }
    }

    public void resetGame() {
        ticTacToeView.resetGameGrid();
        playerTurn = PlayerTurn.X;
        gameStatus = GameStatus.GAME_NOT_STARTED;
        ticTacToeView.setLabelStatus(GameStatus.GAME_NOT_STARTED.getValue());
        ticTacToeView.setButtonStartResetText("Start");
        ticTacToeView.enablePlayerButtons();
        ticTacToeView.disableCells();
    }

    public void humanTakeTurn(JButton cell) {
        if ( (isEmptyCell.test(cell) && (gameIsNotStarted.or(gameIsInProgress).test(gameStatus))) &&
                (  playerTurn == PlayerTurn.X && ticTacToeView.getButtonPlayer1Text().equals("Human")
                || (playerTurn == PlayerTurn.O && ticTacToeView.getButtonPlayer2Text().equals("Human")))) {
            cell.setText(playerTurn.toString());
            updateGameStatus();
            changePlayerTurn();
            setLabelStatus();
        }
    }

    public void activateHumanVsHumanMenuItem() {
        ticTacToeView.resetGameGrid();
        this.playerTurn = PlayerTurn.X;
        ticTacToeView.setButtonPlayer1Text("Human");
        ticTacToeView.setButtonPlayer2Text("Human");
        startGame();
    }

    public void activateHumanVsRobotMenuItem() {
        ticTacToeView.resetGameGrid();
        this.playerTurn = PlayerTurn.X;
        ticTacToeView.setButtonPlayer1Text("Human");
        ticTacToeView.setButtonPlayer2Text("Robot");
        startGame();
    }

    public void activateRobotVsHumanMenuItem() {
        ticTacToeView.resetGameGrid();
        this.playerTurn = PlayerTurn.X;
        ticTacToeView.setButtonPlayer1Text("Robot");
        ticTacToeView.setButtonPlayer2Text("Human");
        startGame();
    }

    public void activateRobotVsRobotMenuItem() {
        ticTacToeView.resetGameGrid();
        this.playerTurn = PlayerTurn.X;
        ticTacToeView.setButtonPlayer1Text("Robot");
        ticTacToeView.setButtonPlayer2Text("Robot");
        startGame();
    }

}
