package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.SensorsManager.Sensors.SensorType;
import com.smartcity.smartHouse.dataModel.Storage.SM_SENSOR;

import java.io.Serializable;

public class GetSensorResult implements Serializable {
    public String id;
    public String houseId;
    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название датчика в таблице
    public Boolean active = false;
    public Integer value;
    public Integer extreme;
    public SensorType sensorType;

    public GetSensorResult(SM_SENSOR sensor) {
        id = sensor.getId().toString();
        houseId = sensor.houseId;
        measurment = sensor.measurment;
        fieldName = sensor.fieldName;
        active = sensor.active;
        value = sensor.value;
        extreme = sensor.extreme;
        sensorType = sensor.sensorType;
    }
}
