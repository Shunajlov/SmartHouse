package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.SensorsManager.Actors.ActorAnalog;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorBase;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorDiscrete;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorType;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_ACTOR")
public class SM_ACTOR extends BaseEntity {

    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название актора в таблице
    public String houseId;
    public ActorType actorType;
    public Integer value;

    public ActorBase actor() {
        if (actorType == ActorType.ANALOG) {
            ActorAnalog actor = new ActorAnalog();
            actor.actorId = id.toString();
            actor.measurment = measurment;
            actor.fieldName = fieldName;
            actor.houseId = houseId;
            actor.value = value;
            actor.actorType = actorType;
            return actor;
        } else {
            ActorDiscrete actor = new ActorDiscrete();
            actor.actorId = id.toString();
            actor.measurment = measurment;
            actor.fieldName = fieldName;
            actor.houseId = houseId;
            actor.value = value;
            actor.actorType = actorType;
            return actor;
        }
    }
}
