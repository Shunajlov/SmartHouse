package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.UUID;

public class AuthResult implements Serializable {
    protected String token, login, password;
    protected boolean isIntegrator;

    public AuthResult() {
        super();
    }

    public AuthResult(String login, String password, boolean isIntegrator) {
        this.login = login;
        this.password = password;
        this.isIntegrator = isIntegrator;
        token = generateToken();
    }

    public boolean isIntegrator() {
        return isIntegrator;
    }

    public void setIntegrator(boolean integrator) {
        isIntegrator = integrator;
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
