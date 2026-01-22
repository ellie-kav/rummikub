package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RunValidator implements MeldValidator {

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

            // Color consistency
            if (runColor == null) runColor = t.getColor();
            else if (t.getColor() != runColor) return false;

            Integer v = t.getValue();
            if (v == null) return false;

            // No duplicates among non-jokers
            if (seen.contains(v)) return false;
            seen.add(v);

            values.add(v);
        }

        // If all tiles are jokers, we can’t determine a valid concrete run in a strict validator
        // (and in real game you can place jokers but they represent specific values)
        if (values.isEmpty()) return false;

        values.sort(Comparator.naturalOrder());

        // Count gaps between sorted non-joker values
        int gapsNeeded = 0;
        for (int i = 1; i < values.size(); i++) {
            int prev = values.get(i - 1);
            int curr = values.get(i);

            int diff = curr - prev;
            if (diff == 0) return false;      // already handled, but safe
            if (diff == 1) continue;          // consecutive
            gapsNeeded += (diff - 1);         // jokers required to fill missing numbers
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

        // If we can place all remaining jokers within bounds, it’s valid.
        // (We can distribute them left then right; if still leftover, it would exceed bounds.)
        int placed = extendLeft + extendRight;
        return placed == remainingJokers;
    }
}
