package com.example.candroid_diagnostics;

public class sensorModel {
    String sensorName;
    String dataValue1;
    int image;

    public sensorModel(String sensorName, String dataValue1, int image) {
        this.sensorName = sensorName;
        this.dataValue1 = dataValue1;
        this.image = image;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getDataValue1() {
        return dataValue1;
    }

    public int getImage() {
        return image;
    }
}
