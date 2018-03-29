package com.smartcity.smartHouse.SensorsManager.Sensors;

import com.smartcity.smartHouse.db.InfluxProvider;

import java.util.Random;
import java.util.TimerTask;

public class SensorAnalog extends SensorBase {

    public Integer value = 0;

    public SensorAnalog() {
        type = SensorType.ANALOG;
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
            valueIncrease();
        } else if (value > 33) {
            valueDecrease();
        }
    }

    private void writeData() {
        InfluxProvider.writeToInfluxData(measurment, fieldName, value);
    }

    public void setValue(Integer value) {
        if (checkValue(value)) {
            this.value = value;
            writeData();
        }
    }

    private Boolean checkValue(Integer value) {
        return (value >= 0 && value <= 1024);
    }

    private void valueIncrease() {
        if (checkValue(value + 1)) {
            value++;
        }
    }

    private void valueDecrease() {
        if (checkValue(value - 1)) {
            value--;
        }
    }
}

