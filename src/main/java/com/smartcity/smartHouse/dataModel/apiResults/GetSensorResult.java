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
    public SensorType sensorType;

    public GetSensorResult(SM_SENSOR sensor) {
        this.id = sensor.getId().toString();
        this.houseId = sensor.houseId;
        this.measurment = sensor.measurment;
        this.fieldName = sensor.fieldName;
        this.active = sensor.active;
        this.value = sensor.value;
        this.sensorType = sensor.sensorType;
    }
}
