package com.smartcity.smartHouse.db;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.Point;

import java.util.Date;
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

//    public static void checkSensorForExtreme(Sensor sensor, String roomName) {
//        Query query = new Query("SELECT mean(\"" + sensor.fieldName + "\") FROM \"autogen\".\"" + roomName + "\" WHERE time >= 1514464662040ms and time <= 1522240662040ms GROUP BY time(1h) fill(null)", DB_NAME);
//        client.query(query, 20, queryResult -> System.out.println(queryResult));
//    }
//
//    public static void checkActorForExtreme(Actor actor, String roomName) {
//        Query query = new Query("SELECT mean(\"" + actor.fieldName + "\") FROM \"autogen\".\"" + roomName + "\" WHERE time >= 1514464662040ms and time <= 1522240662040ms GROUP BY time(1h) fill(null)", DB_NAME);
//        client.query(query, 20, queryResult -> System.out.println(queryResult));
//    }

    public static void writeToInfluxData(String measurement, String field, Integer value){
        InfluxDB influxDB = InfluxDBFactory.connect(SERVER_ENDPOINT, USERNAME, PASSWORD);

        Date date = new Date();
        long longDate = date.getTime();

        influxDB.write(DB_NAME, "autogen", Point.measurement(measurement)
            .time(longDate, TimeUnit.MILLISECONDS)
            .addField(field, value)
            .build());
        influxDB.close();
    }

    public static void writeToInfluxData(Date date, String measurement, String field, Double value, String field2, Double value2){
        InfluxDB influxDB = InfluxDBFactory.connect(SERVER_ENDPOINT, USERNAME, PASSWORD);

        long longDate = date.getTime();

        influxDB.write(DB_NAME, "autogen", Point.measurement(measurement)
            .time(longDate, TimeUnit.MILLISECONDS)
            .addField(field, value)
            .addField(field2, value2)
            .build());
        influxDB.close();
    }

}
