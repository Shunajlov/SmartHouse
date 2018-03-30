package com.smartcity.smartHouse.SensorsManager;

import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorAnalog;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorBase;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorDiscrete;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorType;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorAnalog;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorBase;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorDiscrete;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorType;
import com.smartcity.smartHouse.dataModel.Storage.*;
import com.smartcity.smartHouse.db.MongoDbProvider;
import com.smartcity.smartHouse.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SensorsManager {

    public static List<ActorBase> actors = new ArrayList<ActorBase>();
    public static List<SensorBase> sensors = new ArrayList<SensorBase>();

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

    public static void startAllActiveSensors() {
        for (int i = 0; i < sensors.size(); i++) {
            SensorBase sensor = sensors.get(i);
            if (sensor.active) {
                sensors.get(i).start();
            }
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

        SM_HOUSE house = MongoDbProvider.getHouse("5abd49069433ee36debb84d4");

//        ДАТЧИКИ

//        Температура в гостиной

        SM_SENSOR tempGost = new SM_SENSOR();
        tempGost.houseId = house.getId().toString();
        tempGost.measurment = house.name;
        tempGost.fieldName = "Temperatura v gostinoy";
        tempGost.value = 21;
        tempGost.sensorType = SensorType.ANALOG;
        tempGost.extreme = 100;
        MongoDbProvider.saveSensor(tempGost);

//        Влажность в гостиной

        SM_SENSOR vlazhGost = new SM_SENSOR();
        vlazhGost.houseId = house.getId().toString();
        vlazhGost.measurment = house.name;
        vlazhGost.fieldName = "Vlazhnost v gostinoy";
        vlazhGost.value = 55;
        vlazhGost.extreme = 100;
        vlazhGost.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(vlazhGost);

//        Дым на кухне

        SM_SENSOR dimKuhn = new SM_SENSOR();
        dimKuhn.houseId = house.getId().toString();
        dimKuhn.measurment = house.name;
        dimKuhn.fieldName = "Dim na kuhne";
        dimKuhn.value = 0;
        dimKuhn.extreme = 2;
        dimKuhn.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(dimKuhn);

//        Входная дверь

        SM_SENSOR vhDver = new SM_SENSOR();
        vhDver.houseId = house.getId().toString();
        vhDver.measurment = house.name;
        vhDver.fieldName = "Vhodnaya dver";
        vhDver.value = 0;
        vhDver.extreme = 2;
        vhDver.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(vhDver);

//        Окно на кухне

        SM_SENSOR oknoKuhn = new SM_SENSOR();
        oknoKuhn.houseId = house.getId().toString();
        oknoKuhn.measurment = house.name;
        oknoKuhn.fieldName = "Okno na kuhne";
        oknoKuhn.value = 0;
        oknoKuhn.extreme = 2;
        oknoKuhn.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(oknoKuhn);

//        Розетка на кухне

        SM_SENSOR rozKuhn = new SM_SENSOR();
        rozKuhn.houseId = house.getId().toString();
        rozKuhn.measurment = house.name;
        rozKuhn.fieldName = "Rozetka na kuhne";
        rozKuhn.value = 0;
        rozKuhn.extreme = 2;
        rozKuhn.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(rozKuhn);

//        Выключатель света в гостиной

        SM_SENSOR svetGost = new SM_SENSOR();
        svetGost.houseId = house.getId().toString();
        svetGost.measurment = house.name;
        svetGost.fieldName = "Vikluchatel sveta v gostinoy";
        svetGost.value = 0;
        svetGost.extreme = 2;
        svetGost.sensorType = SensorType.DISCRETE;
        MongoDbProvider.saveSensor(svetGost);

//        Счетчик горячей воды

        SM_SENSOR waterWarm = new SM_SENSOR();
        waterWarm.houseId = house.getId().toString();
        waterWarm.measurment = house.name;
        waterWarm.fieldName = "Schetchik goryachei vodi";
        waterWarm.value = 305;
        waterWarm.extreme = 100;
        waterWarm.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(waterWarm);

//        Счетчик холодной воды

        SM_SENSOR waterCold = new SM_SENSOR();
        waterCold.houseId = house.getId().toString();
        waterCold.measurment = house.name;
        waterCold.fieldName = "Schetchik holodnoy vodi";
        waterCold.value = 150;
        waterCold.extreme = 100;
        waterCold.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(waterCold);

//        Датчик движения в прихожей

        SM_SENSOR dvizhPrih = new SM_SENSOR();
        dvizhPrih.houseId = house.getId().toString();
        dvizhPrih.measurment = house.name;
        dvizhPrih.fieldName = "Datchik dvijenia d prihojey";
        dvizhPrih.value = 30;
        dvizhPrih.extreme = 100;
        dvizhPrih.sensorType = SensorType.ANALOG;
        MongoDbProvider.saveSensor(dvizhPrih);

//        МЕХАНИЗМЫ

//        Кондиционер

        SM_ACTOR condition = new SM_ACTOR();
        condition.houseId = house.getId().toString();
        condition.measurment = house.name;
        condition.fieldName = "Condicioner";
        condition.value = 15;
        condition.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(condition);

//        Выключатель (розетка/свет)

        SM_ACTOR rozet = new SM_ACTOR();
        rozet.houseId = house.getId().toString();
        rozet.measurment = house.name;
        rozet.fieldName = "Vikluchatel";
        rozet.value = 0;
        rozet.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(rozet);

//        Регулятор яркости света

        SM_ACTOR yarkSvet = new SM_ACTOR();
        yarkSvet.houseId = house.getId().toString();
        yarkSvet.measurment = house.name;
        yarkSvet.fieldName = "Regulator yarkosti sveta";
        yarkSvet.value = 10;
        yarkSvet.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(yarkSvet);

//        Теплый пол в ванной (регулировка)

        SM_ACTOR tepPolVann = new SM_ACTOR();
        tepPolVann.houseId = house.getId().toString();
        tepPolVann.measurment = house.name;
        tepPolVann.fieldName = "Tepliy pol v vannoy";
        tepPolVann.value = 15;
        tepPolVann.actorType = ActorType.DISCRETE;
        MongoDbProvider.saveActor(tepPolVann);

//            Замок входной двери

        SM_ACTOR zamokVhod = new SM_ACTOR();
        zamokVhod.houseId = house.getId().toString();
        zamokVhod.measurment = house.name;
        zamokVhod.fieldName = "Zamok vhodnoy dveri";
        zamokVhod.value = 0;
        zamokVhod.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(zamokVhod);

//        Видеокамера у входной двери

        SM_ACTOR videoVhod = new SM_ACTOR();
        videoVhod.houseId = house.getId().toString();
        videoVhod.measurment = house.name;
        videoVhod.fieldName = "Videokamera y vhodnoy dveri";
        videoVhod.value = 0;
        videoVhod.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(videoVhod);

//        Вытяжка на кухне

        SM_ACTOR vitazhKuhn = new SM_ACTOR();
        vitazhKuhn.houseId = house.getId().toString();
        vitazhKuhn.measurment = house.name;
        vitazhKuhn.fieldName = "Vitazhka na kuhne";
        vitazhKuhn.value = 0;
        vitazhKuhn.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(vitazhKuhn);

//        Кран в ванной

        SM_ACTOR kranVann = new SM_ACTOR();
        kranVann.houseId = house.getId().toString();
        kranVann.measurment = house.name;
        kranVann.fieldName = "Kran v vannoy";
        kranVann.value = 0;
        kranVann.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(kranVann);

//        Сигнализация

        SM_ACTOR signal = new SM_ACTOR();
        signal.houseId = house.getId().toString();
        signal.measurment = house.name;
        signal.fieldName = "Signalizacia";
        signal.value = 0;
        signal.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(signal);

//        Пожарная тревога

        SM_ACTOR pozharTrev = new SM_ACTOR();
        pozharTrev.houseId = house.getId().toString();
        pozharTrev.measurment = house.name;
        pozharTrev.fieldName = "Pozharnaya trevoga";
        pozharTrev.value = 0;
        pozharTrev.actorType = ActorType.ANALOG;
        MongoDbProvider.saveActor(pozharTrev);

        getAndStartAllSensorsAndActors();
    }

    public static void setupDefaultUsers() {

        SM_USER kichkin = new SM_USER();
        kichkin.login = "kichkin";
        kichkin.password = "password";
        kichkin.name = "Кычкин Алексей Владимирович";
        kichkin.houseId = "5abd18279433ee27b68c5d11";
        kichkin.token = Utils.generateToken();
        kichkin.type = UserType.BASE;
        MongoDbProvider.saveUser(kichkin);

        SM_USER vikentieva = new SM_USER();
        vikentieva.login = "vikentieva";
        vikentieva.password = "password";
        vikentieva.name = "Викентьева Ольга Леонидовна";
        vikentieva.houseId = "5abd18279433ee27b68c5d11";
        vikentieva.token = Utils.generateToken();
        vikentieva.type = UserType.BASE;
        MongoDbProvider.saveUser(vikentieva);

        SM_USER lebedev = new SM_USER();
        lebedev.login = "lebedev";
        lebedev.password = "password";
        lebedev.name = "Лебедев Виктор Валерьевич";
        lebedev.houseId = "5abd18279433ee27b68c5d11";
        lebedev.token = Utils.generateToken();
        lebedev.type = UserType.BASE;
        MongoDbProvider.saveUser(lebedev);

        SM_USER korotun = new SM_USER();
        korotun.login = "korotun";
        korotun.password = "password";
        korotun.name = "Коротун Василиса Павловна";
        korotun.houseId = "5abd18279433ee27b68c5d11";
        korotun.token = Utils.generateToken();
        korotun.type = UserType.BASE;
        MongoDbProvider.saveUser(korotun);
    }

    public static void setupDefaultScenario() {

        SM_SCENARIO_ITEM item = new SM_SCENARIO_ITEM();
        item.actorId = "5abd49079433ee36debb84df";
        item.actorValue = 100;
        item.sensorId = "5abd49069433ee36debb84d5";
        item.sensorValue = 150;

        SM_SCENARIO_ITEM itemTwo = new SM_SCENARIO_ITEM();
        itemTwo.actorId = "5abd49079433ee36debb84df";
        itemTwo.actorValue = 100;
        itemTwo.sensorId = "5abd49069433ee36debb84d6";
        itemTwo.sensorValue = 70;

        SM_SCENARIO scenario = new SM_SCENARIO();
        scenario.name = "Сценарий 1";
        scenario.houseId = "5abd49069433ee36debb84d4";
        scenario.scenario_items = Arrays.asList(item, itemTwo);

        MongoDbProvider.saveScenario(scenario);
    }

    public static void getAndStartAllSensorsAndActors() {
        getActorsFromDb();
        getSensorsFromDb();

        startAllActors();
//        startAllSensors();
        startAllActiveSensors();
    }


}
