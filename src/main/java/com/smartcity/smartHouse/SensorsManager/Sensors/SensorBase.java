package com.smartcity.smartHouse.SensorsManager.Sensors;

import java.util.Timer;

public class SensorBase {

    public String houseId = "0";
    public String sensorId = "0";
    public String measurment = "";      // Идентификатор дома (таблицы)
    public String fieldName = "";       // Название датчика в таблице
    public Boolean active = false;

    public Timer timer = new Timer();
    public SensorType type = SensorType.ANALOG;

    public void start() {
        active = true;
        System.out.println("Sensor " + sensorId.toString() + " started");
    }

    public void stop() {
        active = false;
        System.out.println("Sensor " + sensorId.toString() + " stopped");
    }
}
