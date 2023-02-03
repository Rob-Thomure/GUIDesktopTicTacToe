package org.example.model;

import org.example.Empty;
import org.example.model.cellStates.CellState;
import org.example.model.cellStates.X;
import org.example.model.gameBoardStates.GameBoardState;
import org.example.model.gameBoardStates.NotStarted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    List<List<Cell>> gameGrid;
    GameBoardState gameBoardState;
    List<List<Cell>> allSections;

    {
        this.gameGrid = Arrays.asList(
                Arrays.asList(new Cell(), new Cell(), new Cell()),
                Arrays.asList(new Cell(), new Cell(), new Cell()),
                Arrays.asList(new Cell(), new Cell(), new Cell()));
        this.gameBoardState = new NotStarted();

        this.allSections = new ArrayList<>();
        this.allSections.add(getRow1());
        this.allSections.add(getRow2());
        this.allSections.add(getRow3());
        this.allSections.add(getColumn1());
        this.allSections.add(getColumn2());
        this.allSections.add(getColumn3());
        this.allSections.add(getMajorDiagonal());
        this.allSections.add(getMinorDiagonal());
    }

    // TODO this is temp
    public void printAllSections() {
        allSections.forEach(System.out::println);
    }

    private void setCellState() {

    }

    public void setCell(int row, int column, CellState cellState) {
        gameGrid.get(row - 1).get(column - 1).setCellState(cellState);
    }

    private List<Cell> getRow1() {
        return this.gameGrid.get(0);
    }

    private List<Cell> getRow2() {
        return this.gameGrid.get(1);
    }

    private List<Cell> getRow3() {
        return this.gameGrid.get(2);
    }

    private List<Cell> getColumn1() {
        return Arrays.asList(this.gameGrid.get(0).get(0),
                this.gameGrid.get(1).get(0),
                this.gameGrid.get(2).get(0));
    }

    private List<Cell> getColumn2() {
        return Arrays.asList(this.gameGrid.get(0).get(1),
                this.gameGrid.get(1).get(1),
                this.gameGrid.get(2).get(1));
    }

    private List<Cell> getColumn3() {
        return Arrays.asList(this.gameGrid.get(0).get(2),
                this.gameGrid.get(1).get(2),
                this.gameGrid.get(2).get(2));
    }

    private List<Cell> getMajorDiagonal() {
        return Arrays.asList(this.gameGrid.get(0).get(0),
                this.gameGrid.get(1).get(1),
                this.gameGrid.get(2).get(2));
    }

    private List<Cell> getMinorDiagonal() {
        return Arrays.asList(this.gameGrid.get(0).get(2),
                this.gameGrid.get(1).get(1),
                this.gameGrid.get(2).get(0));
    }

    public boolean isThreeInRow(CellState cellState) {
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



    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        System.out.println(gameBoard.isThreeInRow(new X()));
        gameBoard.setCell(1, 1, new X());
        System.out.println(gameBoard.isThreeInRow(new X()));
        gameBoard.setCell(2, 1, new X());
        System.out.println(gameBoard.isThreeInRow(new X()));
        gameBoard.setCell(3, 1, new X());
        System.out.println(gameBoard.isThreeInRow(new X()));

    }
}

