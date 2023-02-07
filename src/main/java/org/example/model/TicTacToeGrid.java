package org.example.model;

import org.example.model.Cell;
import org.example.model.CellState;
import org.example.model.GameStatus;

import java.util.Arrays;

public class TicTacToeGrid {
    public Cell[][] gameGrid;
    private Cell[][] vectors;
    private GameStatus gameStatus;

    private final Cell EMPTY_CELL;
    private final Cell X_CELL;
    private final Cell O_CELL;

    {
        this.gameGrid = new Cell[3][3];
        setGameGrid();
        this.vectors = new Cell[8][3];
        setRowVectors();
        setColumnVectors();
        setDiagonalVectors();
        this.gameStatus = GameStatus.GAME_NOT_STARTED;
        this.EMPTY_CELL = new Cell();
        this.X_CELL = new Cell();
        this.X_CELL.setCellStatus(CellState.X);
        this.O_CELL = new Cell();
        this.O_CELL.setCellStatus(CellState.O);
    }

    public void printGameGrid() {
        for (var row : gameGrid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public void printVectors() {
        for (var row : this.vectors) {
            System.out.println(Arrays.toString(row));
        }
    }

    public boolean setCell(int row, int column, CellState cellState) {
        if (gameGrid[row][column].equals(EMPTY_CELL) &&
                (gameStatus.equals(GameStatus.GAME_NOT_STARTED) || gameStatus.equals(GameStatus.GAME_IN_PROGRESS))) {
            gameGrid[row][column].setCellStatus(cellState);
            updateGameStatus();
            return true;
        }
        return false;
    }

    private void setGameGrid() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                this.gameGrid[row][column] = new Cell();
            }
        }
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

    private void updateGameStatus() {
        if (gameStatus == GameStatus.GAME_NOT_STARTED) {
            gameStatus = GameStatus.GAME_IN_PROGRESS;
        } else if (isThreeInRow(X_CELL)) {
            gameStatus = GameStatus.X_WINS;
        } else if (isThreeInRow(O_CELL)) {
            gameStatus = GameStatus.O_WINS;
        } else if (!hasEmptyCell()) {
            gameStatus = GameStatus.DRAW;
        }
    }

    public boolean isThreeInRow(Cell cellValue) {
        for (var vector : vectors) {
            if (Arrays.stream(vector).allMatch(cell -> cell.equals(cellValue))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEmptyCell() {
        for (var vector : vectors) {
            if (Arrays.stream(vector).anyMatch(cell -> cell.equals(EMPTY_CELL))) {
                return true;
            }
        }
        return false;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

}
