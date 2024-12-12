package org.ppke.itk.expense_tracker.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private String message;
    private int status;

    public ErrorResponse(String message, int value) {
        this.message = message;
        this.status = status;
    }

}
