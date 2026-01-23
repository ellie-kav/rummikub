package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.Tile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates SET melds (same value, distinct colors) with optional jokers.
 *
 * Rules enforced:
 * - A set must contain 3 or 4 tiles.
 * - All non-joker tiles must share the same value.
 * - Non-joker tiles must all have distinct colors.
 * - Jokers may substitute for missing colors, but the total
 *   number of tiles may not exceed 4.
 *
 * Note: An all-joker set is rejected to keep validation deterministic.
 */
public class SetValidator implements MeldValidator {
    /**
    * Returns true if the meld forms a legal set.
    */
    @Override
    public boolean isValid(Meld meld) {
        if (meld == null || meld.getTiles() == null) return false;

        List<Tile> tiles = meld.getTiles();
        int n = tiles.size();

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

            Integer v = t.getValue();
            if (v == null) return false;

            if (targetValue == null) targetValue = v;
            else if (!targetValue.equals(v)) return false;

            Color c = t.getColor();
            if (c == null) return false;

            if (colorsSeen.contains(c)) return false;
            colorsSeen.add(c);
        }

        if (targetValue == null) return false;

        // If there are jokers, they can take any missing colors,
        // but the set cannot exceed 4 distinct colors total.
        return colorsSeen.size() + jokerCount == n;
    }
}
