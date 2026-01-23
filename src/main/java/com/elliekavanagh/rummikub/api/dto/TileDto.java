package com.elliekavanagh.rummikub.api.dto;

/**
 * Data transfer object representing a single Rummikub tile
 * submitted by the client for validation.
 *
 * Jokers may substitute for any color and value during
 * meld validation.
 */

public class TileDto {
    /**
    * Tile color. Must be one of RED, BLUE, BLACK, or YELLOW.
    */
    private String color;

    /**
    * Tile face value (1–13). May be null if this tile is a joker.
    */
    private Integer value;
    
    /**
    * Indicates whether this tile is a joker.
    */
    private Boolean joker;

    public TileDto() {}

    public TileDto(String color, Integer value, Boolean joker) {
        this.color = color;
        this.value = value;
        this.joker = joker;
    }

    public String getColor() { return color; }
    public Integer getValue() { return value; }
    public Boolean getJoker() { return joker; }

    public void setColor(String color) { this.color = color; }
    public void setValue(Integer value) { this.value = value; }
    public void setJoker(Boolean joker) { this.joker = joker; }
}
