package org.example.model;

import org.example.model.cellStates.Blank;
import org.example.model.cellStates.CellState;
import org.example.model.cellStates.O;
import org.example.model.cellStates.X;

import java.util.Optional;

public class Cell {
    private CellState cellState;

    {
        this.cellState = new Blank();
    }

    public CellState getCellState() {
        return this.cellState;
    }

    public Optional<CellState> setCellState(CellState cellState) {
        Optional<CellState> newCellState = this.cellState.setCellState(cellState);
        newCellState.ifPresent((a) -> this.cellState = a);
        return newCellState;
    }

}
