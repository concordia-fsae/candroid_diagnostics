package com.example.candroid_diagnostics;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.os.Bundle;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CANDroid";
    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize MQTT client
        try {
            mqttClient = MqttClient.getInstance();
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(TAG, "Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.i(TAG, "Message received on topic: " + topic + ", Message: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.i(TAG, "Message delivered");
                }
            });

            // Connect to MQTT broker
            mqttClient.connect("username", "password", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "MQTT Connected");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(getBaseContext(), "MQTT Connected", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                    // Subscribe to a topic
                    try {
                        mqttClient.subscribe("announce/info", 0, null);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to subscribe to topic: " + e.getMessage());
                    }
                    try {
                        mqttClient.publish("announce/info", "Hello, MQTT!", 0);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to publish message: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(getBaseContext(), "MQTT Connection Failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                    Log.e(TAG, "Failed to connect to MQTT broker: " + exception.getMessage());
                }
            });
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    final Toast toast = Toast.makeText(getBaseContext(), "MQTT Failure", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            Log.e(TAG, "Failed to initialize MQTT client: " + e.getMessage() + " " + e.getCause());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Disconnect from MQTT broker
        if (mqttClient != null) {
            try {
                mqttClient.disconnect();
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(getBaseContext(), "MQTT Disconnect Failed.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                Log.e(TAG, "Failed to disconnect from MQTT broker: " + e.getMessage());
            }
        }
    }
}
