package org.example;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

public class TicTacToeGrid {
    private final Cell[][] gameGrid;
    private final Cell[][] vectors;
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
        this.EMPTY_CELL = new Cell();
        this.X_CELL = new Cell();
        this.X_CELL.setCellStatus(CellState.X);
        this.O_CELL = new Cell();
        this.O_CELL.setCellStatus(CellState.O);
    }

    Predicate<Cell> isEmptyCell = cell -> cell.equals(EMPTY_CELL);

    public boolean setCell(int row, int column, CellState cellState) {
        if (isEmptyCell.test(gameGrid[row][column]))  {
            gameGrid[row][column].setCellStatus(cellState);
            setAllCellWinnableRatings();
            return true;
        } else {
            return false;
        }
    }

    public boolean hasThreeInRow(Cell cellValue) {
        Long count = Arrays.stream(vectors)
                .map(a -> Arrays.stream(a))
                .filter(a -> a.allMatch(cell -> cell.equals(cellValue)))
                .count();
        return count >= 1;
    }

    public boolean hasEmptyCell() {
        for (var vector : vectors) {
            if (Arrays.asList(vector).contains(EMPTY_CELL)) {
                return true;
            }
        }
        return false;
    }

    public Coordinates findBestMove() {
        OptionalInt highestCellRating = Arrays.stream(gameGrid)
                .flatMap(array -> Arrays.stream(array))
                .mapToInt(cell -> cell.getWinnableRating())
                .max();
        Optional<Cell> highestRatedCell = Arrays.stream(gameGrid)
                .flatMap(array -> Arrays.stream(array))
                .filter(cell -> cell.getWinnableRating() == highestCellRating.getAsInt())
                .findFirst();
        return highestRatedCell.get().getCoordinates();
    }

    private void setGameGrid() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                this.gameGrid[row][column] = new Cell(new Coordinates(row, column));
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

    private void setAllCellWinnableRatings() {
        clearAllCellWinnableRatings();
        Arrays.stream(vectors)
                .filter(this::hasOneMatchingInVector)
                .flatMap(vector -> Arrays.stream(vector).filter(this::isEmptyCell))
                .forEach(cell -> cell.setWinnableRating(10));
        Arrays.stream(vectors)
                .filter(this::hasTwoMatchingInVector)
                .flatMap(vector -> Arrays.stream(vector).filter(this::isEmptyCell))
                .forEach(cell -> cell.setWinnableRating(20));
    }

    private void clearAllCellWinnableRatings() {
        Arrays.stream(vectors)
                .flatMap(vector -> Arrays.stream(vector))
                .forEach(cell -> cell.setWinnableRating(0));
    }

    private boolean isEmptyCell(Cell cell) {
        return cell.equals(EMPTY_CELL);
    }

    private boolean hasTwoMatchingInVector(Cell[] vector) {
        return vectorHasTwoX(vector) || vectorHasTwoO(vector);
    }

    private boolean vectorHasTwoX(Cell[] vector) {
        long count = Arrays.stream(vector)
                .filter(a -> a.equals(X_CELL))
                .count();
        return count == 2;
    }

    private boolean vectorHasTwoO(Cell[] vector) {
        long count = Arrays.stream(vector)
                .filter(a -> a.equals(O_CELL))
                .count();
        return count == 2;
    }

    private boolean hasOneMatchingInVector(Cell[] vector) {
        return vectorHasOneX(vector) || vectorHasOneO(vector);
    }

    private boolean vectorHasOneX(Cell[] vector) {
        long count = Arrays.stream(vector)
                .filter(a -> a.equals(X_CELL))
                .count();
        return count == 1;
    }

    private boolean vectorHasOneO(Cell[] vector) {
        long count = Arrays.stream(vector)
                .filter(a -> a.equals(O_CELL))
                .count();
        return count == 1;
    }

}