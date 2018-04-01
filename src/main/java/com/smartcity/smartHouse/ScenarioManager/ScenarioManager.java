package com.smartcity.smartHouse.ScenarioManager;

import com.smartcity.smartHouse.dataModel.Storage.SM_ACTOR;
import com.smartcity.smartHouse.dataModel.Storage.SM_EXTREME;
import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_ITEM;
import com.smartcity.smartHouse.dataModel.Storage.SM_SENSOR;
import com.smartcity.smartHouse.db.InfluxProvider;
import com.smartcity.smartHouse.db.MongoDbProvider;

import java.util.List;

public class ScenarioManager {

    public static List<SM_SENSOR> sensors;

    public static void querySensors() {
        if (sensors != null) {
            for (SM_SENSOR sensor: sensors) {
                Double lastValue = InfluxProvider.querySensorData(sensor);

                if (lastValue != null) {
                    // check for extreme
                    if (lastValue >= Double.parseDouble(sensor.extreme.toString())) {
                        ScenarioManager.extremeSituation(sensor, lastValue);
                    }

                    // check for scenario
                    ScenarioManager.checkForScenarios(sensor);
                }
            }
        }
    }

    public static void extremeSituation(SM_SENSOR sensor, Double value) {
        SM_EXTREME extreme = new SM_EXTREME();
        extreme.houseId = sensor.houseId;
        extreme.sensorId = sensor.getId().toString();
        extreme.value = value.toString();
        MongoDbProvider.addExtremeForSensor(sensor.houseId, sensor.getId().toString(), value.toString());
        System.out.println("Extreme SITUATION, sensorId: " + sensor.getId().toString());
    }

    public static void checkForScenarios(SM_SENSOR sensor) {
        List<SM_SCENARIO_ITEM> items = MongoDbProvider.getScenarioItems(sensor.houseId, sensor.getId().toString());
        if (items != null & !items.isEmpty()) {
            for (SM_SCENARIO_ITEM item: items) {
                if (item.sensorValue <= sensor.value) {
                    executeScenarioItem(item);
                }
            }
        }
    }

    private static void executeScenarioItem(SM_SCENARIO_ITEM item) {
        SM_ACTOR actor = MongoDbProvider.getActor(item.actorId);
        actor.value = item.actorValue;
        MongoDbProvider.saveActor(actor);
        System.out.println("Scenario Executed");
    }

}
