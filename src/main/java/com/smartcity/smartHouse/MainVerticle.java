package com.smartcity.smartHouse;

import com.mongodb.Mongo;
import com.smartcity.smartHouse.Enums.*;
import com.smartcity.smartHouse.dataModel.Storage.*;
import com.smartcity.smartHouse.dataModel.apiResults.*;
import com.smartcity.smartHouse.db.InfluxProvider;
import com.smartcity.smartHouse.db.MongoDbProvider;
import com.smartcity.smartHouse.utils.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> future) {
        Router router = Router.router(vertx);
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);

        router.route().handler(BodyHandler.create());
        router.route().handler(CorsHandler.create("*")
            .allowedHeaders(allowHeaders)
            .allowedMethods(allowMethods));

        setupRoutes(router);
//        setupSocket();

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(Const.PORT, result -> {
                if (result.succeeded())
                    future.complete();
                else
                    future.fail(result.cause());

            });
        System.out.println("HTTP server started on port 8080");
    }

    private void setupRoutes(Router router) {

        router.get(Const.PING).handler(this::handlePing);

        router.get(Const.SETUP_MONGODB).handler(this::handleSetupMongo);
        router.get(Const.SETUP_INFLUX).handler(this::handleSetupInflux);

        router.get(Const.AUTH).handler(this::handleAuth);

        router.get(Const.INTEGRATORS_LIST).handler(this::handleListIntegrators);
        router.get(Const.INTEGRATOR_ADD).handler(this::handleIntegratorAdd);
        router.get(Const.INTEGRATOR_DELETE).handler(this::handleIntegratorDelete);

        router.get(Const.USERS_LIST).handler(this::handleListUsers);
        router.get(Const.USER_ADD).handler(this::handleUserAdd);
        router.get(Const.USER_DELETE).handler(this::handleUserDelete);

        router.get(Const.USER_PLAN).handler(this::handleUserPlan);
        router.get(Const.USER_PLAN_EDIT).handler(this::handleUserPlanEdit);

        router.get(Const.HOUSES_LIST).handler(this::handleListHouses);
        router.get(Const.HOUSE_ADD).handler(this::handleHouseAdd);
        router.get(Const.HOUSE_DELETE).handler(this::handleHouseDelete);

        router.get(Const.SENSORS_LIST).handler(this::handleListSensors);
        router.get(Const.SENSOR).handler(this::handleSensor);
        router.get(Const.SENSOR_ADD).handler(this::handleSensorAdd);
        router.get(Const.SENSOR_EDIT).handler(this::handleSensorEdit);
        router.get(Const.SENSOR_DELETE).handler(this::handleSensorDelete);
        router.get(Const.SENSOR_NEW_VALUE).handler(this::handleSensorAddValue); //
        router.get(Const.SENSOR_LAST_VALUE).handler(this::handleSensorLastValue); //

        router.get(Const.ACTORS_LIST).handler(this::handleActors);
        router.get(Const.ACTOR).handler(this::handleActor);
        router.get(Const.ACTOR_ADD).handler(this::handleActorAdd);
        router.get(Const.ACTOR_EDIT).handler(this::handleActorEdit);
        router.get(Const.ACTOR_DELETE).handler(this::handleActorDelete);
        router.get(Const.ACTOR_NEW_VALUE).handler(this::handleActorAddValue); //
        router.get(Const.ACTOR_LAST_VALUE).handler(this::handleActorLastValue); //

        router.get(Const.SCENARIOS_LIST).handler(this::handleScenariosList);
        router.get(Const.SCENARIO).handler(this::handleScenario);
        router.get(Const.SCENARIO_ADD).handler(this::handleScenarioAdd);
        router.get(Const.SCENARIO_EDIT).handler(this::handleScenarioEdit);
        router.get(Const.SCENARIO_CONDITIONS).handler(this::handleScenarioConditions);
        router.get(Const.SCENARIO_ADD_CONDITION).handler(this::handleScenarioAddCondition);
        router.get(Const.SCENARIO_REMOVE_CONDITION).handler(this::handleScenarioRemoveCondition);
        router.get(Const.SCENARIO_ACTIONS).handler(this::handleScenarioActions);
        router.get(Const.SCENARIO_ADD_ACTION).handler(this::handleScenarioAddAction);
        router.get(Const.SCENARIO_REMOVE_ACTION).handler(this::handleScenarioRemoveAction);
        router.get(Const.SCENARIO_DELETE).handler(this::handleScenarioDelete);

        router.get(Const.HISTORY_LIST).handler(this::handleHistory);

        router.get(Const.EXTREME_LIST).handler(this::handleExtremeList);
    }

    // TEST

    private void handlePing(RoutingContext context) {
        HttpServerResponse response = context.response();
        response.end(Json.encodePrettily(new BasicResult(1, "pong")));
    }

    // TOKEN

    private Boolean validToken(String token) {
        SM_USER user = MongoDbProvider.getUser(token);
        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);
        return (user != null || integrator != null || token.equals("adminToken"));
    }

    // ERROR

    private void sendError(int statusCode, HttpServerResponse response, String message) {
        response.setStatusCode(statusCode).end(message);
    }

    private void sendBadTokenError(HttpServerResponse response) {
        response.setStatusCode(401).end(Json.encodePrettily(new BasicResult(5, "Bad token")));
    }

    // SETUP

    private void handleSetupMongo(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
//            sendBadTokenError(context.response());
//            return;
        }

        String endpoint = context.request().getParam("endpoint");
        String dbName = context.request().getParam("dbName");

        MongoDbProvider.connectMongoDB(endpoint, dbName);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Mongo configuration changed")));
    }

    private void handleSetupInflux(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
//            sendBadTokenError(context.response());
//            return;
        }

        String endpoint = context.request().getParam("endpoint");
        String dbName = context.request().getParam("dbName");
        String username = context.request().getParam("username");
        String password = context.request().getParam("password");

        InfluxProvider.connectInfluxDB(endpoint, dbName, username, password);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Influx configuration changed")));
    }

    // AUTH

    private void handleAuth(RoutingContext context) {
        String login = context.request().getParam("login");
        String password = context.request().getParam("password");

        SM_USER user = MongoDbProvider.getUser(login, password);
        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(login, password);

        if (integrator != null) { // is SM_INTEGRATOR
            context.response().end(Json.encodePrettily(new AuthIntegratorResult(integrator)));
            makeHistory(user.houseId,"User auth, login: " + login);
        } else if (user != null) { // is SM_USER
            context.response().end(Json.encodePrettily(new AuthUserResult(user)));
        } else if (login.equals("avnadmin") && password.equals("password")) { // is SM_ADMIN
            SM_ADMIN admin = new SM_ADMIN();
            admin.login = "avnadmin";
            admin.password = "password";
            admin.token = "adminToken";
            if (MongoDbProvider.getAdmin(admin.token) == null) {
                MongoDbProvider.saveAdmin(admin);
            }
            context.response().end(Json.encodePrettily(new AuthAdminResult(admin)));
        } else if (user == null && integrator == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such user or integrator")));
        }
    }

    private Boolean validLogin(String login) {
        SM_USER userWithLogin = MongoDbProvider.getUserWithLogin(login);
        SM_INTEGRATOR integratorWithLogin = MongoDbProvider.getIntegratorWithLogin(login);
        SM_ADMIN adminWithLogin = MongoDbProvider.getAdminWithLogin(login);

        return !(userWithLogin == null && integratorWithLogin == null && adminWithLogin == null);
    }

    // INTEGRATOR

    private void handleListIntegrators(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_ADMIN admin = MongoDbProvider.getAdmin(token);

        if (admin == null) {
            sendBadTokenError(context.response());
            return;
        }

        ArrayList<IntegratorResult> integrators = new ArrayList<>();

        List<SM_INTEGRATOR> mongoIntegrators = MongoDbProvider.getIntegrators();

        if (mongoIntegrators != null && !mongoIntegrators.isEmpty()) {
            for (SM_INTEGRATOR integrator: mongoIntegrators) {
                integrators.add(new IntegratorResult(integrator));
            }

            context.response().end(Json.encodePrettily(new GetIntegratorsResult(integrators)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No integrators")));
        }
    }

    private void handleIntegratorAdd(RoutingContext context) {
//        String token = context.request().getParam("token");
//
//        SM_ADMIN admin = MongoDbProvider.getAdmin(token);
//
//        if (admin == null) {
//            sendBadTokenError(context.response());
//            return;
//        }

        String login = context.request().getParam("login");

        if (validLogin(login)) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "Login exists")));
            return;
        }

        SM_INTEGRATOR integrator = new SM_INTEGRATOR();

        integrator.login = login;
        integrator.password = context.request().getParam("password");
        integrator.token = Utils.generateToken();

        MongoDbProvider.saveIntegrator(integrator);

        AuthIntegratorResult result = new AuthIntegratorResult(integrator);
        context.response().end(Json.encodePrettily(result));
    }

    private void handleIntegratorDelete(RoutingContext context) {
//        String token = context.request().getParam("token");
//
//        SM_ADMIN admin = MongoDbProvider.getAdmin(token);
//
//        if (admin == null) {
//            sendBadTokenError(context.response());
//            return;
//        }

        String login = context.request().getParam("login");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegratorWithLogin(login);

        MongoDbProvider.deleteIntegrator(login);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Integrator deleted")));
    }

    // USER

    private void handleUserAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) { //|| !Utils.isTokenValid(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");

        if (validLogin(login)) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "Login exists")));
            return;
        }

        SM_USER user = new SM_USER();

        user.login = login;
        user.password = context.request().getParam("password");
        user.houseId = context.request().getParam("houseId");
        user.name = context.request().getParam("name");
        user.type = UserPlanType.valueOf(context.request().getParam("type"));
        user.token = Utils.generateToken();

        MongoDbProvider.saveUser(user);

        AuthUserResult result = new AuthUserResult(user);
        context.response().end(Json.encodePrettily(result));

        makeHistory(user.houseId,"User added, login: " + login);
    }

    private void handleUserDelete(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");

        SM_USER user = MongoDbProvider.getUserWithLogin(login);

        MongoDbProvider.deleteUser(login);

        context.response().end(Json.encodePrettily(new BasicResult(1, "User deleted")));

        if (user != null) {
            makeHistory(user.houseId,"User deleted, login: " + login);
        }
    }

    private void handleListUsers(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        ArrayList<UserResult> users = new ArrayList<>();

        List<SM_USER> mongoUsers = MongoDbProvider.getUsers(houseId);

        if (mongoUsers != null && !mongoUsers.isEmpty()) {
            for (SM_USER user: mongoUsers) {
                users.add(new UserResult(user));
            }

            context.response().end(Json.encodePrettily(new GetUsersResult(users)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No users")));
        }
    }

    // USER PLAN

    private void handleUserPlan(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String userId = context.request().getParam("userId");
        SM_USER user = MongoDbProvider.getUserWithId(userId);

        if (user == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "Wrong userId")));
            return;
        }

        context.response().end(Json.encodePrettily(new BasicResult(0, user.type.toString())));
    }

    private void handleUserPlanEdit(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String userId = context.request().getParam("userId");
        SM_USER user = MongoDbProvider.getUserWithId(userId);

        if (user == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "Wrong userId")));
            return;
        }

        String plan = context.request().getParam("plan");

        user.type = UserPlanType.valueOf(plan);
        MongoDbProvider.saveUser(user);

        context.response().end(Json.encodePrettily(new UserResult(user)));
    }

    // HOUSE

    private void handleListHouses(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        ArrayList<GetHouseResult> houses = new ArrayList<>();

        List<SM_HOUSE> mongoHouses = MongoDbProvider.getHouses();

        if (mongoHouses != null && !mongoHouses.isEmpty()) {
            for (SM_HOUSE house: mongoHouses) {
                houses.add(new GetHouseResult(house));
            }

            context.response().end(Json.encodePrettily(new GetHousesResult(houses)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No houses")));
        }
    }

    private void handleHouseAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String name = context.request().getParam("name");

        SM_HOUSE house = new SM_HOUSE();
        house.name = name;

        MongoDbProvider.saveHouse(house);

        makeHistory(house.getId().toString(), "House added, id: " + house.getId().toString());
        context.response().end(Json.encodePrettily(new GetHouseResult(house)));
    }

    private void handleHouseDelete(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");

        SM_HOUSE house = MongoDbProvider.getHouse(houseId);

        MongoDbProvider.deleteHouse(houseId);

        context.response().end(Json.encodePrettily(new BasicResult(1, "House deleted")));

        if (house != null) {
            makeHistory(house.getId().toString(), "House deleted, id: " + house.getId().toString());
        }
    }

    // SENSORS

    private void handleListSensors(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        ArrayList<GetSensorResult> sensors = new ArrayList<>();

        List<SM_SENSOR> mongoSensors = MongoDbProvider.getSensors(houseId);

        if (mongoSensors != null && !mongoSensors.isEmpty()) {
            for (SM_SENSOR sensor: mongoSensors) {
                sensors.add(new GetSensorResult(sensor));
            }

            context.response().end(Json.encodePrettily(new GetSensorsResult(sensors)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No sensors")));
        }
    }

    private void handleSensor(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String sensorId = context.request().getParam("sensorId");
        SM_SENSOR sensor = MongoDbProvider.getSensor(sensorId);

        if (sensor == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such sensor")));
        } else {
            context.response().end(Json.encodePrettily(new GetSensorResult(sensor)));
        }
    }

    private void handleSensorAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        String extreme = context.request().getParam("extreme");
        String fieldName = context.request().getParam("fieldName");
        String sensorType = context.request().getParam("sensorType");

        SM_SENSOR sensor = new SM_SENSOR();
        sensor.houseId = houseId;
        SM_HOUSE house = MongoDbProvider.getHouse(houseId);
        if (house != null) {
            sensor.measurment = house.name;
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such house")));
            return;
        }
        sensor.fieldName = fieldName;
        sensor.sensorType = SensorType.valueOf(sensorType);
        sensor.extreme = Integer.parseInt(extreme);
        sensor.value = 0;

        InfluxProvider.writeToInfluxData(sensor.measurment, sensor.fieldName, sensor.value);

        MongoDbProvider.saveSensor(sensor);

        makeHistory(sensor.houseId, "Sensor added, id: " + sensor.getId().toString());
        context.response().end(Json.encodePrettily(new GetSensorResult(sensor)));
    }

    private void handleSensorEdit(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String sensorId = context.request().getParam("sensorId");
        String extreme = context.request().getParam("extreme");

        SM_SENSOR sensor = MongoDbProvider.getSensor(sensorId);
        if (sensor == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such sensor")));
            return;
        }

        sensor.extreme = Integer.parseInt(extreme);

        MongoDbProvider.saveSensor(sensor);

        makeHistory(sensor.houseId, "Sensor edited, id: " + sensor.getId().toString());
        context.response().end(Json.encodePrettily(new GetSensorResult(sensor)));
    }

    private void handleSensorDelete(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String sensorId = context.request().getParam("sensorId");

        SM_SENSOR sensor = MongoDbProvider.getSensor(sensorId);

        MongoDbProvider.deleteSensor(sensorId);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Sensor deleted")));

        if (sensor != null) {
            makeHistory(sensor.houseId, "Sensor deleted, id: " + sensorId);
        }
    }

    private void handleSensorAddValue(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String sensorId = context.request().getParam("sensorId");
        SM_SENSOR sensor = MongoDbProvider.getSensor(sensorId);

        if (sensor != null) {
            String value = context.request().getParam("value");
            sensor.value = Integer.parseInt(value);
            MongoDbProvider.saveSensor(sensor);
            InfluxProvider.writeToInfluxData(sensor.measurment, sensor.fieldName, Integer.parseInt(value));

            context.response().end(Json.encodePrettily(new BasicResult(1, "Sensor data added")));
        } else {
            context.response().end(Json.encodePrettily(new BasicResult(0, "No such sensor")));
        }
    }

    private void handleSensorLastValue(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String sensorId = context.request().getParam("sensorId");
        SM_SENSOR sensor = MongoDbProvider.getSensor(sensorId);

        if (sensor != null) {
            Double lastValue = InfluxProvider.queryLastValue(sensor.measurment, sensor.fieldName);

            if (lastValue != null) {
                sensor.value = lastValue.intValue();
            }

            context.response().end(Json.encodePrettily(new GetLastValueResult(sensorId, sensor.value)));
        } else {
            context.response().end(Json.encodePrettily(new BasicResult(0, "No such sensor")));
        }
    }

    // ACTOR

    private void handleActors(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        ArrayList<GetActorResult> actors = new ArrayList<>();

        List<SM_ACTOR> mongoActors = MongoDbProvider.getActors(houseId);

        if (mongoActors != null && !mongoActors.isEmpty()) {
            for (SM_ACTOR actor: mongoActors) {
                actors.add(new GetActorResult(actor));
            }

            context.response().end(Json.encodePrettily(new GetActorsResult(actors)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No actors")));
        }
    }

    private void handleActor(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String actorId = context.request().getParam("actorId");
        SM_ACTOR actor = MongoDbProvider.getActor(actorId);

        if (actor == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such actor")));
        } else {
            context.response().end(Json.encodePrettily(new GetActorResult(actor)));
        }
    }

    private void handleActorAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        String fieldName = context.request().getParam("fieldName");
        String actorType = context.request().getParam("actorType");
        String value = context.request().getParam("value");

        SM_ACTOR actor = new SM_ACTOR();
        actor.houseId = houseId;
        SM_HOUSE house = MongoDbProvider.getHouse(houseId);
        if (house != null) {
            actor.measurment = house.name;
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such house")));
            return;
        }
        actor.fieldName = fieldName;
        actor.actorType = ActorType.valueOf(actorType);
        actor.value = Integer.parseInt(value);

        InfluxProvider.writeToInfluxData(actor.measurment, actor.fieldName, actor.value);

        MongoDbProvider.saveActor(actor);

        makeHistory(actor.houseId, "Actor added, id: " + actor.getId().toString());
        context.response().end(Json.encodePrettily(new GetActorResult(actor)));
    }

    private void handleActorEdit(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String actorId = context.request().getParam("actorId");
        String value = context.request().getParam("value");

        SM_ACTOR actor = MongoDbProvider.getActor(actorId);
        if (actor == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such actor")));
            return;
        }

        actor.value = Integer.parseInt(value);

        InfluxProvider.writeToInfluxData(actor.measurment, actor.fieldName, actor.value);

        MongoDbProvider.saveActor(actor);

        makeHistory(actor.houseId, "Actor edited, id: " + actor.getId().toString());
        context.response().end(Json.encodePrettily(new GetActorResult(actor)));
    }

    private void handleActorDelete(RoutingContext context) {
        String token = context.request().getParam("token");
        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadTokenError(context.response());
            return;
        }

        String actorId = context.request().getParam("actorId");
        SM_ACTOR actor = MongoDbProvider.getActor(actorId);
        MongoDbProvider.deleteActor(actorId);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Actor deleted")));

        if (actor != null) {
            makeHistory(actor.houseId, "Actor deleted, id: " + actorId);
        }
    }

    private void handleActorAddValue(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String actorId = context.request().getParam("actorId");
        SM_ACTOR actor = MongoDbProvider.getActor(actorId);

        if (actor != null) {
            String value = context.request().getParam("value");
            actor.value = Integer.parseInt(value);
            MongoDbProvider.saveActor(actor);
            InfluxProvider.writeToInfluxData(actor.measurment, actor.fieldName, Integer.parseInt(value));

            context.response().end(Json.encodePrettily(new BasicResult(1, "Actor data added")));
        } else {
            context.response().end(Json.encodePrettily(new BasicResult(0, "No such actor")));
        }
    }

    private void handleActorLastValue(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String actorId = context.request().getParam("actorId");
        SM_ACTOR actor = MongoDbProvider.getActor(actorId);

        if (actor != null) {
            Double lastValue = InfluxProvider.queryLastValue(actor.measurment, actor.fieldName);

            if (lastValue != null) {
                actor.value = lastValue.intValue();
            }

            context.response().end(Json.encodePrettily(new GetLastValueResult(actorId, actor.value)));
        } else {
            context.response().end(Json.encodePrettily(new BasicResult(0, "No such actor")));
        }
    }

    // SCENARIO

    private void handleScenariosList(RoutingContext context) {

        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        ArrayList<GetScenarioResult> scenarios = new ArrayList<>();

        List<SM_SCENARIO> mongoScenario = MongoDbProvider.getScenarioList(houseId);

        if (mongoScenario != null && !mongoScenario.isEmpty()) {
            for (SM_SCENARIO scenario: mongoScenario) {
                scenarios.add(new GetScenarioResult(scenario));
            }

            context.response().end(Json.encodePrettily(new GetScenarioListResult(scenarios)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No scenarios")));
        }
    }

    private void handleScenario(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);

        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
        } else {
            context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
        }
    }

    private void handleScenarioAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        String name = context.request().getParam("name");

        SM_SCENARIO scenario = new SM_SCENARIO();
        scenario.houseId = houseId;
        SM_HOUSE house = MongoDbProvider.getHouse(houseId);
        if (house == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such house")));
            return;
        }
        scenario.name = name;

        MongoDbProvider.saveScenario(scenario);

        makeHistory(scenario.houseId, "Scenario added, id: " + scenario.getId().toString());
        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioEdit(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        String name = context.request().getParam("name");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        scenario.name = name;

        MongoDbProvider.saveScenario(scenario);

        makeHistory(scenario.houseId, "Scenario edited, id: " + scenario.getId().toString());
        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioConditions(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);

        List<GetScenarioConditionResult> conditions = new ArrayList<>();

        if (scenario.conditions != null && !scenario.conditions.isEmpty()) {
            for (SM_SCENARIO_CONDITION condition: scenario.conditions) {
                conditions.add(new GetScenarioConditionResult(condition));
            }

            context.response().end(Json.encodePrettily(new GetScenarioConditionsResult(conditions)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No conditions")));
        }
    }

    private void handleScenarioAddCondition(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        String sensorId = context.request().getParam("sensorId");
        String sensorValue = context.request().getParam("sensorValue");
        String type = context.request().getParam("type");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        SM_SCENARIO_CONDITION condition = new SM_SCENARIO_CONDITION();
        condition.sensorId = sensorId;
        condition.scenarioId = scenarioId;
        condition.sensorValue = Integer.parseInt(sensorValue);
        condition.satisfied = false;
        condition.type = ConditionType.valueOf(type);

        MongoDbProvider.saveScenarioCondition(condition);

        if (scenario.conditions != null) {
            scenario.conditions.add(condition);
        } else {
            scenario.conditions = new ArrayList();
            scenario.conditions.add(condition);
        }

        MongoDbProvider.saveScenario(scenario);

        makeHistory(scenario.houseId, "Scenario condition added, scenarioId: " + scenario.getId().toString());
        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioRemoveCondition(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        String conditionId = context.request().getParam("conditionId");

        MongoDbProvider.deleteScenarioCondition(conditionId);
        makeHistory(scenario.houseId, "Scenario condition removed, scenarioId: " + scenario.getId().toString());

        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioActions(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);

        List<GetScenarioActionResult> actions = new ArrayList<>();

        if (scenario.actions != null && !scenario.actions.isEmpty()) {
            for (SM_SCENARIO_ACTION action: scenario.actions) {
                actions.add(new GetScenarioActionResult(action));
            }

            context.response().end(Json.encodePrettily(new GetScenarioActionsResult(actions)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No actions")));
        }
    }

    private void handleScenarioAddAction(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        String actorId = context.request().getParam("actorId");
        String actorValue = context.request().getParam("actorValue");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        SM_SCENARIO_ACTION action = new SM_SCENARIO_ACTION();
        action.actorId = actorId;
        action.scenarioId = scenarioId;
        action.actorValue = Integer.parseInt(actorValue);

        MongoDbProvider.saveScenarioAction(action);

        if (scenario.actions != null) {
            scenario.actions.add(action);
        } else {
            scenario.actions = new ArrayList();
            scenario.actions.add(action);
        }

        MongoDbProvider.saveScenario(scenario);

        makeHistory(scenario.houseId, "Scenario action added, scenarioId: " + scenario.getId().toString());
        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioRemoveAction(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        String actionId = context.request().getParam("actionId");

        MongoDbProvider.deleteScenarioAction(actionId);
        makeHistory(scenario.houseId, "Scenario condition removed, scenarioId: " + scenario.getId().toString());

        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioDelete(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        MongoDbProvider.deleteScenario(scenarioId);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Scenario deleted")));

        if (scenario != null) {
            makeHistory(scenario.houseId, "Scenario deleted, id: " + scenario.getId().toString());
        }
    }

    // HISTORY

    private void handleHistory(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        ArrayList<GetHistoryResult> histories = new ArrayList<>();

        String houseId = context.request().getParam("houseId");
        List<SM_HISTORY> mongoHistory = MongoDbProvider.getAllHistory(houseId);

        if (mongoHistory != null && !mongoHistory.isEmpty()) {
            for (SM_HISTORY history: mongoHistory) {
                histories.add(new GetHistoryResult(history));
            }

            context.response().end(Json.encodePrettily(new GetAllHistoryResult(histories)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No history")));
        }
    }

    private void makeHistory(String houseId, String text) {
        SM_HISTORY history = new SM_HISTORY();
        history.houseId = houseId;
        history.time = new java.util.Date().toString();
        history.eventString = text;
        MongoDbProvider.saveHistory(history);
    }

    // EXTREME

    private void handleExtremeList(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        ArrayList<GetExtremeResult> extremes = new ArrayList<>();

        String houseId = context.request().getParam("houseId");
        List<SM_EXTREME> mongoExtreme = MongoDbProvider.getAllExtreme(houseId);

        if (mongoExtreme != null && !mongoExtreme.isEmpty()) {
            for (SM_EXTREME extreme: mongoExtreme) {
                extremes.add(new GetExtremeResult(extreme));
            }

            context.response().end(Json.encodePrettily(new GetExtremeListResult(extremes)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No extremes")));
        }
    }
}
