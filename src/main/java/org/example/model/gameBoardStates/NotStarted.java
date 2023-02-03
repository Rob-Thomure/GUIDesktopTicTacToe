package org.example.model.gameBoardStates;

import org.example.model.Cell;
import org.example.model.GameBoard;
import org.example.Player;

import java.util.List;
import java.util.function.Consumer;

public class NotStarted implements GameBoardState {

    @Override
    public GameBoardState markSpace(List<Player> players, Player player,
                                    Consumer<GameBoard> markGameBoard) {




        return new InProgress();
    }

    public void test(Cell cell) {

    }


}
