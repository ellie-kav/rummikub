package com.elliekavanagh.rummikub.rules;

import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.MeldType;

public class MeldRulesEngine {

    private final RunValidator runValidator = new RunValidator();
    private final SetValidator setValidator = new SetValidator();

    public boolean isValid(Meld meld) {
        if (meld == null || meld.getType() == null) return false;

        MeldType type = meld.getType();
        switch (type) {
            case RUN:
                return runValidator.isValid(meld);
            case SET:
                return setValidator.isValid(meld);
            default:
                return false;
        }
    }
}
