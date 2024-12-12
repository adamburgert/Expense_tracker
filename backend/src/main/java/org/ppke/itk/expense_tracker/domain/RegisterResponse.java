package org.ppke.itk.expense_tracker.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {
    private String message;
    private String token;
    private User user;

    public RegisterResponse(String message, String token, User user) {
        this.message = message;
        this.token = token;
        this.user = user;
    }

}