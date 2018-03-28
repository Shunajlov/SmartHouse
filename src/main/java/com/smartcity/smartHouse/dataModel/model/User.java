package com.smartcity.smartHouse.dataModel.model;

import com.smartcity.smartHouse.dataModel.apiResults.AuthResult;

import java.util.UUID;

public class User {
    private String login, password, token;

    public User(String login) {
        this.login = login;
    }

    public User(String login, String password) {
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

    public static User fromAuthResult(AuthResult result) {
        User user = new User(result.getLogin(), result.getPassword());
        user.setToken(result.getToken());
        return user;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
