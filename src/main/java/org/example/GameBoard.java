package org.example;

import org.example.model.gameBoardStates.GameBoardState;
import org.example.model.gameBoardStates.NotStarted;

import java.util.Arrays;
import java.util.List;

public class GameBoard {
    List<List<Player>> gameGrid;
    GameBoardState gameBoardState;

    {
        this.gameGrid = Arrays.asList(
                Arrays.asList(new Empty(), new Empty(), new Empty()),
                Arrays.asList(new Empty(), new Empty(), new Empty()),
                Arrays.asList(new Empty(), new Empty(), new Empty()));
        this.gameBoardState = new NotStarted();
    }

    public void markGameGrid() {

    }

    private void addPlayerToGameGrid(Player player, int row, int column) {
        this.gameGrid.get(row - 1).set(column - 1, player);
    }

    private List<Player> getRow1() {
        return this.gameGrid.get(0);
    }

    private List<Player> getRow2() {
        return this.gameGrid.get(1);
    }

    private List<Player> getRow3() {
        return this.gameGrid.get(2);
    }

    private List<Player> getColumn1() {
        return Arrays.asList(this.gameGrid.get(0).get(0),
                this.gameGrid.get(1).get(0),
                this.gameGrid.get(2).get(0));
    }

    private List<Player> getColumn2() {
        return Arrays.asList(this.gameGrid.get(0).get(1),
                this.gameGrid.get(1).get(1),
                this.gameGrid.get(2).get(1));
    }

    private List<Player> getColumn3() {
        return Arrays.asList(this.gameGrid.get(0).get(2),
                this.gameGrid.get(1).get(2),
                this.gameGrid.get(2).get(2));
    }

    private List<Player> getMajorDiagonal() {
        return Arrays.asList(this.gameGrid.get(0).get(0),
                this.gameGrid.get(1).get(1),
                this.gameGrid.get(2).get(2));
    }

    private List<Player> getMinorDiagonal() {
        return Arrays.asList(this.gameGrid.get(0).get(2),
                this.gameGrid.get(1).get(1),
                this.gameGrid.get(2).get(0));
    }

    private boolean isThreeInRow(List<Player> sectional) {
        List<List<Player>> sections = Arrays.asList(
                getRow1(), getRow2(), getRow3(),
                getColumn1(), getColumn2(), getColumn3(),
                getMajorDiagonal(), getMinorDiagonal()
        );
        for (var section: sections) {
            long count = section.stream()
                    .filter(cell -> !cell.equals(new Empty()))
                    .count();
            if (count == 3) {
                return true;
            }
        }
        return false;

    }

    private boolean isDraw(List<Player> sectional) {
        return sectional.stream()
                .anyMatch(cell -> cell.equals(new Empty()));
    }













    public static void main(String[] args) {

    }
}

