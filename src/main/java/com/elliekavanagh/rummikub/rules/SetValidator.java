package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.Tile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetValidator implements MeldValidator {

    @Override
    public boolean isValid(Meld meld) {
        if (meld == null || meld.getTiles() == null) return false;

        List<Tile> tiles = meld.getTiles();
        int n = tiles.size();

        // Sets are 3 or 4 tiles
        if (n < 3 || n > 4) return false;

        Integer targetValue = null;
        Set<Color> colorsSeen = new HashSet<>();

        int jokerCount = 0;

        for (Tile t : tiles) {
            if (t == null) return false;

            if (t.isJoker()) {
                jokerCount++;
                continue;
            }

            // Value consistency
            Integer v = t.getValue();
            if (v == null) return false;

            if (targetValue == null) targetValue = v;
            else if (!targetValue.equals(v)) return false;

            // Color uniqueness
            Color c = t.getColor();
            if (c == null) return false;

            if (colorsSeen.contains(c)) return false;
            colorsSeen.add(c);
        }

        // All jokers: ambiguous as a strict validator (you could choose a value),
        // but we’ll reject to keep rules deterministic.
        if (targetValue == null) return false;

        // If there are jokers, they can take any missing colors,
        // but the set cannot exceed 4 distinct colors total.
        // (We already limited n <= 4 and ensured non-joker colors are unique.)
        return colorsSeen.size() + jokerCount == n;
    }
}
