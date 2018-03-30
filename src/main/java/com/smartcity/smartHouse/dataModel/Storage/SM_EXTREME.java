package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_EXTREME")
public class SM_EXTREME extends BaseEntity {
    public String houseId;
    public String sensorId;
    public String value;
}
