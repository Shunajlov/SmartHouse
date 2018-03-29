package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;

public class AuthUserResult extends AuthResult implements Serializable {
    private String houseId;

    public AuthUserResult(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
