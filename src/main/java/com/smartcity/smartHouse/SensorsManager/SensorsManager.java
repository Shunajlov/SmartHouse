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

    public static void setupDefaultSensorsAndActors() {

        SM_HOUSE house = new SM_HOUSE();
        house.name = "Дом 2";
        MongoDbProvider.saveHouse(house);

//        ДАТЧИКИ

//        Температура в гостиной

        SM_SENSOR tempGost = new SM_SENSOR();
        tempGost.houseId = house.getId().toString();
        tempGost.measurment = house.name;
        tempGost.fieldName = "Температура в гостинной";
        tempGost.value = 21;
        tempGost.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(tempGost);

//        Влажность в гостиной

        SM_SENSOR vlazhGost = new SM_SENSOR();
        vlazhGost.houseId = house.getId().toString();
        vlazhGost.measurment = house.name;
        vlazhGost.fieldName = "Влажность в гостинной";
        vlazhGost.value = 55;
        vlazhGost.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(vlazhGost);

//        Дым на кухне

        SM_SENSOR dimKuhn = new SM_SENSOR();
        dimKuhn.houseId = house.getId().toString();
        dimKuhn.measurment = house.name;
        dimKuhn.fieldName = "Дым на кухне";
        dimKuhn.value = 0;
        dimKuhn.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(dimKuhn);

//        Входная дверь

        SM_SENSOR vhDver = new SM_SENSOR();
        vhDver.houseId = house.getId().toString();
        vhDver.measurment = house.name;
        vhDver.fieldName = "Входная дверь";
        vhDver.value = 0;
        vhDver.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(vhDver);

//        Окно на кухне

        SM_SENSOR oknoKuhn = new SM_SENSOR();
        oknoKuhn.houseId = house.getId().toString();
        oknoKuhn.measurment = house.name;
        oknoKuhn.fieldName = "Окно на кухне";
        oknoKuhn.value = 0;
        oknoKuhn.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(oknoKuhn);

//        Розетка на кухне

        SM_SENSOR rozKuhn = new SM_SENSOR();
        rozKuhn.houseId = house.getId().toString();
        rozKuhn.measurment = house.name;
        rozKuhn.fieldName = "Розетка на кухне";
        rozKuhn.value = 0;
        rozKuhn.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(rozKuhn);

//        Выключатель света в гостиной

        SM_SENSOR svetGost = new SM_SENSOR();
        svetGost.houseId = house.getId().toString();
        svetGost.measurment = house.name;
        svetGost.fieldName = "Выключатель света в гостиной";
        svetGost.value = 0;
        svetGost.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(svetGost);

//        Счетчик горячей воды

        SM_SENSOR waterWarm = new SM_SENSOR();
        waterWarm.houseId = house.getId().toString();
        waterWarm.measurment = house.name;
        waterWarm.fieldName = "Счетчик горячей воды";
        waterWarm.value = 305;
        waterWarm.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(waterWarm);

//        Счетчик холодной воды

        SM_SENSOR waterCold = new SM_SENSOR();
        waterCold.houseId = house.getId().toString();
        waterCold.measurment = house.name;
        waterCold.fieldName = "Счетчик холодной воды";
        waterCold.value = 150;
        waterCold.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(waterCold);

//        Датчик движения в прихожей

        SM_SENSOR dvizhPrih = new SM_SENSOR();
        dvizhPrih.houseId = house.getId().toString();
        dvizhPrih.measurment = house.name;
        dvizhPrih.fieldName = "Датчик движения в прихожей";
        dvizhPrih.value = 30;
        dvizhPrih.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(dvizhPrih);

//        МЕХАНИЗМЫ

//        Кондиционер

        SM_ACTOR condition = new SM_ACTOR();
        condition.houseId = house.getId().toString();
        condition.measurment = house.name;
        condition.fieldName = "Кондиционер";
        condition.value = 15;
        condition.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(condition);

//        Выключатель (розетка/свет)

        SM_ACTOR rozet = new SM_ACTOR();
        rozet.houseId = house.getId().toString();
        rozet.measurment = house.name;
        rozet.fieldName = "Выключатель";
        rozet.value = 0;
        rozet.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(rozet);

//        Регулятор яркости света

        SM_ACTOR yarkSvet = new SM_ACTOR();
        yarkSvet.houseId = house.getId().toString();
        yarkSvet.measurment = house.name;
        yarkSvet.fieldName = "Регулятор яркости света";
        yarkSvet.value = 10;
        yarkSvet.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(yarkSvet);

//        Теплый пол в ванной (регулировка)

        SM_ACTOR tepPolVann = new SM_ACTOR();
        tepPolVann.houseId = house.getId().toString();
        tepPolVann.measurment = house.name;
        tepPolVann.fieldName = "Теплый пол в ванной";
        tepPolVann.value = 15;
        tepPolVann.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(tepPolVann);

//            Замок входной двери

        SM_ACTOR zamokVhod = new SM_ACTOR();
        zamokVhod.houseId = house.getId().toString();
        zamokVhod.measurment = house.name;
        zamokVhod.fieldName = "Замок входной двери";
        zamokVhod.value = 0;
        zamokVhod.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(zamokVhod);

//        Видеокамера у входной двери

        SM_ACTOR videoVhod = new SM_ACTOR();
        videoVhod.houseId = house.getId().toString();
        videoVhod.measurment = house.name;
        videoVhod.fieldName = "Видеокамера у входной двери";
        videoVhod.value = 0;
        videoVhod.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(videoVhod);

//        Вытяжка на кухне

        SM_ACTOR vitazhKuhn = new SM_ACTOR();
        vitazhKuhn.houseId = house.getId().toString();
        vitazhKuhn.measurment = house.name;
        vitazhKuhn.fieldName = "Вытяжка на кухне";
        vitazhKuhn.value = 0;
        vitazhKuhn.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(vitazhKuhn);

//        Кран в ванной

        SM_ACTOR kranVann = new SM_ACTOR();
        kranVann.houseId = house.getId().toString();
        kranVann.measurment = house.name;
        kranVann.fieldName = "Кран в ванной";
        kranVann.value = 0;
        kranVann.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(kranVann);

//        Сигнализация

        SM_ACTOR signal = new SM_ACTOR();
        signal.houseId = house.getId().toString();
        signal.measurment = house.name;
        signal.fieldName = "Сигнализация";
        signal.value = 0;
        signal.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(signal);

//        Пожарная тревога

        SM_ACTOR pozharTrev = new SM_ACTOR();
        pozharTrev.houseId = house.getId().toString();
        pozharTrev.measurment = house.name;
        pozharTrev.fieldName = "Пожарная тревога";
        pozharTrev.value = 0;
        pozharTrev.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(pozharTrev);

        getAndStartAllSensorsAndActors();
    }

    public static void getAndStartAllSensorsAndActors() {
        getActorsFromDb();
        getSensorsFromDb();

        startAllActors();
        startAllSensors();
    }


}
