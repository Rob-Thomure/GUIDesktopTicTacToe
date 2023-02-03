package org.example.model.gameBoardStates;

import org.example.Empty;
import org.example.model.Cell;
import org.example.model.GameBoard;
import org.example.Player;
import org.example.model.cellStates.CellState;

import java.util.List;
import java.util.function.Consumer;

public class InProgress implements GameBoardState {
    @Override
    public GameBoardState markSpace(List<Player> players, Player player,
                                    Consumer<GameBoard> markGameBoard) {
        return null;
    }

    private boolean isThreeInRow(List<List<Cell>> allSections, CellState cellState) {
        for (var element : allSections) {
            if (element.stream().allMatch(cell -> cell.getCellState().equals(cellState))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw(List<Cell> sectional) {
        return sectional.stream()
                .anyMatch(cell -> cell.getCellState().equals(new Empty()));
    }
}
