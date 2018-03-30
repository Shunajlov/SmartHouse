package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.dataModel.Storage.SM_USER;

import java.io.Serializable;

public class UserResult implements Serializable {
    protected String id, login, password, name, token, houseId;
    protected UserType type;

    public UserResult() {
        super();
    }

    public UserResult(SM_USER user) {
        id = user.getId().toString();
        login = user.login;
        password = user.password;
        name = user.name;
        token = user.token;
        houseId = user.houseId;
        type = user.type;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getHouseId() { return houseId; }
    public void setHouseId(String houseId) { this.houseId = houseId; }

    public UserType getType() { return type; }
    public void setType(UserType type) { this.type = type; }
}
