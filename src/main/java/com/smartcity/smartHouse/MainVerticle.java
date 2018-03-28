package com.smartcity.smartHouse;

import com.smartcity.smartHouse.dataModel.AuthResult;
import com.smartcity.smartHouse.dataModel.AuthRoomerResult;
import com.smartcity.smartHouse.dataModel.BasicAnswer;
import com.smartcity.smartHouse.dataModel.RoomersHouseResult;
import com.smartcity.smartHouse.utils.Const;
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
import java.util.Map;
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
        router.get(Const.ADD_ROOMER).handler(this::handleAddRoomer);
        router.get(Const.ROOMERS_LIST).handler(this::handleListRoomers);

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

    private void sendBadTokenError(HttpServerResponse response) {
        response.setStatusCode(401).end(Json.encodePrettily(new BasicAnswer(5, "Bad user token")));
    }

    private void handleTestMethod(RoutingContext context) {
        String name = context.request().getParam("name");
        if (name == null)
            sendError(400, context.response(), "");
        else {
            HttpServerResponse response = context.response();
            response.end(Json.encodePrettily(new BasicAnswer("Hello, " + name + "!")));
        }
    }

    /**
     * Авторизация
     */
    private void handleAuth(RoutingContext context) {
        String login = context.request().getParam("login");
        String password = context.request().getParam("password");
        AuthResult result = new AuthResult(login, password);

        if (Const.tokenUsersMap.containsKey(login)) {
            sendError(401, context.response(), Json.encodePrettily(new BasicAnswer(1, "User already exists")));
        } else {
            Const.tokenUsersMap.put(login, result);

            context.response().end(Json.encodePrettily(result));
        }
    }

    private void handleAddRoomer(RoutingContext context) {
        String token = context.request().getParam("token");
        if (!Utils.isTokenValid(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String login = context.request().getParam("login");
        String password = context.request().getParam("password");
        String houseId = context.request().getParam("houseId");

        AuthRoomerResult result = new AuthRoomerResult(houseId);
        result.setLogin(login);
        result.setPassword(password);

        if (Const.tokenRoomersMap.containsKey(login)) {
            sendError(401, context.response(), Json.encodePrettily(new BasicAnswer(1, "User already exists")));
        } else {
            Const.tokenRoomersMap.put(login, result);
            context.response().end(Json.encodePrettily(result));
        }
    }

    private void handleListRoomers(RoutingContext context) {
        String token = context.request().getParam("token");
        if (!Utils.isTokenValid(token)) {
            sendBadTokenError(context.response());
            return;
        }

        String houseId = context.request().getParam("houseId");
        ArrayList<AuthRoomerResult> roomersList = new ArrayList<>();
        for (Map.Entry<String, AuthRoomerResult> entry : Const.tokenRoomersMap.entrySet()) {
            if (entry.getValue().getHouseId() != null && entry.getValue().getHouseId().equals(houseId))
                roomersList.add(entry.getValue());
        }
        context.response().end(Json.encodePrettily(new RoomersHouseResult(roomersList)));
    }
}
