package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_HOUSE;

import java.io.Serializable;

public class GetHouseRequest implements Serializable {
    private String id, name;

    public GetHouseRequest(SM_HOUSE house) {
        this.id = house.getId().toString();
        this.name = house.name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
