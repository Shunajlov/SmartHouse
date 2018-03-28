package com.smartcity.smartHouse.dataModel;

import java.io.Serializable;
import java.util.UUID;

public class AuthResult extends BasicAnswer implements Serializable {
    private String token, login, password;

    public AuthResult() {
        super();
    }

    public AuthResult(String login, String password) {
        this.login = login;
        token = generateToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
