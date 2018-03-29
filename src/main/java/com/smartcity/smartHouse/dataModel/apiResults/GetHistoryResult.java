package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_HISTORY;
import com.smartcity.smartHouse.dataModel.Storage.SM_HOUSE;

import java.io.Serializable;

public class GetHistoryResult implements Serializable {
    private String houseId, time, event;

    public GetHistoryResult(SM_HISTORY history) {
        houseId = history.houseId;
        time = history.time;
        event = history.eventString;
    }

    public String getHouseId() {
        return houseId;
    }
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getTime() { return time; }
    public void setTime(String id) { this.time = time; }

    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }
}
