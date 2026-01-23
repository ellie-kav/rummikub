package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates RUN melds (same color, consecutive values) with optional jokers.
 *
 * Strategy:
 * - Collect and sort non-joker values (enforcing single color + no duplicates).
 * - Compute internal gaps (jokers must fill these first).
 * - Any remaining jokers may extend the run on either end, but the final run
 *   must remain within 1..13.
 *
 * Note: This validator treats an all-joker run as invalid.
 */
public class RunValidator implements MeldValidator {
    /**
    * Returns true if the meld forms a legal run of length >= 3.
    */
    @Override
    public boolean isValid(Meld meld) {
        if (meld == null || meld.getTiles() == null) return false;

        List<Tile> tiles = meld.getTiles();
        if (tiles.size() < 3) return false;

        int jokerCount = 0;
        Color runColor = null;

        List<Integer> values = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();

        for (Tile t : tiles) {
            if (t == null) return false;

            if (t.isJoker()) {
                jokerCount++;
                continue;
            }

            if (runColor == null) runColor = t.getColor();
            else if (t.getColor() != runColor) return false;

            Integer v = t.getValue();
            if (v == null) return false;

            if (seen.contains(v)) return false;
            seen.add(v);

            values.add(v);
        }

        // If all tiles are jokers, can’t determine a valid concrete run in a strict validator.
        if (values.isEmpty()) return false;

        values.sort(Comparator.naturalOrder());

        // Count gaps between sorted non-joker values
        int gapsNeeded = 0;
        for (int i = 1; i < values.size(); i++) {
            int prev = values.get(i - 1);
            int curr = values.get(i);

            int diff = curr - prev;
            if (diff == 0) return false;      
            if (diff == 1) continue;          
            gapsNeeded += (diff - 1);         
        }

        // Jokers must be enough to fill internal gaps
        if (jokerCount < gapsNeeded) return false;

        // Also ensure the run can fit within 1..13 once jokers are placed.
        // After filling internal gaps, remaining jokers can extend the run on either end.
        int min = values.get(0);
        int max = values.get(values.size() - 1);

        int remainingJokers = jokerCount - gapsNeeded;

        // max length extension allowed without crossing bounds
        int extendLeft = Math.min(remainingJokers, min - 1);
        int extendRight = Math.min(remainingJokers - extendLeft, 13 - max);

        int placed = extendLeft + extendRight;
        return placed == remainingJokers;
    }
}
