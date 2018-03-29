package com.smartcity.smartHouse.db;

import com.smartcity.smartHouse.dataModel.Storage.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

public class MongoDbProvider {

    //    private static String user = "ADMIN";
//    private static String password = "PASS";
    private static String database = "admin";

    private static Datastore datastore;

    public MongoDbProvider() {
        setupDatastore();
    }

    public static void setupDatastore() {
//        MongoClientURI connectionString = new MongoClientURI("mongodb://" + user + ":" + password + "@159.65.120.1:27017/?authSource=" + database);
        MongoClientURI connectionString = new MongoClientURI("mongodb://159.65.120.1:27017/?authSource=" + database);
        MongoClient mongoClient = new MongoClient(connectionString);
        String dbName = "admin";
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.smartcity.smartHouse.dataModel.Storage");
        datastore = morphia.createDatastore(mongoClient, dbName);
    }

    // SM_INTEGRATOR

    public static SM_INTEGRATOR getIntegrator(String login, String password) {

        final List<SM_INTEGRATOR> integrators = datastore.createQuery(SM_INTEGRATOR.class)
            .field("login").equal(login)
            .field("password").equal(password)
            .asList();

        if (integrators.isEmpty()) {
            System.out.println("Such integrator not exist in database");
            return null;
        } else {
            SM_INTEGRATOR integrator = integrators.get(0);
            System.out.println("Such integrator exists in database");
            return integrator;
        }
    }

    public static SM_INTEGRATOR getIntegrator(String token) {

        final List<SM_INTEGRATOR> integrators = datastore.createQuery(SM_INTEGRATOR.class)
            .field("token").equal(token)
            .asList();

        if (integrators.isEmpty()) {
            System.out.println("Such integrator not exist in database");
            return null;
        } else {
            SM_INTEGRATOR integrator = integrators.get(0);
            System.out.println("Such integrator exists in database");
            return integrator;
        }
    }

    public static void saveIntegrator(SM_INTEGRATOR integrator) { datastore.save(integrator); }

    // SM_USER

    public static SM_USER getUser(String login, String password) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("login").equal(login)
            .field("password").equal(password)
            .asList();

        if (users.isEmpty()) {
            System.out.println("User with login: " + login +  " password: " + password + " not exist in database");
            return null;
        } else {
            SM_USER user = users.get(0);
            System.out.println("User with login: " + login +  " password: " + password + " exist in database");
            return user;
        }
    }

    public static List<SM_USER> getUsers(String houseId) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("houseId").equal(houseId)
            .asList();

        if (users.isEmpty()) {
            System.out.println("No users");
            return null;
        } else {
            System.out.println("Users count: " + users.size());
            return users;
        }
    }

    public static List<SM_USER> getUsersWithLogin(String login) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("login").equal(login)
            .asList();

        if (users.isEmpty()) {
            System.out.println("No users");
            return null;
        } else {
            System.out.println("Users count: " + users.size());
            return users;
        }
    }

    public static void deleteUser(String login) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("login").equal(login)
            .asList();

        if (users.isEmpty()) {
            System.out.println("No users");
        } else {
            SM_USER user = users.get(0);
            datastore.delete(user);
            System.out.println("User deleted: " + user.login);
        }
    }

    public static void saveUser(SM_USER user) { datastore.save(user); }

    // SM_HOUSE

    public static SM_HOUSE getHouse(String id) {

        final List<SM_HOUSE> houses = datastore.createQuery(SM_HOUSE.class)
            .field("id").equal(id)
            .asList();

        if (houses.isEmpty()) {
            System.out.println("User with id: " + id + " not exist in database");
            return null;
        } else {
            SM_HOUSE user = houses.get(0);
            System.out.println("User with id: " + id + " exist in database");
            return user;
        }
    }

    public static List<SM_HOUSE> getHouses() {

        final List<SM_HOUSE> houses = datastore.createQuery(SM_HOUSE.class)
            .asList();

        if (houses.isEmpty()) {
            System.out.println("No houses");
            return null;
        } else {
            System.out.println("Houses count: " + houses.size());
            return houses;
        }
    }

    public static void saveHouse(SM_HOUSE house) { datastore.save(house); }

//    // SM_ACTOR
//
//    public static SM_ACTOR getActor(String actorId) {
//
//        final List<SM_ACTOR> actors = datastore.createQuery(SM_ACTOR.class)
//            .field("actorId").equal(actorId)
//            .asList();
//
//        if (actors.isEmpty()) {
//            System.out.println("Actor with id: " + actorId.toString() +  " not exist in database");
//            return null;
//        } else {
//            SM_ACTOR actor = actors.get(0);
//            System.out.println("Actor with id: " + actorId.toString() + " exists in database");
//            return actor;
//        }
//    }
//
//    public static List<SM_ACTOR> getAllActors() {
//
//        final List<SM_ACTOR> actors = datastore.createQuery(SM_ACTOR.class)
//            .asList();
//
//        if (actors.isEmpty()) {
//            System.out.println("No actors");
//            return null;
//        } else {
//            System.out.println("Actors count" + actors.size());
//            return actors;
//        }
//    }
//
//    public static List<SM_ACTOR> getActors(String houseId) {
//
//        final List<SM_ACTOR> actors = datastore.createQuery(SM_ACTOR.class)
//            .field("houseId").equal(houseId)
//            .asList();
//
//        if (actors.isEmpty()) {
//            System.out.println("No actors");
//            return null;
//        } else {
//            System.out.println("Actors count" + actors.size());
//            return actors;
//        }
//    }
//
//    public static void saveActor(SM_ACTOR actor) {
//        datastore.save(actor);
//    }
//
//    // SM_HOUSE
//
//    public static SM_HOUSE getHouse(String houseId) {
//
//        final List<SM_HOUSE> houses = datastore.createQuery(SM_HOUSE.class)
//            .field("houseId").equal(houseId)
//            .asList();
//
//        if (houses.isEmpty()) {
//            System.out.println("House with id: " + houseId.toString() +  " not exist in database");
//            return null;
//        } else {
//            SM_HOUSE house = houses.get(0);
//            System.out.println("House with id: " + houseId.toString() + " exists in database");
//            return house;
//        }
//    }
//
//    public static void saveHouse(SM_HOUSE house) {
//        datastore.save(house);
//    }
//
//    // SM_SENSOR
//
//    static public SM_SENSOR getSensor(String sensorId) {
//
//        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
//            .field("sendorId").equal(sensorId)
//            .asList();
//
//        if (sensors.isEmpty()) {
//            System.out.println("Sensor with id: " + sensorId.toString() +  " not exist in database");
//            return null;
//        } else {
//            SM_SENSOR sensor = sensors.get(0);
//            System.out.println("Sensor with id: " + sensorId.toString() + " exists in database");
//            return sensor;
//        }
//    }
//
//    public static List<SM_SENSOR> getAllSensors() {
//
//        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
//            .asList();
//
//        if (sensors.isEmpty()) {
//            System.out.println("No sensors");
//            return null;
//        } else {
//            System.out.println("Sensors count" + sensors.size());
//            return sensors;
//        }
//    }
//
//    public static List<SM_SENSOR> getSensors(String houseId) {
//
//        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
//            .field("houseId").equal(houseId)
//            .asList();
//
//        if (sensors.isEmpty()) {
//            System.out.println("No sensors");
//            return null;
//        } else {
//            System.out.println("Sensors count" + sensors.size());
//            return sensors;
//        }
//    }

//    public static void saveSensor(SM_SENSOR sensor) {
//        datastore.save(sensor);
//    }
}
