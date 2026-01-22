package com.elliekavanagh.rummikub.api.dto;

import java.util.List;

public class MeldValidationRequest {
    private String type; // "RUN" or "SET"
    private List<TileDto> tiles;

    public MeldValidationRequest() {}

    public MeldValidationRequest(String type, List<TileDto> tiles) {
        this.type = type;
        this.tiles = tiles;
    }

    public String getType() { return type; }
    public List<TileDto> getTiles() { return tiles; }

    public void setType(String type) { this.type = type; }
    public void setTiles(List<TileDto> tiles) { this.tiles = tiles; }
}
