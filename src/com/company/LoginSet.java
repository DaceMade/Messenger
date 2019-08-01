package com.company;

import java.io.Serializable;

public class LoginSet implements Serializable {

    private String login;
    private String password;

    public LoginSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
