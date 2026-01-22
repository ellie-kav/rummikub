package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.MeldType;

/**
 * Dispatches validation logic based on MeldType.
 * Acts as a simple rules engine for Rummikub melds.
 */
public class MeldRulesEngine {

    private final RunValidator runValidator = new RunValidator();
    private final SetValidator setValidator = new SetValidator();

    public boolean isValid(Meld meld) {
        if (meld == null || meld.getType() == null) {
            return false;
        }

        switch (meld.getType()) {
            case RUN:
                return runValidator.isValid(meld);
            case SET:
                return setValidator.isValid(meld);
            default:
                return false;
        }
    }
}
