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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "CANDroid";
    private MqttClient mqttClient;
    protected Button diagnosticBtn;
    protected Button ecu1Btn;
    protected Button ecu2Btn;
    protected Button ecu3Btn;
    protected Button ecu4Btn;
    private Communications comms;

    protected Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        diagnosticBtn = findViewById(R.id.DiagnosticBtn);
        diagnosticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToDiagnosticActivity();
                Globals.selected_ecu = "diag";
                goToECU1Activity();
            }
        });

        ecu1Btn = findViewById(R.id.ECU1btn);
        ecu1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.selected_ecu = "ecu1";
                goToECU1Activity();
            }
        });

        ecu2Btn = findViewById(R.id.ECU2btn);
        ecu2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.selected_ecu = "ecu2";
                goToECU1Activity();
            }
        });

        ecu3Btn = findViewById(R.id.ECU3btn);
        ecu3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.selected_ecu = "ecu3";
                goToECU1Activity();
            }
        });

        ecu4Btn = findViewById(R.id.ECU4btn);
        ecu4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.selected_ecu = "ecu4";
                goToECU1Activity();
            }
        });

        settingsBtn=findViewById(R.id.SettingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToSettingsActivity();
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

    private void goToSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }
}
