package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_SCENARIO_ACTION")
public class SM_SCENARIO_ACTION extends BaseEntity {
    public String scenarioId;
    public String actorId;
    public Integer actorValue;
}
