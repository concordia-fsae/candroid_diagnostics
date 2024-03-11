package com.example.candroid_diagnostics;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.Context;
import android.util.Log;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CANDroid";
    private MqttClient mqttClient;
    protected Button diagnosticBtn;
    protected Button ecu1Btn;
    protected Button ecu2Btn;
    protected Button ecu3Btn;
    protected Button ecu4Btn;
    Communications comms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        comms.start(Communications.Comm_Class_E.MQTT, getApplicationContext());
    }

    private void setupUI() {
        diagnosticBtn = findViewById(R.id.DiagnosticBtn);
        diagnosticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToDiagnosticActivity();
            }
        });

        ecu1Btn = findViewById(R.id.ECU1btn);
        ecu1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToECU1Activity();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop communications interface
        comms.destroy();
    }

    private void goToECU1Activity() {
        Intent intent = new Intent(getApplicationContext(), ECUActivity.class);
        startActivity(intent);
    }

    private void goToDiagnosticActivity() {
        Intent intent = new Intent(getApplicationContext(), DiagnosticActivity.class);
        startActivity(intent);
    }
}
