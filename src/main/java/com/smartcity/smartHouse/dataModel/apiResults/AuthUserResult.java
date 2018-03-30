package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_USER;

import java.io.Serializable;

public class AuthUserResult extends UserResult implements Serializable {
    protected boolean isIntegrator;

    public AuthUserResult() {
        super();
    }

    public AuthUserResult(SM_USER user) {
        id = user.getId().toString();
        login = user.login;
        password = user.password;
        name = user.name;
        token = user.token;
        houseId = user.houseId;
        type = user.type;
        isIntegrator = false;
    }

    public boolean getIsIntegrator() { return isIntegrator; }
    public void setIntegrator(boolean isIntegrator) { this.isIntegrator = isIntegrator; }
}
