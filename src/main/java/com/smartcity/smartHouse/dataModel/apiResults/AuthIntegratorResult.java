package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_INTEGRATOR;
import com.smartcity.smartHouse.dataModel.Storage.SM_USER;

import java.io.Serializable;
import java.util.UUID;

public class AuthIntegratorResult implements Serializable {
    protected String login, password, token;
    protected boolean isIntegrator;

    public AuthIntegratorResult() {
        super();
    }

    public AuthIntegratorResult(SM_INTEGRATOR integrator) {
        login = integrator.login;
        password = integrator.password;
        token = integrator.token;
        isIntegrator = true;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public boolean getIsIntegrator() { return isIntegrator; }
    public void setIntegrator(boolean isIntegrator) { this.isIntegrator = isIntegrator; }
}
