package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.List;

public class GetScenarioListResult implements Serializable {
    public List<GetScenarioResult> scenarios;

    public GetScenarioListResult(List<GetScenarioResult> scenarios) {
        this.scenarios = scenarios;
    }
}
