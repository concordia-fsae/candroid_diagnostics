package com.example.candroid_diagnostics;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MqttClient {
    private static final String TAG = "MQTT Wrapper";

    private static MqttClient instance;
    private MqttAsyncClient mqttClient;
    private boolean connected = false;
    private boolean connecting = false;

    private MqttClient() throws MqttException {
        mqttClient = new MqttAsyncClient(Globals.MQTT_SERVER_URI,
                MqttAsyncClient.generateClientId(),
                new MemoryPersistence());

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "Connection lost: " + cause.getMessage());
                connected = false;
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
                Log.i(TAG, "Message received on topic: " + topic + ", Message: " + msg);
                String[] strs = topic.split("/", 2);

                Globals.setValue(strs[0], strs[1], msg);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i(TAG, "Message delivered");
            }
        });
    }

    public static synchronized MqttClient getInstance() throws MqttException {
        instance = new MqttClient();
        return instance;
    }

    public void connect(ArrayList<Tuple<String, Integer>> filterList) throws Exception {
        connect("username", "password", new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                // Subscribe to a topic
                filterList.forEach((e) -> {
                    subscribe(e.x, e.y, null);
                });
                connecting = false;
                connected = true;

                publish("announce/info", "Hello MQTT from Android!", 0);

                Log.i(TAG, "MQTT connection established!");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.e(TAG, "Failed to connect to MQTT broker: " + exception.getMessage());
                connecting = false;
                connected = false;
            }
        });
    }

    private void connect(String username, String password, IMqttActionListener callback) throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        mqttClient.connect(options, null, callback);
        connecting = true;
    }

    public void subscribe(String topic, int qos, IMqttActionListener callback) {
        try {
            mqttClient.subscribe(topic, qos, null, callback);
        } catch (Exception e) {
            Log.e(TAG, "Failed to subscribe to topic: " + e.getMessage());
        }
    }

    public void publish(String topic, String msg, int qos) {
        try {
            MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
            mqttMessage.setQos(qos);
            mqttClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            Log.e(TAG, "Failed to publish message: " + e.getMessage());
        }
    }

    public void setCallback(MqttCallback callback) {
        mqttClient.setCallback(callback);
    }

    public void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (Exception e) {
            Log.e(TAG, "Failed to disconnect from MQTT broker: " + e.getMessage());
        }

        connected = false;
    }

    public void unsubscribe(String topic) {
        try {
            mqttClient.unsubscribe(topic);
        } catch (Exception e) {
            Log.e(TAG, "Failed to unsubscribe from MQTT topic: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isConnecting() {
        return connecting;
    }
}
