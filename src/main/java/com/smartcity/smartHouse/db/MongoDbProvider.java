package com.smartcity.smartHouse.db;

import com.smartcity.smartHouse.dataModel.Storage.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.types.ObjectId;
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

    // SM_ADMIN

    public static SM_ADMIN getAdmin(String login, String password) {

        final List<SM_ADMIN> admins = datastore.createQuery(SM_ADMIN.class)
            .field("login").equal(login)
            .field("password").equal(password)
            .asList();

        if (admins == null || admins.isEmpty()) {
            System.out.println("Admin with login: " + login +  " password: " + password + " not exist in database");
            return null;
        } else {
            SM_ADMIN admin = admins.get(0);
            System.out.println("Admin with login: " + login +  " password: " + password + " exists in database");
            return admin;
        }
    }

    public static SM_ADMIN getAdmin(String token) {

        final List<SM_ADMIN> admins = datastore.createQuery(SM_ADMIN.class)
            .field("token").equal(token)
            .asList();

        if (admins == null || admins.isEmpty()) {
            System.out.println("Integrator with token: " + token + " not exist in database");
            return null;
        } else {
            SM_ADMIN admin = admins.get(0);
            System.out.println("Integrator with token: " + token + " exists in database");
            return admin;
        }
    }

    public static void saveAdmin(SM_ADMIN admin) { datastore.save(admin); }

    // SM_INTEGRATOR

    public static SM_INTEGRATOR getIntegrator(String login, String password) {

        final List<SM_INTEGRATOR> integrators = datastore.createQuery(SM_INTEGRATOR.class)
            .field("login").equal(login)
            .field("password").equal(password)
            .asList();

        if (integrators == null || integrators.isEmpty()) {
            System.out.println("Integrator with login: " + login +  " password: " + password + " not exist in database");
            return null;
        } else {
            SM_INTEGRATOR integrator = integrators.get(0);
            System.out.println("Integrator with login: " + login +  " password: " + password + " exists in database");
            return integrator;
        }
    }

    public static SM_INTEGRATOR getIntegrator(String token) {

        final List<SM_INTEGRATOR> integrators = datastore.createQuery(SM_INTEGRATOR.class)
            .field("token").equal(token)
            .asList();

        if (integrators == null || integrators.isEmpty()) {
            System.out.println("Integrator with token: " + token + " not exist in database");
            return null;
        } else {
            SM_INTEGRATOR integrator = integrators.get(0);
            System.out.println("Integrator with token: " + token + " exists in database");
            return integrator;
        }
    }

    public static SM_INTEGRATOR getIntegratorWithLogin(String login) {

        final List<SM_INTEGRATOR> integrators = datastore.createQuery(SM_INTEGRATOR.class)
            .field("login").equal(login)
            .asList();

        if (integrators == null || integrators.isEmpty()) {
            System.out.println("No users");
            return null;
        } else {
            SM_INTEGRATOR integrator = integrators.get(0);
            System.out.println("Users count: " + integrators.size());
            return integrator;
        }
    }

    public static void saveIntegrator(SM_INTEGRATOR integrator) { datastore.save(integrator); }

    // SM_USER

    public static SM_USER getUserWithId(String id) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("id").equal(new ObjectId(id))
            .asList();

        if (users == null || users.isEmpty()) {
            System.out.println("User with id: " + id + " not exist in database");
            return null;
        } else {
            SM_USER user = users.get(0);
            System.out.println("User with id: " + id + " exist in database");
            return user;
        }
    }

    public static SM_USER getUser(String login, String password) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("login").equal(login)
            .field("password").equal(password)
            .asList();

        if (users == null || users.isEmpty()) {
            System.out.println("User with login: " + login +  " password: " + password + " not exist in database");
            return null;
        } else {
            SM_USER user = users.get(0);
            System.out.println("User with login: " + login +  " password: " + password + " exist in database");
            return user;
        }
    }

    public static SM_USER getUser(String token) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("token").equal(token)
            .asList();

        if (users == null || users.isEmpty()) {
            System.out.println("User with token: " + token +  " not exist in database");
            return null;
        } else {
            SM_USER user = users.get(0);
            System.out.println("User with token: " + token +  " exist in database");
            return user;
        }
    }

    public static List<SM_USER> getUsers(String houseId) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("houseId").equal(houseId)
            .asList();

        if (users == null || users.isEmpty()) {
            System.out.println("No users");
            return null;
        } else {
            System.out.println("Users count: " + users.size());
            return users;
        }
    }

    public static SM_USER getUserWithLogin(String login) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("login").equal(login)
            .asList();

        if (users == null || users.isEmpty()) {
            System.out.println("No users");
            return null;
        } else {
            SM_USER user = users.get(0);
            System.out.println("Users count: " + users.size());
            return user;
        }
    }

    public static void deleteUser(String login) {

        final List<SM_USER> users = datastore.createQuery(SM_USER.class)
            .field("login").equal(login)
            .asList();

        if (users == null || users.isEmpty()) {
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
            .field("id").equal(new ObjectId(id))
            .asList();

        if (houses == null || houses.isEmpty()) {
            System.out.println("House with id: " + id + " not exist in database");
            return null;
        } else {
            SM_HOUSE user = houses.get(0);
            System.out.println("House with id: " + id + " exist in database");
            return user;
        }
    }

    public static List<SM_HOUSE> getHouses() {

        final List<SM_HOUSE> houses = datastore.createQuery(SM_HOUSE.class)
            .asList();

        if (houses == null || houses.isEmpty()) {
            System.out.println("No houses");
            return null;
        } else {
            System.out.println("Houses count: " + houses.size());
            return houses;
        }
    }

    public static void deleteHouse(String houseId) {
        final List<SM_HOUSE> houses = datastore.createQuery(SM_HOUSE.class)
            .field("_id").equal(new ObjectId(houseId))
            .asList();

        if (houses == null || houses.isEmpty()) {
            System.out.println("No such house");
        } else {
            SM_HOUSE house = houses.get(0);
            datastore.delete(house);
            System.out.println("House deleted: " + house.getId().toString());
        }
    }

    public static void saveHouse(SM_HOUSE house) { datastore.save(house); }

    // SM_ACTOR

    public static SM_ACTOR getActor(String actorId) {

        final List<SM_ACTOR> actors = datastore.createQuery(SM_ACTOR.class)
            .field("_id").equal(new ObjectId(actorId))
            .asList();

        if (actors == null || actors.isEmpty()) {
            System.out.println("Actor with id: " + actorId.toString() +  " not exist in database");
            return null;
        } else {
            SM_ACTOR actor = actors.get(0);
            System.out.println("Actor with id: " + actorId.toString() + " exists in database");
            return actor;
        }
    }

    public static List<SM_ACTOR> getAllActors() {

        final List<SM_ACTOR> actors = datastore.createQuery(SM_ACTOR.class)
            .asList();

        if (actors == null || actors.isEmpty()) {
            System.out.println("No actors");
            return null;
        } else {
            System.out.println("Actors count: " + actors.size());
            return actors;
        }
    }

    public static List<SM_ACTOR> getActors(String houseId) {
        final List<SM_ACTOR> actors = datastore.createQuery(SM_ACTOR.class)
            .field("houseId").equal(houseId)
            .asList();

        if (actors == null || actors.isEmpty()) {
            System.out.println("No actors");
            return null;
        } else {
            System.out.println("Actors count: " + actors.size());
            return actors;
        }
    }

    public static void deleteActor(String actorId) {
        final List<SM_ACTOR> sensors = datastore.createQuery(SM_ACTOR.class)
            .field("_id").equal(new ObjectId(actorId))
            .asList();

        if (sensors == null || sensors.isEmpty()) {
            System.out.println("No such actor");
        } else {
            SM_ACTOR sensor = sensors.get(0);
            datastore.delete(sensor);
            System.out.println("Actor deleted: " + sensor.getId().toString());
        }
    }

    public static void saveActor(SM_ACTOR actor) {
        datastore.save(actor);
    }

    // SM_SENSOR

    static public SM_SENSOR getSensor(String sensorId) {

        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
            .field("_id").equal(new ObjectId(sensorId))
            .asList();

        if (sensors == null || sensors.isEmpty()) {
            System.out.println("Sensor with id: " + sensorId.toString() +  " not exist in database");
            return null;
        } else {
            SM_SENSOR sensor = sensors.get(0);
            System.out.println("Sensor with id: " + sensorId.toString() + " exists in database");
            return sensor;
        }
    }

    public static List<SM_SENSOR> getAllSensors() {

        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
            .asList();

        if (sensors == null || sensors.isEmpty()) {
            System.out.println("No sensors");
            return null;
        } else {
            System.out.println("Sensors count: " + sensors.size());
            return sensors;
        }
    }

    public static List<SM_SENSOR> getSensors(String houseId) {

        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
            .field("houseId").equal(houseId)
            .asList();

        if (sensors == null || sensors.isEmpty()) {
            System.out.println("No sensors");
            return null;
        } else {
            System.out.println("Sensors count: " + sensors.size());
            return sensors;
        }
    }

    public static void deleteSensor(String sensorId) {

        final List<SM_SENSOR> sensors = datastore.createQuery(SM_SENSOR.class)
            .field("_id").equal(new ObjectId(sensorId))
            .asList();

        if (sensors == null || sensors.isEmpty()) {
            System.out.println("No such sensor");
        } else {
            SM_SENSOR sensor = sensors.get(0);
            datastore.delete(sensor);
            System.out.println("Sensor deleted: " + sensor.getId().toString());
        }
    }

    public static void saveSensor(SM_SENSOR sensor) {
        datastore.save(sensor);
    }

    // SM_HISTORY

    public static List<SM_HISTORY> getAllHistory(String houseId) {

        final List<SM_HISTORY> historyList = datastore.createQuery(SM_HISTORY.class)
            .field("houseId").equal(houseId)
            .asList();

        if (historyList == null || historyList.isEmpty()) {
            System.out.println("No history");
            return null;
        } else {
            System.out.println("History count: " + historyList.size());
            return historyList;
        }
    }

    public static void saveHistory(SM_HISTORY history) { datastore.save(history); }

    // SM_EXTREME

    public static List<SM_EXTREME> getAllExtreme(String houseId) {

        final List<SM_EXTREME> extremes = datastore.createQuery(SM_EXTREME.class)
            .field("houseId").equal(houseId)
            .asList();

        if (extremes == null || extremes.isEmpty()) {
            System.out.println("No extremes");
            return null;
        } else {
            System.out.println("Extremes count: " + extremes.size());
            return extremes;
        }
    }

    public static void saveExtreme(SM_EXTREME extreme) { datastore.save(extreme); }

    // SM_SCENARIO

    public static List<SM_SCENARIO> getScenarioList(String houseId) {

        final List<SM_SCENARIO> scenarios = datastore.createQuery(SM_SCENARIO.class)
            .field("houseId").equal(houseId)
            .asList();

        if (scenarios == null || scenarios.isEmpty()) {
            System.out.println("No scenarios");
            return null;
        } else {
            System.out.println("Scenarios count: " + scenarios.size());
            return scenarios;
        }
    }

    static public SM_SCENARIO getScenario(String scenarioId) {

        final List<SM_SCENARIO> scenarios = datastore.createQuery(SM_SCENARIO.class)
            .field("_id").equal(new ObjectId(scenarioId))
            .asList();

        if (scenarios == null || scenarios.isEmpty()) {
            System.out.println("Scenario with id: " + scenarioId.toString() +  " not exist in database");
            return null;
        } else {
            SM_SCENARIO scenario = scenarios.get(0);
            System.out.println("Scenario with id: " + scenarioId.toString() + " exists in database");
            return scenario;
        }
    }

    public static void deleteScenario(String scenarioId) {

        final List<SM_SCENARIO> scenarios = datastore.createQuery(SM_SCENARIO.class)
            .field("_id").equal(new ObjectId(scenarioId))
            .asList();

        if (scenarios == null || scenarios.isEmpty()) {
            System.out.println("No such scenario");
        } else {
            SM_SCENARIO scenario = scenarios.get(0);
            datastore.delete(scenario);
            System.out.println("Scenario deleted: " + scenario.getId().toString());
        }
    }

    public static void saveScenario(SM_SCENARIO scenario) { datastore.save(scenario); }
}
