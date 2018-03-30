package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.SensorsManager.Sensors.SensorType;
import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO;
import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_ITEM;
import com.smartcity.smartHouse.dataModel.Storage.SM_SENSOR;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class GetScenarioResult implements Serializable {
    public String id;
    public String houseId;
    public String name;
    public List<GetScenarioItemResult> items;

    public GetScenarioResult(SM_SCENARIO scenario) {
        id = scenario.getId().toString();
        houseId = scenario.houseId;
        name = scenario.name;
        if (scenario.scenario_items != null) {
            items = scenario.scenario_items.stream()
                .filter(elt -> elt != null)
                .map(elt -> new GetScenarioItemResult(elt))
                .collect(Collectors.toList());
        }
    }
}
