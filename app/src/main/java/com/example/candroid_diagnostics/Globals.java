package com.example.candroid_diagnostics;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

import java.util.HashMap;
import java.util.Set;

public class Globals {
    // Base concept from https://stackoverflow.com/questions/1944656/android-global-variable

    public static String MQTT_SERVER_URI = "tcp://10.0.0.47:1883";

    public static HashMap<String, HashMap<String, String>> lookupTables = new HashMap<>();
    public static String selected_ecu;

    public static boolean valueExists(String ecu, String val) {
        if (tableExists(ecu)) {
            if (lookupTables.get(ecu).containsKey(val)) { return true; }
        }

        return false;
    }

    public static boolean tableExists(String ecu) {
        if (lookupTables.containsKey(ecu)) { return true; }

        return false;
    }

    public static void setValue(String ecu, String val, String value) {
        if (!tableExists(ecu)) { lookupTables.put(ecu, new HashMap<>()); }

        lookupTables.get(ecu).put(val, value);
    }
    public static String getValue(String ecu, String val) {
        if (valueExists(ecu, val))
            return lookupTables.get(ecu).get(val);

        return "";
    }

    public static Set<String> getECUs() {
        return lookupTables.keySet();
    }

    public static Set<String> getECUVariables(String ecu) {
        if (tableExists(ecu)) {
            return lookupTables.get(ecu).keySet();
        }

        return null;
    }
}