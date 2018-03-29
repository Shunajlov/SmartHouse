package com.smartcity.smartHouse;

import com.smartcity.smartHouse.dataModel.Storage.*;
import com.smartcity.smartHouse.dataModel.apiResults.*;
import com.smartcity.smartHouse.db.MongoDbProvider;
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

        // routes
        router.get(Const.TEST).handler(this::handleTestMethod);
        router.get(Const.AUTH).handler(this::handleAuth);

        router.post(Const.USER_ADD).handler(this::handleUserAdd);
        router.post(Const.USER_DELETE).handler(this::handleUserDelete);
        router.get(Const.USERS_LIST).handler(this::handleListUsers);

        router.get(Const.HOUSES_LIST).handler(this::handleListHouses);

        router.get(Const.SENSORS_LIST).handler(this::handleListSensors);
        router.get(Const.SENSOR).handler(this::handleSensor);
        router.get(Const.SENSOR_DELETE).handler(this::handleSensorDelete);

        router.get(Const.ACTORS_LIST).handler(this::handleActors);
        router.get(Const.ACTOR).handler(this::handleActor);
        router.get(Const.ACTOR_DELETE).handler(this::handleActorDelete);

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

    private void sendError(int statusCode, HttpServerResponse response, String message) {
        response.setStatusCode(statusCode).end(message);
    }

    private void sendBadUserTokenError(HttpServerResponse response) {
        response.setStatusCode(401).end(Json.encodePrettily(new BasicResult(5, "Bad user token")));
    }

    private void sendBadIntegratorTokenError(HttpServerResponse response) {
        response.setStatusCode(401).end(Json.encodePrettily(new BasicResult(5, "Bad integrator token")));
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

    // USER

    /**
     * Авторизация
     */
    private void handleAuth(RoutingContext context) {
        String login = context.request().getParam("login");
        String password = context.request().getParam("password");
        AuthResult result = new AuthResult(login, password, true);
        // TODO забирать данные из монгодб

//        if (Const.tokenUsersMap.containsKey(login)) {
//            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "User already exists")));
//        } else {
//            Const.tokenUsersMap.put(login, result);
//            User user = User.fromAuthResult(result);
//            MongoProvider.writeUser(vertx, user);
//            context.response().end(Json.encodePrettily(result));
//        }

        SM_USER user = MongoDbProvider.getUser(result.getLogin(), result.getPassword());
        if (user == null) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No such user")));
        } else {
            context.response().end(Json.encodePrettily(result));
        }
    }

    private void handleUserAdd(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) { //|| !Utils.isTokenValid(token)) {
            sendBadIntegratorTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");

        List<SM_USER> usersWithLogin = MongoDbProvider.getUsersWithLogin(login);

        if (!(usersWithLogin == null || usersWithLogin.isEmpty())) {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "Login exists")));
            return;
        }

        SM_USER user = new SM_USER();

        user.login = login;
        user.password = context.request().getParam("password");
        user.houseId = context.request().getParam("houseId");

        AuthUserResult result = new AuthUserResult(user.houseId);
        result.setLogin(user.login);
        result.setPassword(user.password);

        MongoDbProvider.saveUser(user);
        context.response().end(Json.encodePrettily(result));
    }

    private void handleUserDelete(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadIntegratorTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");

        MongoDbProvider.deleteUser(login);

        context.response().end(Json.encodePrettily(new BasicResult(1, "User deleted")));
    }

    private void handleListUsers(RoutingContext context) {
        String token = context.request().getParam("token");

        //        if (!Utils.isTokenValid(token)) {
//            sendBadIntegratorTokenError(context.response());
//            return;
//        }

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) { //|| !Utils.isTokenValid(token)) {
            sendBadIntegratorTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        ArrayList<GetUserResult> users = new ArrayList<>();

        List<SM_USER> mongoUsers = MongoDbProvider.getUsers(houseId);

        if (mongoUsers != null && !mongoUsers.isEmpty()) {
            for (SM_USER user: mongoUsers) {
                users.add(new GetUserResult(user));
            }

            context.response().end(Json.encodePrettily(new GetUsersResult(users)));
        } else {
            sendError(401, context.response(), Json.encodePrettily(new BasicResult(1, "No users")));
        }
    }

    // HOUSE

    private void handleListHouses(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadIntegratorTokenError(context.response());
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

    // SENSORS

    private void handleListSensors(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_USER user = MongoDbProvider.getUser(token);

        if (user == null) {
            sendBadUserTokenError(context.response());
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

        SM_USER user = MongoDbProvider.getUser(token);

        if (user == null) {
            sendBadUserTokenError(context.response());
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

    private void handleSensorDelete(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadIntegratorTokenError(context.response());
            return;
        }

        String sensorId = context.request().getParam("sensorId");

        MongoDbProvider.deleteSensor(sensorId);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Sensor deleted")));
    }

    // ACTOR

    private void handleActors(RoutingContext context) {
        String token = context.request().getParam("token");

        SM_USER user = MongoDbProvider.getUser(token);

        if (user == null) {
            sendBadUserTokenError(context.response());
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

        SM_USER user = MongoDbProvider.getUser(token);

        if (user == null) {
            sendBadUserTokenError(context.response());
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

    private void handleActorDelete(RoutingContext context) {
        String token = context.request().getParam("token");
        SM_INTEGRATOR integrator = MongoDbProvider.getIntegrator(token);

        if (integrator == null) {
            sendBadIntegratorTokenError(context.response());
            return;
        }

        String actorId = context.request().getParam("actorId");
        MongoDbProvider.deleteActor(actorId);

        context.response().end(Json.encodePrettily(new BasicResult(1, "Actor deleted")));
    }
}
