package org.example.model.gameBoardStates;

import org.example.GameBoard;
import org.example.Player;

import java.util.List;
import java.util.function.Consumer;

public interface GameBoardState {
    GameBoardState markSpace(List<Player> players, Player player,
                             Consumer<GameBoard> markGameBoard);

    default boolean test() {
        return true;
    }
}
