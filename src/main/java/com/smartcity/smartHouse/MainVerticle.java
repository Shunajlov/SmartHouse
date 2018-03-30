package com.smartcity.smartHouse;

import com.mongodb.Mongo;
import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.ScenarioManager.ScenarioManager;
import com.smartcity.smartHouse.SensorsManager.Actors.ActorType;
import com.smartcity.smartHouse.SensorsManager.Sensors.SensorType;
import com.smartcity.smartHouse.SensorsManager.SensorsManager;
import com.smartcity.smartHouse.dataModel.Storage.*;
import com.smartcity.smartHouse.dataModel.apiResults.*;
import com.smartcity.smartHouse.db.MongoDbProvider;
import com.smartcity.smartHouse.utils.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

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

        router.get(Const.TEST).handler(this::handleTestMethod);
        router.get(Const.AUTH).handler(this::handleAuth);

        router.get(Const.INTEGRATOR_ADD).handler(this::handleIntegratorAdd);

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

        router.get(Const.ACTORS_LIST).handler(this::handleActors);
        router.get(Const.ACTOR).handler(this::handleActor);
        router.get(Const.ACTOR_ADD).handler(this::handleActorAdd);
        router.get(Const.ACTOR_EDIT).handler(this::handleActorEdit);
        router.get(Const.ACTOR_DELETE).handler(this::handleActorDelete);

        router.get(Const.SCENARIOS_LIST).handler(this::handleScenariosList);
        router.get(Const.SCENARIO).handler(this::handleScenario);
        router.get(Const.SCENARIO_ADD).handler(this::handleScenarioAdd);
        router.get(Const.SCENARIO_EDIT).handler(this::handleScenarioEdit);
        router.get(Const.SCENARIO_ADD_ITEM).handler(this::handleScenarioAddItem);
        router.get(Const.SCENARIO_REMOVE_ITEM).handler(this::handleScenarioRemoveItem);
        router.get(Const.SCENARIO_DELETE).handler(this::handleScenarioDelete);

        router.get(Const.HISTORY_LIST).handler(this::handleHistory);

        router.get(Const.EXTREME_LIST).handler(this::handleExtremeList);
    }

    private void sendError(int statusCode, HttpServerResponse response, String message) {
        response.setStatusCode(statusCode).end(message);
    }

    private void sendBadTokenError(HttpServerResponse response) {
        response.setStatusCode(401).end(Json.encodePrettily(new BasicResult(5, "Bad token")));
    }

    private void handleTestMethod(RoutingContext context) {
        String name = context.request().getParam("name");
        if (name == null)
            sendError(400, context.response(), "");
        else {
            HttpServerResponse response = context.response();
            response.end(Json.encodePrettily(new BasicResult("Hello, " + name + "!")));
        }
    }

    private Boolean validToken(String token) {
        SM_USER user = MongoDbProvider.getUser(token);
        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);
        return (user != null || integrator != null);
    }

    private void makeHistory(String houseId, String text) {
        SM_HISTORY history = new SM_HISTORY();
        history.houseId = houseId;
        history.time = new java.util.Date().toString();
        history.eventString = text;
        MongoDbProvider.saveHistory(history);
    }

    // INTEGRATOR

    private void handleIntegratorAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_ADMIN admin = MongoDbProvider.getAdmin(token);

        if (admin == null) {
            sendBadTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");

        SM_INTEGRATOR integratorWithLogin = MongoDbProvider.getIntegratorWithLogin(login);

        if (integratorWithLogin != null) {
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

    // USER

    private void handleAuth(RoutingContext context) {
        String login = context.request().getParam("login");
        String password = context.request().getParam("password");

        SM_USER user = MongoDbProvider.getUser(login, password);
        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(login, password);

        if (user == null && integrator == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such user or integrator")));
        } else if (user == null) { // is SM_INTEGRATOR
            AuthIntegratorResult result = new AuthIntegratorResult(integrator);
            context.response().end(Json.encodePrettily(result));
            makeHistory(user.houseId,"User auth, login: " + login);
        } else if (integrator == null) { // is SM_USER
            AuthUserResult result = new AuthUserResult(user);
            context.response().end(Json.encodePrettily(result));
        }
    }

    private void handleUserAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) { //|| !Utils.isTokenValid(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");

        SM_USER userWithLogin = MongoDbProvider.getUserWithLogin(login);

        if (userWithLogin != null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "Login exists")));
            return;
        }

        SM_USER user = new SM_USER();

        user.login = login;
        user.password = context.request().getParam("password");
        user.houseId = context.request().getParam("houseId");

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

        user.type = UserType.valueOf(plan);
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
        String active = context.request().getParam("active");

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
        sensor.active = Boolean.parseBoolean(active);
        sensor.value = 0;

        if (sensor.active) {
            SensorsManager.startSensor(sensor.getId().toString());
        } else {
            SensorsManager.stopSensor(sensor.getId().toString());
        }

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
        String active = context.request().getParam("active");

        SM_SENSOR sensor = MongoDbProvider.getSensor(sensorId);
        if (sensor == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such sensor")));
            return;
        }

        sensor.extreme = Integer.parseInt(extreme);
        sensor.active = Boolean.parseBoolean(active);

        if (sensor.active) {
            SensorsManager.startSensor(sensorId);
        } else {
            SensorsManager.stopSensor(sensorId);
        }

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

        SensorsManager.setValueForActor(actor.getId().toString(), actor.value);

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

        SensorsManager.setValueForActor(actor.getId().toString(), actor.value);

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

    private void handleScenarioAddItem(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        String actorId = context.request().getParam("actorId");
        String actorValue = context.request().getParam("actorValue");
        String sensorId = context.request().getParam("sensorId");
        String sensorValue = context.request().getParam("sensorValue");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        SM_SCENARIO_ITEM item = new SM_SCENARIO_ITEM();
        item.actorId = actorId;
        item.actorValue = Integer.parseInt(actorValue);
        item.sensorId = sensorId;
        item.sensorValue = Integer.parseInt(sensorValue);

        scenario.scenario_items.add(item);

        MongoDbProvider.saveScenario(scenario);

        makeHistory(scenario.houseId, "Scenario item added, scenarioId: " + scenario.getId().toString());
        context.response().end(Json.encodePrettily(new GetScenarioResult(scenario)));
    }

    private void handleScenarioRemoveItem(RoutingContext context) {
        String token = context.request().getParam("token");

        if (!validToken(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String scenarioId = context.request().getParam("scenarioId");
        String actorId = context.request().getParam("actorId");
        String sensorId = context.request().getParam("sensorId");

        SM_SCENARIO scenario = MongoDbProvider.getScenario(scenarioId);
        if (scenario == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such scenario")));
            return;
        }

        SM_SCENARIO_ITEM itemToDelete = new SM_SCENARIO_ITEM();

        for (SM_SCENARIO_ITEM item: scenario.scenario_items) {
            if (item.sensorId.equals(sensorId) && item.actorId.equals(actorId)) {
                itemToDelete = item;
                break;
            }
            itemToDelete = null;
        }

        if (itemToDelete != null) {
            scenario.scenario_items.remove(itemToDelete);
            makeHistory(scenario.houseId, "Scenario item removed, scenarioId: " + scenario.getId().toString());
        }

        MongoDbProvider.saveScenario(scenario);

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
