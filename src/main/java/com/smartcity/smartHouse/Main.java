package com.smartcity.smartHouse;

import com.smartcity.smartHouse.ScenarioManager.ScenarioManager;
import com.smartcity.smartHouse.dataModel.Storage.SM_EXTREME;
import com.smartcity.smartHouse.dataModel.Storage.SM_SENSOR;
import com.smartcity.smartHouse.db.InfluxProvider;
import com.smartcity.smartHouse.db.MongoDbProvider;
import com.smartcity.smartHouse.utils.DefaultManager;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Main {
    public static void main(String... args) {

        MongoDbProvider.setupDatastore();
        InfluxProvider.initInflux();

        DefaultManager.setupDefaultUsers();
//        DefaultManager.setupDefaultSensorsAndActors();
//        DefaultManager.setupDefaultScenario();

        startVertx();

        startScenarioManager();
    }

    private static void startScenarioManager() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<SM_SENSOR> sensors = MongoDbProvider.getAllSensors(false);
                    if (sensors != null) {
                        ScenarioManager.sensors = sensors;
                        ScenarioManager.querySensors();
                    }
                } catch (Exception e) {
                    System.out.println("Timer Exception: probably Mongo:\n" +  e.getLocalizedMessage());
                }
            }
        }, 0, 1*5*1000);
    }

    private static void startVertx() {
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
