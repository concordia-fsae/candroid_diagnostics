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

public class MqttClient {
    private static final String TAG = "MQTT Wrapper";

    private static MqttClient instance;
    private MqttAsyncClient mqttClient;

    private MqttClient() throws MqttException {
        mqttClient = new MqttAsyncClient(Globals.MQTT_SERVER_URI,
                MqttAsyncClient.generateClientId(),
                new MemoryPersistence());

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "Connection lost: " + cause.getMessage());
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
        if (instance == null) {
            instance = new MqttClient();
        }
        return instance;
    }

    public void connect(String username, String password, IMqttActionListener callback) throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        mqttClient.connect(options, null, callback);
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
    }

    public void unsubscribe(String topic) {
        try {
            mqttClient.unsubscribe(topic);
        } catch (Exception e) {
            Log.e(TAG, "Failed to unsubscribe from MQTT topic: " + e.getMessage());
        }
    }
}
