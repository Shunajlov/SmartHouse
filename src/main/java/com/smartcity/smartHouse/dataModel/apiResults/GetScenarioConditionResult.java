package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_CONDITION;

import java.io.Serializable;

public class GetScenarioConditionResult implements Serializable {
    public String id;
    public String sensorId;
    public Integer sensorValue;

    public GetScenarioConditionResult(SM_SCENARIO_CONDITION condition) {
        id = condition.getId().toString();
        sensorId = condition.sensorId;
        sensorValue = condition.sensorValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(Integer sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
