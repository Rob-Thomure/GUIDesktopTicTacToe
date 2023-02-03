package org.example.model.gameBoardStates;

import org.example.Player;
import org.example.model.GameBoard;

import java.util.List;
import java.util.function.Consumer;

public interface GameBoardState {
    GameBoardState markSpace(List<Player> players, Player player,
                             Consumer<GameBoard> markGameBoard);

}
