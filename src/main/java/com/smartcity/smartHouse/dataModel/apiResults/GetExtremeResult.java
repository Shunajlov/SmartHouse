package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_EXTREME;
import com.smartcity.smartHouse.dataModel.Storage.SM_HISTORY;
import com.smartcity.smartHouse.dataModel.Storage.SM_HOUSE;

import java.io.Serializable;

public class GetExtremeResult implements Serializable {
    private String houseId, sensorId, value;

    public GetExtremeResult(SM_EXTREME extreme) {
        houseId = extreme.houseId;
        sensorId = extreme.sensorId;
        value = extreme.value;
    }

    public String getHouseId() {
        return houseId;
    }
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
