package org.example.model;


import java.util.Optional;

public class Player {
    PlayerTurn playerTurn;
    TicTacToeGrid ticTacToeGrid;

    {
        this.playerTurn = PlayerTurn.X;
        this.ticTacToeGrid = new TicTacToeGrid();
    }

    private void changePlayerTurn() {
        this.playerTurn = playerTurn == PlayerTurn.X ? PlayerTurn.O : PlayerTurn.X;
    }

    public Optional<PlayerTurn> takeTurn(int row, int column) {
        CellState cellState = playerTurn == PlayerTurn.X ? CellState.X : CellState.O;
        boolean successful = ticTacToeGrid.setCell(row, column, cellState);
        if (successful) {
            PlayerTurn currentPlayerTurn = this.playerTurn;
            changePlayerTurn();
            return Optional.of(currentPlayerTurn);
        } else {
            return Optional.empty();
        }
    }

    public String getGameStatus() {
        return ticTacToeGrid.getGameStatus().getValue();
    }

    public void reset() {
        ticTacToeGrid = new TicTacToeGrid();
        playerTurn = PlayerTurn.X;
    }



}
