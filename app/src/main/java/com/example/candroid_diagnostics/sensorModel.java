package com.example.candroid_diagnostics;

public class sensorModel {
    String sensorName;
    String dataValue1;
    String dataValue2;
    int image;

    public sensorModel(String sensorName, String dataValue1, String dataValue2, int image) {
        this.sensorName = sensorName;
        this.dataValue1 = dataValue1;
        this.dataValue2 = dataValue2;
        this.image = image;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getDataValue1() {
        return dataValue1;
    }

    public String getDataValue2() {
        return dataValue2;
    }

    public int getImage() {
        return image;
    }
}

