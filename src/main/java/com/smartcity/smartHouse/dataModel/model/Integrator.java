package com.smartcity.smartHouse.dataModel.model;

import com.smartcity.smartHouse.dataModel.apiResults.AuthResult;

import java.util.UUID;

public class Integrator {
    private String login, password, token;

    public Integrator(String login) {
        this.login = login;
    }

    public Integrator(String login, String password) {
        this.login = login;
        this.password = password;
        this.token = generateToken();
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static Integrator fromAuthResult(AuthResult result) {
        Integrator user = new Integrator(result.getLogin(), result.getPassword());
        user.setToken(result.getToken());
        return user;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
