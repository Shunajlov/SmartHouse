package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.ArrayList;

public class GetIntegratorsResult implements Serializable {
    private ArrayList<IntegratorResult> integrators;

    public GetIntegratorsResult(ArrayList<IntegratorResult> integrators) {
        this.integrators = integrators;
    }

    public ArrayList<IntegratorResult> getIntegrators() {
        return integrators;
    }

    public void setIntegrators(ArrayList<IntegratorResult> integrators) {
        this.integrators = integrators;
    }
}

