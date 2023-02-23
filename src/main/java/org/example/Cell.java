package org.example;

import java.util.Objects;
import java.util.function.Predicate;

public class Cell {
    private CellState cellState;
    private int winnableRating;
    private Coordinates coordinates;

    public Cell() {
    }

    public Cell(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    {
        this.cellState = CellState.EMPTY;
        this.winnableRating = 0;
    }

    Predicate<CellState> isEmptyCell = cell -> cell.equals(CellState.EMPTY);

    public boolean setCellStatus(CellState cellState) {
        if (isEmptyCell.test(this.cellState)) {
            this.cellState = cellState;
            return true;
        } else {
            return false;
        }
    }

    public CellState getCellStatus() {
        return this.cellState;
    }

    public int getWinnableRating() {
        return winnableRating;
    }

    public void setWinnableRating(int winnableRating) {
        this.winnableRating = winnableRating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return this.cellState == cell.getCellStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellState);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "cellStatus=" + cellState +
                "cellRating=" + winnableRating +
                '}';
    }
}
