package com.smartcity.smartHouse.SensorsManager.Actors;

public class ActorBase {

    public String houseId = "0";
    public String actorId = "0";
    public Integer value = 0;
    public Boolean active = false;
    public ActorType actorType = ActorType.ANALOG;

    public void setValue(Integer value) { }

    public void start() {
        active = true;
    }

    public void stop() {
        active = false;
    }
}
