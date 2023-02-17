package org.example.newVersion;

public enum GameStatus {
    GAME_NOT_STARTED("Game is not started"),
    GAME_IN_PROGRESS("Game in progress"),
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
