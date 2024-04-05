package com.example.candroid_diagnostics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CANDroid";
    private MqttClient mqttClient;
    protected Button diagnosticBtn;
    protected Button ecu1Btn;
    protected Button ecu2Btn;
    protected Button ecu3Btn;
    protected Button ecu4Btn;
    protected Button configBtn;
    private Communications comms;
    protected ImageButton infoDiag;
    protected ImageButton infoSet;
    protected ImageButton infoEcu;
    protected ImageButton infoConf;


    protected Button settingsBtn;
    private boolean welcomemsg = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();

        //To display the Welcome message on the first use.
        SharedPreferences sharedPreferences = getSharedPreferences("welcomemessage", Context.MODE_PRIVATE);
        welcomemsg = sharedPreferences.getBoolean("firstUse", true);
        if (welcomemsg) {
            welcomeMessageAlert();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstUse", false);
            editor.apply();
        }


    }


    private void welcomeMessageAlert() {
        new AlertDialog.Builder(this)
                .setTitle("WELCOME TO CANDroid APP!")
                .setMessage("You'll be able to easily make diagnostics and evaluate your data. Click on " +
                        " the little info buttons to see the role of each component of this app.")
                .setPositiveButton("Okay, got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void setupUI() {
        diagnosticBtn = findViewById(R.id.DiagnosticBtn);
        diagnosticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDiagnosticActivity();
                Globals.selected_ecu = "diag";
                //goToECU1Activity();
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

        configBtn = findViewById(R.id.ConfigBtn);

        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToConfigActivity();
            }
        });


        settingsBtn=findViewById(R.id.SettingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToSettingsActivity();
            }
        });

        infoDiag= findViewById(R.id.infoDiagnosticBtn);
        infoDiag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DiagDialog();
                dialog.show();


            }
        });

        infoEcu= findViewById(R.id.infoECUBtn);
        infoEcu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog dialog = ECUDialog();
                dialog.show();

            }
        });

        infoSet= findViewById(R.id.infoSettingsBtn);
        infoSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog dialog = SetDialog();
                dialog.show();

            }
        });

        infoConf= findViewById(R.id.infoConfigurationBtn);
        infoConf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog dialog = ConfDialog();
                dialog.show();
            }
        });

    }


    AlertDialog ConfDialog() {
        AlertDialog.Builder builderConf = new AlertDialog.Builder(this);
        builderConf.setMessage("Here is where you can make default configuration and set expected values for some variables.");

        builderConf.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builderConf.create();
    }

    AlertDialog SetDialog() {
        AlertDialog.Builder builderSet = new AlertDialog.Builder(this);
        builderSet.setMessage("Here is where you can connect your device to the computer you are using through its IP address.");

        builderSet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builderSet.create();
    }

    AlertDialog ECUDialog() {
        AlertDialog.Builder builderECU = new AlertDialog.Builder(this);
        builderECU.setMessage("Here is where you can find generated values for the variables tested. This app reads the data every second.");

        builderECU.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builderECU.create();
    }

    AlertDialog DiagDialog() {
        AlertDialog.Builder builderDiag = new AlertDialog.Builder(this);
        builderDiag.setMessage("Here is where you can find the error messages generated.");

        builderDiag.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builderDiag.create();

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

    private void goToConfigActivity() {
        Intent intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
        startActivity(intent);
    }
}
