package org.example.model.cellStates;

import java.util.Optional;

public class O implements CellState {
    @Override
    public Optional<CellState> setCellState(CellState cellState) {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "O{}";
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
