package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.dataModel.Storage.SM_USER;

import java.io.Serializable;

public class AuthUserResult extends UserResult implements Serializable {
    protected UserType userType;

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
        userType = UserType.USER;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
