package com.smartcity.smartHouse.SensorsManager.Sensors;

import com.smartcity.smartHouse.db.InfluxProvider;

import java.util.Random;
import java.util.TimerTask;

public class SensorDiscrete extends SensorBase {

    public Boolean value = false;

    public SensorDiscrete() {
        type = SensorType.DISCRETE;
    }

    @Override
    public void start() {
        super.start();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Sensor " + sensorId.toString() + " run");
                generateData();
                writeData();
                System.out.println("Value: " + value.toString());
            }
        }, 0, 1*5*1000);
    }

    @Override
    public void stop() {
        super.stop();
        timer.cancel();
    }

    private void generateData() {

        Random rand = new Random();
        int value = rand.nextInt(1024);

        if (value > 66) {
            valueOne();
        } else if (value > 33) {
            valueZero();
        }
    }

    private void writeData() {
        InfluxProvider.writeToInfluxData(measurment, fieldName, (value) ? 1 : 0);
    }

    public void setValue(Boolean value) {
        this.value = value;
        writeData();
    }

    private void valueOne() {
        value = true;
    }

    private void valueZero() {
        value = false;
    }
}