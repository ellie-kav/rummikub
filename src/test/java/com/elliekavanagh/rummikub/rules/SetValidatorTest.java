package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.MeldType;
import com.elliekavanagh.rummikub.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetValidatorTest {

    private final SetValidator validator = new SetValidator();

    @Test
    void validSet_threeColorsSameValue() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 7),
                new Tile(Color.BLUE, 7),
                new Tile(Color.YELLOW, 7)
        ));
        assertTrue(validator.isValid(meld));
    }

    @Test
    void validSet_fourColorsSameValue() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 9),
                new Tile(Color.BLUE, 9),
                new Tile(Color.YELLOW, 9),
                new Tile(Color.BLACK, 9)
        ));
        assertTrue(validator.isValid(meld));
    }

    @Test
    void validSet_withJoker() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 5),
                new Tile(Color.BLUE, 5),
                new Tile() // joker stands in for another color 5
        ));
        assertTrue(validator.isValid(meld));
    }

    @Test
    void invalidSet_duplicateColor() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 7),
                new Tile(Color.RED, 7),
                new Tile(Color.BLUE, 7)
        ));
        assertFalse(validator.isValid(meld));
    }

    @Test
    void invalidSet_mixedValues() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 7),
                new Tile(Color.BLUE, 8),
                new Tile(Color.YELLOW, 7)
        ));
        assertFalse(validator.isValid(meld));
    }

    @Test
    void invalidSet_tooShort() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 7),
                new Tile(Color.BLUE, 7)
        ));
        assertFalse(validator.isValid(meld));
    }

    @Test
    void invalidSet_tooLong() {
        Meld meld = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 7),
                new Tile(Color.BLUE, 7),
                new Tile(Color.YELLOW, 7),
                new Tile(Color.BLACK, 7),
                new Tile() // would imply 5 tiles in a set, not allowed
        ));
        assertFalse(validator.isValid(meld));
    }
}
