package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.Enums.SensorType;
import org.mongodb.morphia.annotations.Entity;

@Entity("SM_SENSOR")
public class SM_SENSOR extends BaseEntity {

    public String houseId;
    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название датчика в таблице
    public Integer value;
    public Integer extreme;
    public SensorType sensorType;
}
