package com.smartcity.smartHouse;

//import com.smartcity.smartHouse.dataModel.apiResults.AuthResult;
//import com.smartcity.smartHouse.dataModel.apiResults.AuthRoomerResult;

public class Const {
    public static final int PORT = 8080;

    public static final String TEST = "/api/test/:name";

//    public static HashMap<String, AuthResult> tokenUsersMap = new HashMap<>();
//    public static HashMap<String, AuthRoomerResult> tokenRoomersMap = new HashMap<>();

    /*
    Если токен не существует
    {
      "status" : 5,
      "text" : "Bad user token"
    }
     */

    public static final String AUTH = "/api/auth/:login/:password";

    public static final String INTEGRATOR_ADD = "/api/integratorAdd/:login/:password/:token";

    public static final String USERS_LIST = "/api/users/:houseId/:token";
    public static final String USER_ADD = "/api/userAdd/:login/:password/:houseId/:token";
    public static final String USER_DELETE = "/api/userDelete/:login/:token";

    public static final String USER_PLAN = "/api/userPlan/:userId/:token";
    public static final String USER_PLAN_EDIT = "/api/userPlanEdit/:userId/:plan/:token";

    public static final String HOUSES_LIST = "/api/houses/:token";
    public static final String HOUSE_ADD = "/api/houseAdd/:name/:token";
    public static final String HOUSE_DELETE = "/api/houseDelete/:houseId/:token";

    public static final String SENSORS_LIST = "/api/sensors/:houseId/:token";
    public static final String SENSOR = "/api/sensor/:sensorId/:token";
    public static final String SENSOR_ADD = "/api/sensorAdd/:houseId/:token/:fieldName/:sensorType/:extreme/:active";
    public static final String SENSOR_EDIT = "/api/sensorEdit/:sensorId/:token/:active/:extreme";
    public static final String SENSOR_DELETE = "/api/sensorDelete/:sensorId/:token";

    public static final String ACTORS_LIST = "/api/actors/:houseId/:token";
    public static final String ACTOR = "/api/actor/:actorId/:token";
    public static final String ACTOR_ADD = "/api/actorAdd/:houseId/:token/:fieldName/:actorType/:value";
    public static final String ACTOR_EDIT = "/api/actorEdit/:actorId/:token/:value";
    public static final String ACTOR_DELETE = "/api/actorDelete/:actorId/:token";

    public static final String SCENARIOS_LIST = "/api/scenarios/:houseId/:token";
    public static final String SCENARIO = "/api/scenario/:scenarioId/:token";
    public static final String SCENARIO_ADD = "/api/scenarioAdd/:houseId/:name/:token";
    public static final String SCENARIO_EDIT = "/api/scenarioEdit/:scenarioId/:name/:token";
    public static final String SCENARIO_ADD_ITEM = "/api/scenarioAddItem/:scenarioId/:sensorId/:sensorValue/:actorId/:actorValue/:token";
    public static final String SCENARIO_REMOVE_ITEM = "/api/scenarioRemoveItem/:scenarioId/:sensorId/:actorId/:token";
    public static final String SCENARIO_DELETE = "/api/scenarioDelete/:scenarioId/:token";


    public static final String HISTORY_LIST = "/api/historyList/:houseId/:token";

    public static final String EXTREME_LIST = "/api/extremeList/:houseId/:token";
}
