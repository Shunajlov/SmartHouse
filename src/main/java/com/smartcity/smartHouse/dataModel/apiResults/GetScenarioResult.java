package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class GetScenarioResult implements Serializable {
    public String id;
    public String houseId;
    public String name;
    public List<GetScenarioConditionResult> conditions;
    public List<GetScenarioActionResult> actions;

    public GetScenarioResult(SM_SCENARIO scenario) {
        id = scenario.getId().toString();
        houseId = scenario.houseId;
        name = scenario.name;
        if (scenario.conditions != null) {
            conditions = scenario.conditions.stream()
                .filter(elt -> elt != null)
                .map(elt -> new GetScenarioConditionResult(elt))
                .collect(Collectors.toList());
        }
        if (scenario.actions != null) {
            actions = scenario.actions.stream()
                .filter(elt -> elt != null)
                .map(elt -> new GetScenarioActionResult(elt))
                .collect(Collectors.toList());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public List<GetScenarioActionResult> getActions() {
        return actions;
    }

    public void setActions(List<GetScenarioActionResult> actions) {
        this.actions = actions;
    }

    public List<GetScenarioConditionResult> getConditions() {
        return conditions;
    }

    public void setConditions(List<GetScenarioConditionResult> conditions) {
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
