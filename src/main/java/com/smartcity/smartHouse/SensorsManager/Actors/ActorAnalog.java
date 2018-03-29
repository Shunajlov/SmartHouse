package com.smartcity.smartHouse.SensorsManager.Actors;

public class ActorAnalog extends ActorBase {

    public ActorAnalog() {
        actorType = ActorType.ANALOG;
    }

    @Override
    public void setValue(Integer value) {
        if (value >= 0 && value <= 1024) {
            this.value = value;
        }
    }
}
