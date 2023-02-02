package org.example.model.cellStates;

import java.util.Optional;

public class Blank implements CellState {


    @Override
    public Optional<CellState> setCellState(CellState cellState) {
        return Optional.ofNullable(cellState);
    }

    @Override
    public String toString() {
        return "Blank{}";
    }
}
