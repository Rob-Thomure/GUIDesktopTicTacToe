package org.example.model;

import java.util.Objects;
import java.util.function.Predicate;

public class Cell {
    CellState cellState;
    int cellRating;

    {
        this.cellState = CellState.EMPTY;
        this.cellRating = 0;
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

    public int getCellRating() {
        return cellRating;
    }

    public void setCellRating(int cellRating) {
        this.cellRating = cellRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return cellState == cell.cellState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellState);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "cellStatus=" + cellState +
                "cellRating=" + cellRating +
                '}';
    }
}
