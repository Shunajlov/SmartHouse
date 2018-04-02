package com.smartcity.smartHouse.ScenarioManager;

import com.mongodb.Mongo;
import com.smartcity.smartHouse.dataModel.Storage.*;
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
        List<SM_SCENARIO> scenarios = MongoDbProvider.getScenarioList(sensor.houseId);

        if (scenarios != null && !scenarios.isEmpty()) {
            for (SM_SCENARIO scenario: scenarios) {
                Boolean allConditionsSatisfied = true;
                List<SM_SCENARIO_CONDITION> conditions = MongoDbProvider.getScenarioConditions(scenario.getId().toString(), true);
                if (conditions != null && !conditions.isEmpty()) {
                    for (SM_SCENARIO_CONDITION condition: conditions) {
                        if (condition.sensorId.equals(sensor.getId().toString())) {
                            if (sensor.value >= condition.sensorValue) {
                                condition.satisfied = true;
                            } else {
                                condition.satisfied = false;
                                allConditionsSatisfied = false;
                            }
                        }
                    }
                    if (allConditionsSatisfied) {
                        executeScenarioActions(scenario);
                    }
                }
            }
        }
    }

    private static void executeScenarioActions(SM_SCENARIO scenario) {
        List<SM_SCENARIO_ACTION> actions = MongoDbProvider.getScenarioActions(scenario.getId().toString());

        if (actions != null) {
            for (SM_SCENARIO_ACTION action: actions) {
                SM_ACTOR actor = MongoDbProvider.getActor(action.actorId);
                if (actor != null) {
                    actor.value = action.actorValue;
                    InfluxProvider.writeToInfluxData(actor.measurment, actor.fieldName, actor.value);
                    MongoDbProvider.saveActor(actor);
                }
            }
            System.out.println("All actions executed");
        } else {
            System.out.println("No actions");
        }
    }

}
