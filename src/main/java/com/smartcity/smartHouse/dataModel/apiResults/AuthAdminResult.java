package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.dataModel.Storage.SM_ADMIN;

import java.io.Serializable;

public class AuthAdminResult implements Serializable {
    protected String id, login, password, token;
    protected UserType userType;

    public AuthAdminResult() {
        super();
    }

    public AuthAdminResult(SM_ADMIN admin) {
        id = admin.getId().toString();
        login = admin.login;
        password = admin.password;
        token = admin.token;
        userType = UserType.ADMIN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
