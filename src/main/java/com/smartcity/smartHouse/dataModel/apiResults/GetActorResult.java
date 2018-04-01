package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.Enums.ActorType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public void setActorType(ActorType actorType) {
        this.actorType = actorType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getMeasurment() {
        return measurment;
    }

    public void setMeasurment(String measurment) {
        this.measurment = measurment;
    }
}
