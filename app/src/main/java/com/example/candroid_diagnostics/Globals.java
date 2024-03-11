package com.example.candroid_diagnostics;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

import java.util.ArrayList;
import java.util.HashMap;

public class Globals {
    // Base concept from https://stackoverflow.com/questions/1944656/android-global-variable

    public static final String MQTT_SERVER_URI = "tcp://10.0.0.47:1883";
    public static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId(); //"candroid";
    public static HashMap<String, HashMap<String, String>> lookupTables = new HashMap<>();

}