package com.smartcity.smartHouse;

public class Const {
    public static final int PORT = 8080;

    public static final String TEST = "/api/test/:name";

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
    public static final String ADD_USER = "/api/regRoomer/:login/:password/:houseId/:token";

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
    public static final String ROOMERS_LIST = "/api/roomersList/:houseId/:token";


}
