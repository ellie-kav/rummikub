package com.elliekavanagh.rummikub.api.dto;

public class TileDto {
    private String color;   // "RED", "BLUE", "BLACK", "YELLOW"
    private Integer value;  // 1..13
    private Boolean joker;  // true if joker

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
