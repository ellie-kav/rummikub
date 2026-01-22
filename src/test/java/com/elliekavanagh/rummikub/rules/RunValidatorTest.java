package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.MeldType;
import com.elliekavanagh.rummikub.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunValidatorTest {

    private final RunValidator validator = new RunValidator();

    @Test
    void validSimpleRun_threeConsecutiveSameColor() {
        Meld meld = new Meld(MeldType.RUN, List.of(
                new Tile(Color.RED, 5),
                new Tile(Color.RED, 6),
                new Tile(Color.RED, 7)
        ));
        assertTrue(validator.isValid(meld));
    }

    @Test
    void validRun_withJokerFillingInternalGap() {
        Meld meld = new Meld(MeldType.RUN, List.of(
                new Tile(Color.BLUE, 10),
                new Tile(),                 // joker represents 11
                new Tile(Color.BLUE, 12)
        ));
        assertTrue(validator.isValid(meld));
    }

    @Test
    void invalidRun_mixedColors() {
        Meld meld = new Meld(MeldType.RUN, List.of(
                new Tile(Color.RED, 5),
                new Tile(Color.BLUE, 6),
                new Tile(Color.RED, 7)
        ));
        assertFalse(validator.isValid(meld));
    }

    @Test
    void invalidRun_duplicateValues() {
        Meld meld = new Meld(MeldType.RUN, List.of(
                new Tile(Color.RED, 5),
                new Tile(Color.RED, 5),
                new Tile(Color.RED, 6)
        ));
        assertFalse(validator.isValid(meld));
    }

    @Test
    void invalidRun_tooShort() {
        Meld meld = new Meld(MeldType.RUN, List.of(
                new Tile(Color.RED, 5),
                new Tile(Color.RED, 6)
        ));
        assertFalse(validator.isValid(meld));
    }
}
