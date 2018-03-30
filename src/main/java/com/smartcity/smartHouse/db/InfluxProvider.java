package com.smartcity.smartHouse.db;

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

//    новые данные для подключения
//    инфлюкс:   influx-pogahuko-41bd.aivencloud.com: 28496
//    логин: avnadmin
//    пароль: v0to1jeosa0qsluz
//
//    графана: grafana-pogahuko-41bd.aivencloud.com
//    логин: avnadmin
//    пароль: xa1g5p51wgks6xc5

    private static final String DB_NAME = "defaultdb";
    private static final String SERVER_ENDPOINT = "https://influx-pogahuko-41bd.aivencloud.com:28496";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "v0to1jeosa0qsluz";
    private static InfluxDB client;

    public static void initInflux() {
        client = InfluxDBFactory.connect(SERVER_ENDPOINT, USERNAME, PASSWORD);
    }

    public static Double querySensorData(SM_SENSOR sensor) {
        String queryString = "SELECT mean(\"" + sensor.fieldName + "\") FROM \"autogen\".\"" + sensor.measurment + "\" GROUP BY time(1h) fill(none)";
//        System.out.println(queryString);
        Query query = new Query(queryString, DB_NAME);

        QueryResult queryResult = client.query(query);
        List<QueryResult.Result> results = queryResult.getResults();

        if (results != null) {
            for (QueryResult.Result result: results) {
                List<QueryResult.Series> seriesList = result.getSeries();

                if (seriesList != null) {
                    for (QueryResult.Series series: seriesList) {
                        List<List<Object>> values = series.getValues();
                        List<Object> lastValue = values.get(values.size() - 1);
                        Object lastValueValue = lastValue.get(1);
                        Double lastValueValueDouble = Double.parseDouble(lastValueValue.toString());

                        return lastValueValueDouble;

////                        System.out.println("Sensor with id: " + sensor.getId().toString() + " last value: " + lastValueValueDouble.toString());
//
//                        // check for extreme
//                        if (lastValueValueDouble >= Double.parseDouble(sensor.extreme.toString())) {
//                            ScenarioManager.extremeSituation(sensor, lastValueValueDouble);
//                        } else {
////                            System.out.println("No extreme");
//                        }
//
//                        // check for scenario
//                        ScenarioManager.checkForScenarios(sensor);
                    }
                } else {
//                    System.out.println("Series null");
                }
            }
        } else {
//            System.out.println("Results null");
        }
        return null;
    }

    public static void writeToInfluxData(String measurement, String field, Integer value){

        Date date = new Date();
        long longDate = date.getTime();

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
