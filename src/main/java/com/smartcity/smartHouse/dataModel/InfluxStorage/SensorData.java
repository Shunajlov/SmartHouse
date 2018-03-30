package com.smartcity.smartHouse.dataModel.InfluxStorage;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "SensorData")
public class SensorData {

    @Column(name = "time")
    private Instant time;

    @Column(name = "mean")
    private String mean;
}
