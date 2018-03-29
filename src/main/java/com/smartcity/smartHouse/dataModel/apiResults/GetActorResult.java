package com.smartcity.smartHouse.dataModel.apiResults;

import com.smartcity.smartHouse.SensorsManager.Actors.ActorType;
import com.smartcity.smartHouse.dataModel.Storage.SM_ACTOR;

public class GetActorResult {
    public String id;
    public String houseId;
    public ActorType actorType;
    public Integer value;

    public GetActorResult(SM_ACTOR actor) {
        this.id = actor.getId().toString();
        this.houseId = actor.houseId;
        this.actorType = actor.actorType;
        this.value = actor.value;
    }
}
