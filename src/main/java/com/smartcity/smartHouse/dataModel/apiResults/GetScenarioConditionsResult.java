package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.List;

public class GetScenarioConditionsResult implements Serializable {
    private List<GetScenarioConditionResult> conditions;

    public GetScenarioConditionsResult(List<GetScenarioConditionResult> conditions) {
        this.conditions = conditions;
    }

    public List<GetScenarioConditionResult> getConditions() {
        return conditions;
    }

    public void setConditions(List<GetScenarioConditionResult> conditions) {
        this.conditions = conditions;
    }
}
