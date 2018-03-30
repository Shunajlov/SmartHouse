package com.smartcity.smartHouse;

import com.smartcity.smartHouse.SensorsManager.SensorsManager;
import com.smartcity.smartHouse.dataModel.Storage.SM_EXTREME;
import com.smartcity.smartHouse.db.MongoDbProvider;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

class Main {
    public static void main(String... args) {

        MongoDbProvider.setupDatastore();

//        SensorsManager.setupDefaultUsers();
//        SensorsManager.setupDefaultSensorsAndActors();
//        SensorsManager.getAndStartAllSensorsAndActors();
//        SensorsManager.setupDefaultScenario();

        Vertx vertx = Vertx.vertx();
        Verticle mainVerticle = new MainVerticle();

        vertx.deployVerticle(mainVerticle, res -> {
            if (res.succeeded())
                System.out.println("SmartHouse service started on port " + Const.PORT);
            else
                res.cause().printStackTrace();
        });
    }
}
