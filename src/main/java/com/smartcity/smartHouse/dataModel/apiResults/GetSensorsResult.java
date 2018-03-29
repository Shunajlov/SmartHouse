package com.smartcity.smartHouse.dataModel.apiResults;

import java.util.ArrayList;

public class GetSensorsResult {
    private ArrayList<GetSensorResult> sensors;

    public GetSensorsResult(ArrayList<GetSensorResult> sensors) {
        this.sensors = sensors;
    }

    public ArrayList<GetSensorResult> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<GetSensorResult> sensors) {
        this.sensors = sensors;
    }
}
