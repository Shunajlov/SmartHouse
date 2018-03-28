package com.smartcity.smartHouse.db;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

public class InfluxProvider {

    //    Еще раз новые настройки подключения:
//    инфлюкс: influx-taberum-461f.aivencloud.com: 26215
//    логин: avnadmin
//    пароль: eabvua78m3ko6nyo
//
//    графана:   grafana-taberum-461f.aivencloud.com
//    логин: avnadmin
//    пароль: j2osyza843sdz83c

    private static final String DB_NAME = "defaultdb";
    private static final String SERVER_ENDPOINT = "https://influx-taberum-461f.aivencloud.com:26215";
    private static InfluxDB client;

    public static void initInflux() {
        client = InfluxDBFactory.connect(SERVER_ENDPOINT, "avnadmin", "eabvua78m3ko6nyo");
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

//    public static void writeToInfluxData(String measurement, String field, Integer value){
//        InfluxDB influxDB = InfluxDBFactory.connect(SERVER_ENDPOINT, "avnadmin", "eabvua78m3ko6nyo");
//
//        Date date = new Date();
//        long longDate = date.getTime();
//
//        influxDB.write(DB_NAME, "autogen", Point.measurement(measurement)
//            .time(longDate, TimeUnit.MILLISECONDS)
//            .addField(field, value)
//            .build());
//        influxDB.close();
//    }
//
//    public static void writeToInfluxData(Date date, String measurement, String field, Double value, String field2, Double value2){
//        InfluxDB influxDB = InfluxDBFactory.connect(SERVER_ENDPOINT, "avnadmin", "eabvua78m3ko6nyo");
//
//        long longDate = date.getTime();
//
//        influxDB.write(DB_NAME, "autogen", Point.measurement(measurement)
//            .time(longDate, TimeUnit.MILLISECONDS)
//            .addField(field, value)
//            .addField(field2, value2)
//            .build());
//        influxDB.close();
//    }

}
