package com.elliekavanagh.rummikub.api.dto;

/**
 * Represents the result of validating a meld submission.
 * 
 * Returned by the validation API to indicate whether the
 * provided tiles form a legal run or set according to Rummikub rules.
 */

public class MeldValidationResponse {
    /**
    * True if the submitted meld is valid; false otherwise.
    */
    private boolean valid;

    public MeldValidationResponse() {}

    public MeldValidationResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
}
