package org.example.controller;

import org.example.model.Coordinates;
import org.example.model.Player;
import org.example.model.PlayerTurn;
import org.example.view.TicTacToe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    Player player = new Player();
    TicTacToe ticTacToeView;

    public Controller(TicTacToe ticTacToeView) {
        this.ticTacToeView = ticTacToeView;
    }

    public Optional<PlayerTurn> takeTurn(String cellName) {
        Coordinates coordinates = convertCoordinates(cellName);
        return player.takeTurn(coordinates.getRow(), coordinates.getColumn());
    }

    public String getGameStatus() {
        return player.getGameStatus();
    }

    public void resetGame(ArrayList<JButton> cells) {
        cells.forEach(cell -> cell.setText(" "));
        player.reset();
        TicTacToe.setLabelStatus(getGameStatus());
    }

    private Coordinates convertCoordinates(String cellName) {
        switch (cellName) {
            case "ButtonA3":
                return new Coordinates(0, 0);
            case "ButtonB3":
                return new Coordinates(0, 1);
            case "ButtonC3":
                return new Coordinates(0, 2);
            case "ButtonA2":
                return new Coordinates(1, 0);
            case "ButtonB2":
                return new Coordinates(1, 1);
            case "ButtonC2":
                return new Coordinates(1, 2);
            case "ButtonA1":
                return new Coordinates(2, 0);
            case "ButtonB1":
                return new Coordinates(2, 1);
            case "ButtonC1":
                return new Coordinates(2, 2);
            default:
                return null;
        }
    }
}
