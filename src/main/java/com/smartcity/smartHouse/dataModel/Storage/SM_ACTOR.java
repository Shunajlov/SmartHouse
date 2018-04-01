package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.Enums.ActorType;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_ACTOR")
public class SM_ACTOR extends BaseEntity {

    public String measurment;      // Идентификатор дома (таблицы)
    public String fieldName;       // Название актора в таблице
    public String houseId;
    public ActorType actorType;
    public Integer value;
}
