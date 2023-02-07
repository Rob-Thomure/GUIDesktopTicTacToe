package org.example.view;

import org.example.controller.Controller;
import org.example.model.PlayerTurn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Cell extends JButton implements ActionListener {
    Controller controller;

    public Cell(String cellName, Controller controller) {
        super(" ");
        setName("Button" + cellName);
        addActionListener(this);
        Font font = new Font("sans-serif",Font.PLAIN, 40);
        this.setFont(font);
        this.controller = controller;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Optional<PlayerTurn> playerTurn = controller.takeTurn(this.getName());
        if (playerTurn.isPresent()) {
            this.setText(playerTurn.get().toString());
            TicTacToe.setLabelStatus(controller.getGameStatus());
        }
    }
}
