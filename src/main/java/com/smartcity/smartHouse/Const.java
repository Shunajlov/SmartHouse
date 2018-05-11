package com.smartcity.smartHouse;

public class Const {
    public static final int PORT = 8080;

    public static final String PING = "/api/ping";

    public static final String SETUP_MONGODB = "/api/setupMongo/:endpoint/:dbName/:token";
    public static final String SETUP_INFLUX = "/api/setupInflux/:endpoint/:dbName/:username/:password/:token";

    public static final String AUTH = "/api/auth/:login/:password";

    public static final String INTEGRATORS_LIST = "/api/integrators/:token";
    public static final String INTEGRATOR_ADD = "/api/integratorAdd/:login/:password/:token";
    public static final String INTEGRATOR_DELETE = "/api/integratorDelete/:login/:token";

    public static final String USERS_LIST = "/api/users/:houseId/:token";
    public static final String USER_ADD = "/api/userAdd/:login/:password/:name/:type/:houseId/:token";
    public static final String USER_DELETE = "/api/userDelete/:login/:token";

    public static final String USER_PLAN = "/api/userPlan/:userId/:token";
    public static final String USER_PLAN_EDIT = "/api/userPlanEdit/:userId/:plan/:token";

    public static final String HOUSES_LIST = "/api/houses/:token";
    public static final String HOUSE_ADD = "/api/houseAdd/:name/:token";
    public static final String HOUSE_DELETE = "/api/houseDelete/:houseId/:token";

    public static final String SENSORS_LIST = "/api/sensors/:houseId/:token";
    public static final String SENSOR = "/api/sensor/:sensorId/:token";
    public static final String SENSOR_ADD = "/api/sensorAdd/:houseId/:token/:fieldName/:sensorType/:extreme";
    public static final String SENSOR_EDIT = "/api/sensorEdit/:sensorId/:token/:extreme";
    public static final String SENSOR_DELETE = "/api/sensorDelete/:sensorId/:token";
    public static final String SENSOR_NEW_VALUE = "/api/sensorNewValue/:sensorId/:token/:value";
    public static final String SENSOR_LAST_VALUE = "/api/sensorLastValue/:sensorId/:token";

    public static final String ACTORS_LIST = "/api/actors/:houseId/:token";
    public static final String ACTOR = "/api/actor/:actorId/:token";
    public static final String ACTOR_ADD = "/api/actorAdd/:houseId/:token/:fieldName/:actorType/:value";
    public static final String ACTOR_EDIT = "/api/actorEdit/:actorId/:token/:value";
    public static final String ACTOR_DELETE = "/api/actorDelete/:actorId/:token";
    public static final String ACTOR_NEW_VALUE = "/api/actorNewValue/:actorId/:token/:value";
    public static final String ACTOR_LAST_VALUE = "/api/actorLastValue/:actorId/:token";

    public static final String SCENARIOS_LIST = "/api/scenarios/:houseId/:token";
    public static final String SCENARIO = "/api/scenario/:scenarioId/:token";
    public static final String SCENARIO_ADD = "/api/scenarioAdd/:houseId/:name/:token";
    public static final String SCENARIO_EDIT = "/api/scenarioEdit/:scenarioId/:name/:token";
    public static final String SCENARIO_CONDITIONS = "/api/scenarioConditions/:scenarioId/:token";
    public static final String SCENARIO_ADD_CONDITION = "/api/scenarioAddCondition/:scenarioId/:sensorId/:sensorValue/:type/:token";
    public static final String SCENARIO_REMOVE_CONDITION = "/api/scenarioRemoveCondition/:scenarioId/:conditionId/:token";
    public static final String SCENARIO_ACTIONS = "/api/scenarioActions/:scenarioId/:token";
    public static final String SCENARIO_ADD_ACTION = "/api/scenarioAddAction/:scenarioId/:actorId/:actorValue/:token";
    public static final String SCENARIO_REMOVE_ACTION = "/api/scenarioRemoveAction/:scenarioId/:actionId/:token";
    public static final String SCENARIO_DELETE = "/api/scenarioDelete/:scenarioId/:token";

    public static final String HISTORY_LIST = "/api/historyList/:houseId/:token";

    public static final String EXTREME_LIST = "/api/extremeList/:houseId/:token";
}
