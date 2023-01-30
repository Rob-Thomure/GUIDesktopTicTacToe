package org.example.GameBoardStates;

import org.example.GameBoard;
import org.example.GameBoardState;
import org.example.Player;

import java.util.List;
import java.util.function.Consumer;

public class O_Wins implements GameBoardState {
    @Override
    public GameBoardState markSpace(List<Player> players, Player player,
                                    Consumer<GameBoard> markGameBoard) {
        return null;
    }
}
