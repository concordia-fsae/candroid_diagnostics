package com.example.candroid_diagnostics;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
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
    private static MqttClient instance;
    private MqttAsyncClient mqttClient;

    private MqttClient() throws MqttException {
        mqttClient = new MqttAsyncClient(Globals.MQTT_SERVER_URI,
                MqttAsyncClient.generateClientId(),
                new MemoryPersistence());
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

    public void subscribe(String topic, int qos, IMqttActionListener callback) throws Exception {
        mqttClient.subscribe(topic, qos, null, callback);
    }

    public void publish(String topic, String message, int qos) throws Exception {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        mqttClient.publish(topic, mqttMessage);
    }

    public void setCallback(MqttCallback callback) {
        mqttClient.setCallback(callback);
    }

    public void disconnect() throws Exception {
        mqttClient.disconnect();
    }
}
