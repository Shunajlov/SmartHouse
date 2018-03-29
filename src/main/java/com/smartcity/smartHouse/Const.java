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

    /**
     * Аваторизация пользователей
     {
     "status" : 0,
     "text" : "",
     "token" : "a99e712b-6603-48bc-9365-1cb43ecb0fb3",
     "login" : "topkek",
     "password" : null
     }
     */
    public static final String AUTH = "/api/auth/:login/:password";

    /**
     * Добавление жильца
     * :token - токен юзера (не жильца)
     {
     "status" : 0,
     "text" : "",
     "token" : null,
     "login" : "roomerLogin",
     "password" : "roomerPass",
     "houseId" : "1"
     }
     */
    public static final String USER_ADD = "/api/userAdd/:login/:password/:houseId/:token";

    public static final String USER_DELETE = "/api/userDelete/:login/:token";

    /**
     * Список жильцов в доме
     * :token - токен юзера (не жильца)
     {
     "status" : 0,
     "text" : "",
     "roomerList" : [ {
     "status" : 0,
     "text" : "",
     "token" : null,
     "login" : "login1",
     "password" : "pass1",
     "houseId" : "1"
     } ]
     }
     */
    public static final String USERS_LIST = "/api/users/:houseId/:token";

    public static final String HOUSES_LIST = "/api/houses/:token";

    public static final String SENSORS_LIST = "/api/sensors/:houseId/:token";
    public static final String SENSOR = "/api/sensor/:sensorId/:token";
    public static final String SENSOR_DELETE = "/api/sensorDelete/:sensorId/:token";

    public static final String ACTORS_LIST = "/api/actors/:houseId/:token";
    public static final String ACTOR = "/api/actor/:actorId/:token";
    public static final String ACTOR_DELETE = "/api/actorDelete/:actorId/:token";

    public static final String HISTORY_LIST = "/api/historyList/:houseId/:token";


}
