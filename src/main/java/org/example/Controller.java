package org.example;

import javax.swing.*;

public class Controller {
    private final TicTacToe ticTacToeView;
    private TicTacToeGame ticTacToeGame;

    public Controller(TicTacToe ticTacToe) {
        this.ticTacToeView = ticTacToe;
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
        ticTacToeView.enableCells();
        this.ticTacToeGame = new TicTacToeGame(PlayerType.valueOf(ticTacToeView.getButtonPlayer1Text().toUpperCase())
                , PlayerType.valueOf(ticTacToeView.getButtonPlayer2Text().toUpperCase()), this);
        ticTacToeView.setLabelStatus(ticTacToeGame.getGameStatus().getValue());
    }

    public void resetGame() {
        ticTacToeView.resetGameGrid();
        ticTacToeView.enablePlayerButtons();
        ticTacToeView.disableCells();
        ticTacToeView.setLabelStatus(GameStatus.GAME_NOT_STARTED.getValue());
        ticTacToeView.setButtonStartResetText("Start");
    }

    public void takeTurn(JButton cell) {
        if (cell.getText().equals(" ") &&
                ticTacToeGame.getGameStatus() != GameStatus.HUMAN_PLAYER_X_WINS &&
                ticTacToeGame.getGameStatus() != GameStatus.HUMAN_PLAYER_O_WINS &&
                ticTacToeGame.getGameStatus() != GameStatus.ROBOT_PLAYER_X_WINS &&
                ticTacToeGame.getGameStatus() != GameStatus.ROBOT_PLAYER_O_WINS &&
                ticTacToeGame.getGameStatus() != GameStatus.DRAW) {
            PlayerTurn playerTurn1 = ticTacToeGame.getPlayerTurn();
            ticTacToeGame.humanTakeTurn(convertToCoordinates(cell.getName()));
            ticTacToeView.setLabelStatus(ticTacToeGame.getGameStatus().getValue());
        }
    }

    public void placeMoveOnCellView(Coordinates coordinates, PlayerTurn playerTurn) {
        ticTacToeView.placeMoveOnView(coordinates, playerTurn);
    }

    public void setViewLabelStatus(String text) {
        ticTacToeView.setLabelStatus(text);
    }

    public void activateHumanVsHumanMenuItem() {
        ticTacToeView.resetGameGrid();
        ticTacToeView.setButtonPlayer1Text("Human");
        ticTacToeView.setButtonPlayer2Text("Human");
        startGame();
    }

    public void activateHumanVsRobotMenuItem() {
        ticTacToeView.resetGameGrid();
        ticTacToeView.setButtonPlayer1Text("Human");
        ticTacToeView.setButtonPlayer2Text("Robot");
        startGame();
    }

    public void activateRobotVsHumanMenuItem() {
        ticTacToeView.resetGameGrid();
        ticTacToeView.setButtonPlayer1Text("Robot");
        ticTacToeView.setButtonPlayer2Text("Human");
        startGame();
    }

    public void activateRobotVsRobotMenuItem() {
        ticTacToeView.resetGameGrid();
        ticTacToeView.setButtonPlayer1Text("Robot");
        ticTacToeView.setButtonPlayer2Text("Robot");
        startGame();
    }

    private Coordinates convertToCoordinates(String cellName) {
        switch (cellName) {
            case "ButtonA3":
                return new Coordinates(0, 0);
            case "ButtonB3":
                return new Coordinates(0, 1);
            case "ButtonC3":
                return new Coordinates(0, 2);
            case "ButtonA2":
                return new Coordinates(1, 0);
            case "ButtonB2":
                return new Coordinates(1, 1);
            case "ButtonC2":
                return new Coordinates(1, 2);
            case "ButtonA1":
                return new Coordinates(2, 0);
            case "ButtonB1":
                return new Coordinates(2, 1);
            case "ButtonC1":
                return new Coordinates(2, 2);
            default:
                return null;
        }
    }

}
