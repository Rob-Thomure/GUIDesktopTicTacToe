package org.example;

public enum GameStatus {
    GAME_NOT_STARTED("Game is not started"),
    GAME_IN_PROGRESS("Game in progress"),
    HUMAN_PLAYER_X_TURN("The turn of Human Player (X)"),
    ROBOT_PLAYER_X_TURN("The turn of Robot Player (X)"),
    HUMAN_PLAYER_O_TURN("The turn of Human Player (O)"),
    ROBOT_PLAYER_O_TURN("The turn of Robot Player (O)"),

    HUMAN_PLAYER_X_WINS("The Human Player (X) wins"),
    ROBOT_PLAYER_X_WINS("The Robot Player (X) wins"),
    HUMAN_PLAYER_O_WINS("The Human Player (O) wins"),
    ROBOT_PLAYER_O_WINS("The Robot Player (X) wins"),

    X_WINS("X wins"),
    O_WINS("O wins"),
    DRAW("Draw");

    private final String value;

    GameStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
