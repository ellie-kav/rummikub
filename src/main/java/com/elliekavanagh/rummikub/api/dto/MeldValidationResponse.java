package com.elliekavanagh.rummikub.api.dto;

public class MeldValidationResponse {
    private boolean valid;

    public MeldValidationResponse() {}

    public MeldValidationResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
}
