package org.example.temp;

import java.util.Objects;

public class Cell {
    CellStatus cellStatus;

    {
        this.cellStatus = CellStatus.EMPTY;
    }

    public boolean setCellStatus(CellStatus cellStatus) {
        if (this.cellStatus.equals(CellStatus.EMPTY)) {
            this.cellStatus = cellStatus;
            return true;
        } else {
            return false;
        }
    }

    public CellStatus getCellStatus() {
        return this.cellStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return cellStatus == cell.cellStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellStatus);
    }

}
