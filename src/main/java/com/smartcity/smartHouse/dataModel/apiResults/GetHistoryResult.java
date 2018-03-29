package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_HISTORY;
import com.smartcity.smartHouse.dataModel.Storage.SM_HOUSE;

import java.io.Serializable;

public class GetHistoryResult implements Serializable {
    private String houseId, time, eventString;

    public GetHistoryResult(SM_HISTORY history) {
        houseId = history.houseId;
        time = history.time;
        eventString = history.eventString;
    }

    public String getHouseId() {
        return houseId;
    }
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getTime() { return time; }
    public void setTime(String id) { this.time = time; }

    public String getEventString() { return eventString; }
    public void setEventString(String event) { this.eventString = event; }
}
