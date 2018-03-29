package com.smartcity.smartHouse.SensorsManager.Actors;

public class ActorDiscrete extends ActorBase {

    public ActorDiscrete() {
        actorType = ActorType.DISCRETE;
    }

    @Override
    public void setValue(Integer value) {
        if (value == 0) {
            this.value = 0;
        } else if (value == 1) {
            this.value = 1;
        }
    }
}
