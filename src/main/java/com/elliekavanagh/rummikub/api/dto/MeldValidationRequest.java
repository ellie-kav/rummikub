package com.elliekavanagh.rummikub.api.dto;

import java.util.List;

/** 
 * Represents a client request to validate a single meld
 * before it is committed to the game state.
 * 
 * A meld can be either a run or a set and 
 * is validated independently of the player's full rack.
*/

public class MeldValidationRequest {
    /**
     * The meld type: either RUN or SET.
     */
    private String type;
    /**
     * Tiles played as part of a single meld.
     */
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
