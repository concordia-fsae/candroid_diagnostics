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

import java.util.HashMap;

public class Communications {
    private static final String TAG = "Communications";

    public enum Comm_Class_E {
        MQTT,
    }


    private static Communications instance;
    protected Comm_Class_E comms;
    private MqttClient mqttClient;

    private Context ctx;

    private Communications() {

    }

    public static synchronized Communications start(Comm_Class_E comms, Context ctx) {
        if (instance == null) {
            instance = new Communications();
        }

        instance.ctx = ctx;
        instance.comms = comms;

        if (instance.comms == Comm_Class_E.MQTT) {
            instance.mqttInit();
        }
        return instance;
    }

    public void destroy() {
        if (instance.comms == Comm_Class_E.MQTT) {
            // Disconnect from MQTT broker
            if (instance.mqttClient != null) {
                try {
                    instance.mqttClient.disconnect();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to disconnect from MQTT broker: " + e.getMessage());
                }
            }
        }
    }

    protected void mqttInit() {

        // Initialize MQTT client
        try {
            instance.mqttClient = MqttClient.getInstance();
            instance.mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(TAG, "Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String msg = new String(message.getPayload());
                    Log.i(TAG, "Message received on topic: " + topic + ", Message: " + msg);
                    String[] strs = topic.split("/", 2);
                    if (!Globals.lookupTables.containsKey(strs[0])) {
                        Globals.lookupTables.put(strs[0], new HashMap<>());
                    }

                    Globals.lookupTables.get(strs[0]).put(strs[1], msg);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.i(TAG, "Message delivered");
                }
            });

            // Connect to MQTT broker
            instance.mqttClient.connect("username", "password", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Subscribe to a topic
                    try {
                        instance.mqttClient.subscribe("announce/info", 0, null);
                        instance.mqttClient.subscribe("ecu1/info", 0, null);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to subscribe to topic: " + e.getMessage());
                    }
                    try {
                        instance.mqttClient.publish("announce/info", "Hello, MQTT!", 0);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to publish message: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "Failed to connect to MQTT broker: " + exception.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize MQTT client: " + e.getMessage() + " " + e.getCause());
        }
    }
}
