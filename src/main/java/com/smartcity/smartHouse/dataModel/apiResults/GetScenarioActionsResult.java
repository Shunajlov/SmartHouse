package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.List;

public class GetScenarioActionsResult implements Serializable {
    private List<GetScenarioActionResult> actions;

    public GetScenarioActionsResult(List<GetScenarioActionResult> actions) {
        this.actions = actions;
    }

    public List<GetScenarioActionResult> getActions() {
        return actions;
    }

    public void setActions(List<GetScenarioActionResult> actions) {
        this.actions = actions;
    }
}
