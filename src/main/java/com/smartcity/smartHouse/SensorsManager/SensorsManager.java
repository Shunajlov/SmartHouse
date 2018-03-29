package com.smartcity.smartHouse.SensorsManager;

import com.smartcity.smartHouse.SensorsManager.Actors.ActorAnalog;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorBase;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorDiscrete;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorType;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorAnalog;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorBase;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorDiscrete;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorType;
import com.smartcity.smartHouse.dataModel.Storage.SM_ACTOR;
import com.smartcity.smartHouse.dataModel.Storage.SM_HOUSE;
import com.smartcity.smartHouse.dataModel.Storage.SM_SENSOR;
import com.smartcity.smartHouse.db.MongoDbProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SensorsManager {

    private static List<ActorBase> actors = new ArrayList<ActorBase>();
    private static List<SensorBase> sensors = new ArrayList<SensorBase>();

    // ACTORS

    public static void startAllActors() {
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).start();
        }
        System.out.println("All sensors started");
    }

    public static void stopAllActors() {
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).stop();
        }
        System.out.println("All sensors stopped");
    }

    public static void startActor(String actorId) {
        for (int i = 0; i < actors.size(); i++) {
            ActorBase actor = actors.get(i);
            if (actor.actorId == actorId) {
                actor.start();
                System.out.println("Actor " + actor.actorId.toString() + " started");
                return;
            }
        }
    }

    public static void stopActor(String actorId) {
        for (int i = 0; i < actors.size(); i++) {
            ActorBase actor = actors.get(i);
            if (actor.actorId == actorId) {
                actor.stop();
                System.out.println("Actor " + actor.actorId.toString() + " stopped");
                return;
            }
        }
    }

    public static void setValueForActor(String actorId, Integer value) {
        SM_ACTOR mongoActor = MongoDbProvider.getActor(actorId);
        if (mongoActor != null) {
            ActorBase actor = mongoActor.actor();
            if (actor != null) {
                mongoActor.value = value;
                actor.setValue(value);
                MongoDbProvider.saveActor(mongoActor);
            }
        }
    }

    public static void addActor(SM_ACTOR actor) {
        MongoDbProvider.saveActor(actor);
        System.out.println("Actor added, id: " + actor.getId().toString());
        getActorsFromDb();
    }

    public static void getActorsFromDb() {
        List<SM_ACTOR> mongoActors = MongoDbProvider.getAllActors();

        if (mongoActors != null) {
            actors = mongoActors.stream()
                .filter(elt -> elt != null)
                .map(elt -> elt.actor())
                .collect(Collectors.toList());;
        }
    }

    // SENSORS

    public static void startAllSensors() {
        for (int i = 0; i < sensors.size(); i++) {
            sensors.get(i).start();
        }
        System.out.println("All sensors started");
    }

    public static void stopAllSensors() {
        for (int i = 0; i < sensors.size(); i++) {
            sensors.get(i).stop();
        }
        System.out.println("All sensors stopped");
    }

    public static void startSensor(String sensorId) {
        for (int i = 0; i < sensors.size(); i++) {
            SensorBase sensor = sensors.get(i);
            if (sensor.sensorId == sensorId) {
                sensor.start();
                System.out.println("Sensor " + sensor.sensorId.toString() + " started");
                return;
            }
        }
    }

    public static void stopSensor(String sensorId) {
        for (int i = 0; i < sensors.size(); i++) {
            SensorBase sensor = sensors.get(i);
            if (sensor.sensorId == sensorId) {
                sensor.stop();
                System.out.println("Sensor " + sensor.sensorId.toString() + " stopped");
                return;
            }
        }
    }

    public static void setValueForSensor(String sensorId, Integer value) {
        SM_SENSOR mongoSensor = MongoDbProvider.getSensor(sensorId);
        if (mongoSensor != null) {
            mongoSensor.value = value;
            MongoDbProvider.saveSensor(mongoSensor);
            getSensorsFromDb();
        }
    }

    public static void addSensor(SM_SENSOR sensor) {
        MongoDbProvider.saveSensor(sensor);
        System.out.println("Sensor added, id: " + sensor.getId().toString());
        getSensorsFromDb();
    }

    public static void getSensorsFromDb() {
        List<SM_SENSOR> mongoSensors = MongoDbProvider.getAllSensors();

        if (mongoSensors != null) {
            sensors = mongoSensors.stream()
                .filter(elt -> elt != null)
                .map(elt -> elt.sensor())
                .collect(Collectors.toList());
        }
    }

    public static void setTestingScenario() {

        SM_HOUSE house = new SM_HOUSE();
        house.name = "дом 1";
        MongoDbProvider.saveHouse(house);

        SM_SENSOR sensorAnalog = new SM_SENSOR();
        sensorAnalog.measurment = house.name;
        sensorAnalog.fieldName = "Entrance Room 1";
        sensorAnalog.value = 30;
        sensorAnalog.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(sensorAnalog);

        SM_ACTOR actorAnalog = new SM_ACTOR();
        actorAnalog.houseId = house.getId().toString();
        actorAnalog.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(actorAnalog);

        SM_SENSOR sensorDiscrete = new SM_SENSOR();
        sensorDiscrete.measurment = house.name;
        sensorDiscrete.fieldName = "Temperature Room 1";
        sensorDiscrete.value = 0;
        sensorDiscrete.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(sensorDiscrete);

        SM_ACTOR actorDiscrete = new SM_ACTOR();
        actorDiscrete.houseId = house.getId().toString();
        actorDiscrete.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(actorDiscrete);

        getActorsFromDb();
        getSensorsFromDb();

        startAllActors();
        startAllSensors();
    }


}
