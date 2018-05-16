package com.smartcity.smartHouse.db;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.smartcity.smartHouse.ScenarioManager.ScenarioManager;
import com.smartcity.smartHouse.dataModel.Storage.*;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;

import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class InfluxProvider {

//    инфлюкс: influx-nufuxekago-2672.aivencloud.com:20897
//    логин: avnadmin
//    пароль: vuafqasrhd1co0e3
//
//    графана:   grafana-nufuxekago-2672.aivencloud.com
//    логин: avnadmin
//    пароль: abhwhs1cq9rz0n1x

    private static String DB_NAME = "defaultdb";
    private static String SERVER_ENDPOINT = "http://127.0.0.1:8086";
    private static String USERNAME = "superadmin";
    private static String PASSWORD = "pass";
    private static InfluxDB client;

    public static void connectInfluxDB(String endpoint, String dbName, String username, String password) {
        SERVER_ENDPOINT = endpoint;
        DB_NAME = dbName;
        USERNAME = username;
        PASSWORD = password;
        initInflux();
    }

    public static void initInflux() {
        try {
            client = InfluxDBFactory.connect(SERVER_ENDPOINT, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("ERROR! NO INFLUXDB CONNECTION, RESTART SERVER TO RECCONNECT!\nOr send viable InfluxDb configuration via /api/setupInflux/:endpoint/:dbName/:username/:password/:token");
        }
    }

    public static Double querySensorData(SM_SENSOR sensor) {
        String queryString = "SELECT mean(\"" + sensor.fieldName + "\") FROM \"autogen\".\"" + sensor.measurment + "\" GROUP BY time(1h) fill(none)";
//        System.out.println(queryString);
        Query query = new Query(queryString, DB_NAME);

        QueryResult queryResult = client.query(query);
        List<QueryResult.Result> results = queryResult.getResults();

        try {
            if (results != null) {
                for (QueryResult.Result result: results) {
                    List<QueryResult.Series> seriesList = result.getSeries();

                    if (seriesList != null) {
                        for (QueryResult.Series series: seriesList) {
                            List<List<Object>> values = series.getValues();
                            List<Object> lastValue = values.get(values.size() - 1);
                            Object lastValueValue = lastValue.get(1);
                            String lastValueValueString = lastValueValue.toString();
                            if (!lastValueValueString.equals("")) {
                                return Double.valueOf(lastValueValueString);
                            }
                        }
                    } else {
//                    System.out.println("Series null");
                    }
                }
            } else {
//            System.out.println("Results null");
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    public static Double queryLastValue(String fieldName, String measurment) {
        String queryString = "SELECT mean(\"" + fieldName + "\") FROM \"autogen\".\"" + measurment + "\" GROUP BY time(1h) fill(none)";
//        System.out.println(queryString);
        Query query = new Query(queryString, DB_NAME);

        QueryResult queryResult = client.query(query);
        List<QueryResult.Result> results = queryResult.getResults();

        try {
            if (results != null) {
                for (QueryResult.Result result: results) {
                    List<QueryResult.Series> seriesList = result.getSeries();

                    if (seriesList != null) {
                        for (QueryResult.Series series: seriesList) {
                            List<List<Object>> values = series.getValues();
                            List<Object> lastValue = values.get(values.size() - 1);
                            Object lastValueValue = lastValue.get(1);
                            String lastValueValueString = lastValueValue.toString();
                            if (lastValueValueString.equals("")) {
                                return Double.valueOf(lastValueValueString);
                            }
                        }
                    } else {
//                    System.out.println("Series null");
                    }
                }
            } else {
                System.out.println("Results null");
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    public static void writeToInfluxData(String measurement, String field, Integer value){

        Date date = new Date();
        long longDate = date.getTime();

        System.out.println("influx " + DB_NAME);
        System.out.println("SELECT mean(\"" + field + "\") FROM \"autogen\".\"" + measurement + "\" GROUP BY time(1h) fill(none)");

        client.write(DB_NAME, "autogen", Point.measurement(measurement)
            .time(longDate, TimeUnit.MILLISECONDS)
            .addField(field, value)
            .build());
    }

    public static void writeToInfluxData(Date date, String measurement, String field, Double value, String field2, Double value2){
        long longDate = date.getTime();

        client.write(DB_NAME, "autogen", Point.measurement(measurement)
            .time(longDate, TimeUnit.MILLISECONDS)
            .addField(field, value)
            .addField(field2, value2)
            .build());
        client.close();
    }

}
