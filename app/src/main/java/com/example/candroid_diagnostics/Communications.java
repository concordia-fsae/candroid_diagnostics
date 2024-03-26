package com.example.candroid_diagnostics;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Communications {
    private static final String TAG = "Communications";

    public enum Comm_Class_E {
        MQTT,
    }


    private static Communications instance;
    private Comm_Class_E comms;
    private MqttClient mqttClient;
    private ArrayList<Tuple<String, Integer>> filterList;

    private Communications() {

    }

    public static synchronized Communications start(Comm_Class_E comms, ArrayList<Tuple<String, String>> vars) {
        if (instance == null) {
            instance = new Communications();
        }

        instance.comms = comms;
        instance.filterList = new ArrayList<>();

        vars.forEach((e) -> {
            instance.filterList.add(new Tuple<String, Integer>(e.x + "/" + e.y, 0));
        });

        if (instance.comms == Comm_Class_E.MQTT) {
            instance.mqttInit();
        }

        return instance;
    }

    public void clearFilter() {
        filterList.forEach((e) -> {
            mqttClient.unsubscribe(e.x);
        });

        filterList.clear();
    }

    public static void destroy() {
        if (instance == null) return;

        instance.clearFilter();

        if (instance.comms == Comm_Class_E.MQTT) {
            // Disconnect from MQTT broker
            if (instance.mqttClient != null) {
                instance.mqttClient.disconnect();
            }
        }

        instance.mqttClient = null;
    }

    private void mqttInit() {
        // Initialize MQTT client
        try {
            mqttClient = MqttClient.getInstance();

            // Connect to MQTT broker
            mqttClient.connect(filterList);
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize MQTT client: " + e.getMessage() + " " + e.getCause());
        }
    }

    public static boolean isConnected() {
        if (instance == null) return false;
        if (instance.mqttClient== null) return false;
        return instance.mqttClient.isConnected();
    }

    public static boolean isConnecting() {
        if (instance == null) return false;
        if (instance.mqttClient== null) return false;
        return instance.mqttClient.isConnecting();
    }
}
