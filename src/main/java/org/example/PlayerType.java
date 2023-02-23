package org.example;

public enum PlayerType {
    HUMAN("Human"),
    ROBOT("Robot");

    String value;

    PlayerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
