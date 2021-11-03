package com.techelevator.tenmo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterUserDTO {

    @NotEmpty
    @Size(min = 3, max = 50, message = "Username must be in between 3 and 50 characters.")
    private String username;
    @NotEmpty
    @Size(min = 6, max = 15, message = "Passwords must be in between 6 and 15 characters.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
