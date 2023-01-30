package org.example;

import java.util.Objects;

public class Player1 implements Player {
    private char symbol = 'X';


    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player1 player1 = (Player1) o;
        return symbol == player1.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
