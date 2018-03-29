package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_USER;

import java.io.Serializable;

public class GetUserResult extends AuthResult implements Serializable {
    protected String login, id;

    public GetUserResult(SM_USER user) {
        this.login = user.login;
        this.password = user.password;
        this.token = user.token;
        this.id = user.getId().toString();
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
}
