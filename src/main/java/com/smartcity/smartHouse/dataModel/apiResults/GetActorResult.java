package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.SensorsManager.Actors.ActorType;
import com.smartcity.smartHouse.dataModel.Storage.SM_ACTOR;

import java.io.Serializable;

public class GetActorResult implements Serializable {
    public String id;
    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название актора в таблице
    public String houseId;
    public ActorType actorType;
    public Integer value;

    public GetActorResult(SM_ACTOR actor) {
        id = actor.getId().toString();
        measurment = actor.measurment;
        fieldName = actor.fieldName;
        houseId = actor.houseId;
        actorType = actor.actorType;
        value = actor.value;
    }
}
