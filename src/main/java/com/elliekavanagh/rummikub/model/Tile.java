package com.elliekavanagh.rummikub.model;

import java.util.Objects;

public class Tile {

    private final Color color;
    private final Integer value; // null if joker
    private final boolean isJoker;

    // Constructor for normal tiles
    public Tile(Color color, int value) {
        if (value < 1 || value > 13) {
            throw new IllegalArgumentException("Tile value must be between 1 and 13");
        }
        this.color = Objects.requireNonNull(color);
        this.value = value;
        this.isJoker = false;
    }

    // Constructor for joker
    public Tile() {
        this.color = null;
        this.value = null;
        this.isJoker = true;
    }

    public boolean isJoker() {
        return isJoker;
    }

    public Color getColor() {
        return color;
    }

    public Integer getValue() {
        return value;
    }

    public static Tile joker() {
        return new Tile();
    }
    

    @Override
    public String toString() {
        return isJoker ? "JOKER" : value + " " + color;
    }
}
