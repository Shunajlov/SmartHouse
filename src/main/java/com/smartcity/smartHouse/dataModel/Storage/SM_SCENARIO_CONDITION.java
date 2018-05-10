package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.Enums.ConditionType;
import org.mongodb.morphia.annotations.Entity;

@Entity("SM_SCENARIO_CONDITION")
public class SM_SCENARIO_CONDITION extends BaseEntity {
    public String scenarioId;
    public String sensorId;
    public ConditionType type;
    public Integer sensorValue;
    public Boolean satisfied;
}
