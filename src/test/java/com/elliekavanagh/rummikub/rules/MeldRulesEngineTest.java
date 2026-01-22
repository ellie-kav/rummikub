package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.MeldType;
import com.elliekavanagh.rummikub.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeldRulesEngineTest {

    private final MeldRulesEngine engine = new MeldRulesEngine();

    @Test
    void validatesRunViaEngine() {
        Meld run = new Meld(MeldType.RUN, List.of(
                new Tile(Color.RED, 1),
                new Tile(Color.RED, 2),
                new Tile(Color.RED, 3)
        ));
        assertTrue(engine.isValid(run));
    }

    @Test
    void validatesSetViaEngine() {
        Meld set = new Meld(MeldType.SET, List.of(
                new Tile(Color.RED, 7),
                new Tile(Color.BLUE, 7),
                new Tile(Color.YELLOW, 7)
        ));
        assertTrue(engine.isValid(set));
    }
}
