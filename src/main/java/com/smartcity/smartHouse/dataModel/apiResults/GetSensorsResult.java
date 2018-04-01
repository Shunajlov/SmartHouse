package com.smartcity.smartHouse.dataModel.apiResults;

import java.io.Serializable;
import java.util.ArrayList;

public class GetSensorsResult implements Serializable {
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
