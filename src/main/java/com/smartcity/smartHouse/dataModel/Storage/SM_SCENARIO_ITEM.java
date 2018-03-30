package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_SCENARIO_ITEM")
public class SM_SCENARIO_ITEM extends BaseEntity {

    public String sensorId;
    public Integer sensorValue;
    public String actorId;
    public Integer actorValue;
}
