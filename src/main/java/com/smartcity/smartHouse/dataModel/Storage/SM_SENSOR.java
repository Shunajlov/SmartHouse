package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.SensorsManager.Sensors.SensorAnalog;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorBase;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorDiscrete;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorType;
import org.mongodb.morphia.annotations.Entity;

@Entity("SM_SENSOR")
public class SM_SENSOR extends BaseEntity {

    public String houseId;
    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название датчика в таблице
    public Boolean active = false;
    public Integer value;
    public SensorType sensorType;

    public SensorBase sensor() {
        if (sensorType == SensorType.ANALOG) {
            SensorAnalog sensor = new SensorAnalog();
            sensor.sensorId = id.toString();
            sensor.houseId = houseId;
            sensor.measurment = measurment;
            sensor.fieldName = fieldName;
            sensor.active = active;
            sensor.type = sensorType;
            sensor.value = value;
            return sensor;
        } else {
            SensorDiscrete sensor = new SensorDiscrete();
            sensor.sensorId = id.toString();
            sensor.houseId = houseId;
            sensor.measurment = measurment;
            sensor.fieldName = fieldName;
            sensor.active = this.active;
            sensor.type = sensorType;
            sensor.value = (value == 1) ? true : false;
            return sensor;
        }
    }
}
