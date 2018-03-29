package com.smartcity.smartHouse;

import com.smartcity.smartHouse.db.MongoDbProvider;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

class Main {
    public static void main(String... args) {

        MongoDbProvider.setupDatastore();

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
