package org.example.model.cellStates;

import java.util.Optional;

public class X implements CellState {
    @Override
    public Optional<CellState> setCellState(CellState cellState) {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "X{}";
    }
}