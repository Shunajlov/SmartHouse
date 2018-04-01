package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_ACTION;

import java.io.Serializable;

public class GetScenarioActionResult implements Serializable {
    public String id;
    public String actorId;
    public Integer actorValue;

    public GetScenarioActionResult(SM_SCENARIO_ACTION action) {
        id = action.getId().toString();
        actorId = action.actorId;
        actorValue = action.actorValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getActorValue() {
        return actorValue;
    }

    public void setActorValue(Integer actorValue) {
        this.actorValue = actorValue;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }
}
