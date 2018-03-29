package com.smartcity.smartHouse.db;

//import com.smartcity.smartHouse.dataModel.model.TestClass;
//import com.smartcity.smartHouse.dataModel.model.Integrator;
//import io.vertx.core.Vertx;
//import io.vertx.core.json.Json;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.mongo.MongoClient;
//
//public class MongoProvider {
//    private static MongoClient client;
//
//    private static void initDb(Vertx vertx) {
//        JsonObject config = new JsonObject();
//        config.put("host", "159.65.120.1");
//        config.put("port", 27017);
//        config.put("db_name", "admin");
//
//        client = MongoClient.createShared(vertx, config);
//    }
//
//    // TestData
//
//    public static void writeTestData(Vertx vertx) {
//        if (client == null) initDb(vertx);
//
//        JsonObject test = JsonObject.mapFrom(new TestClass("wow", "title"));
//        client.save("testTable", test, result -> {
//            if (result.succeeded())
//                System.out.println("wow some data inserted");
//        });
//    }
//
//    // Integrator
//
//    public static void writeIntegrator(Vertx vertx, Integrator user) {
//        if (client == null) initDb(vertx);
//
//        JsonObject user1 = JsonObject.mapFrom(user);
//        client.save(Table.SM_INTEGRATOR, user1, result -> {
//            if (result.succeeded())
//                System.out.println("wow some data inserted");
//        });
//    }
//
//    public static Integrator getIntegrator(Vertx vertx, String id) {
//        if (client == null) initDb(vertx);
//
//        JsonObject query = new JsonObject()
//            .put("id", id);
//        client.find(Table.SM_INTEGRATOR, query, res -> {
//            if (res.succeeded()) {
//                for (JsonObject json : res.result()) {
//                    System.out.println(json.encodePrettily());
//                    Integrator integrator = Json.decodeValue(json.encodePrettily(), Integrator.class);
//                }
//            } else {
//                res.cause().printStackTrace();
//            }
//        });
//
//        return null;
//    }
//
//    // User
//
//    public static void writeUser(Vertx vertx) {
//
//    }
//
//    public static MongoClient getClient() {
//        return client;
//    }
//}
