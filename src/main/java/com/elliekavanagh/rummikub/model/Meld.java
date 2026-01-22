package com.elliekavanagh.rummikub.model;

import java.util.List;

public class Meld {

    private final MeldType type;
    private final List<Tile> tiles;

    public Meld(MeldType type, List<Tile> tiles) {
        this.type = type;
        this.tiles = tiles;
    }

    public MeldType getType() {
        return type;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
