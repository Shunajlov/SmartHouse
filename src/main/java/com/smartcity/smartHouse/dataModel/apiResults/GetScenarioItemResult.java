package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_ITEM;
import java.io.Serializable;

public class GetScenarioItemResult implements Serializable {
    public String sensorId;
    public Integer sensorValue;
    public String actorId;
    public Integer actorValue;

    public GetScenarioItemResult(SM_SCENARIO_ITEM item) {
        sensorId = item.sensorId;
        sensorValue = item.sensorValue;
        actorId = item.actorId;
        actorValue = item.actorValue;
    }
}
