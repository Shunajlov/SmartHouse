package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_INTEGRATOR;

import java.io.Serializable;

public class IntegratorResult implements Serializable {
    protected String id, login, password, token;

    public IntegratorResult() {
        super();
    }

    public IntegratorResult(SM_INTEGRATOR integrator) {
        id = integrator.getId().toString();
        login = integrator.login;
        password = integrator.password;
        token = integrator.token;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
