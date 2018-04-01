package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.dataModel.Storage.SM_INTEGRATOR;
import com.smartcity.smartHouse.dataModel.Storage.SM_USER;

import java.io.Serializable;
import java.util.UUID;

public class AuthIntegratorResult extends IntegratorResult implements Serializable {
    protected UserType userType;

    public AuthIntegratorResult() {
        super();
    }

    public AuthIntegratorResult(SM_INTEGRATOR integrator) {
        id = integrator.getId().toString();
        login = integrator.login;
        password = integrator.password;
        token = integrator.token;
        userType = UserType.INTEGRATOR;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
