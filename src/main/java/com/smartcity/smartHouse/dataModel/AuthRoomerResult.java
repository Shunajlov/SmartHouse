package com.smartcity.smartHouse.dataModel;

import java.io.Serializable;

public class AuthRoomerResult extends AuthResult implements Serializable {
    private String houseId;

    public AuthRoomerResult(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
