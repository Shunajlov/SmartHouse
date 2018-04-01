package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.Enums.SensorType;
import com.smartcity.smartHouse.dataModel.Storage.SM_SENSOR;

import java.io.Serializable;

public class GetSensorResult implements Serializable {
    public String id;
    public String houseId;
    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название датчика в таблице
    public Integer value;
    public Integer extreme;
    public SensorType sensorType;

    public GetSensorResult(SM_SENSOR sensor) {
        id = sensor.getId().toString();
        houseId = sensor.houseId;
        measurment = sensor.measurment;
        fieldName = sensor.fieldName;
        value = sensor.value;
        extreme = sensor.extreme;
        sensorType = sensor.sensorType;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMeasurment() {
        return measurment;
    }

    public void setMeasurment(String measurment) {
        this.measurment = measurment;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getExtreme() {
        return extreme;
    }

    public void setExtreme(Integer extreme) {
        this.extreme = extreme;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }
}
